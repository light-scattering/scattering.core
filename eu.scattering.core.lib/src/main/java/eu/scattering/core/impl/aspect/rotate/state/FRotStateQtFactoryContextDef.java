package eu.scattering.core.impl.aspect.rotate.state;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.rotate.state.FRotState;
import eu.scattering.core.design.aspect.rotate.state.FRotStateFactoryContext;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import org.json.JSONObject;

public class FRotStateQtFactoryContextDef implements FRotStateFactoryContext {
    private final ScatterFactory factory;

    private FRotStateQtFactoryContextDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FRotStateFactoryContext create(ScatterFactory factory) {

        return new FRotStateQtFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FRotState fromComponents(FPos4D quaternion, FPos3D offset, FMatrix3x3D matrix) {

        return FRotStateQtDef.create(this.factory, quaternion, offset, matrix);
    }

    @Override
    public FRotState fromJSON(JSONObject json) {

        return FRotStateQtDef.create(this.factory, json);
    }

    @Override
    public FRotState aroundAxis(FPairPos3D axis, double angle) {
        FPos3D offset = getOffset(axis);
        FPos4D quaternion = getQuaternion(axis, angle);
        FMatrix3x3D matrix = getMatrix(quaternion);

        return FRotStateQtDef.create(this.factory, quaternion, offset, matrix);
    }

    @Override
    public FRotState aroundAxis(FPos3D axis, double angle) {

        return aroundAxis(factory.getFPairPos3D(factory.getFPos3D(0, 0, 0), axis), angle);
    }

    @Override
    public FRotState aroundAxis(double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        return aroundAxis(factory.getFPairPos3D(factory.getFPos3D(bX, bY, bZ), factory.getFPos3D(hX, hY, hZ)), angle);
    }

    @Override
    public FRotState aroundAxis(double x, double y, double z, double angle) {

        return aroundAxis(factory.getFPairPos3D(factory.getFPos3D(0, 0, 0), factory.getFPos3D(x, y, z)), angle);
    }

    //--------------------------------------------------

    private FPos4D getQuaternion(FPairPos3D axis, double angle) {

        if (axis.getPosA().equals(axis.getPosB())) {
            throw new IllegalArgumentException("The rotation axis is non-directional");
        }

        double headX = axis.getPosB().getD0() - axis.getPosA().getD0();
        double headY = axis.getPosB().getD1() - axis.getPosA().getD1();
        double headZ = axis.getPosB().getD2() - axis.getPosA().getD2();

        double factor1 = Math.sqrt((headX * headX) + (headY * headY) + (headZ * headZ));

        headX /= factor1;
        headY /= factor1;
        headZ /= factor1;

        double factor2 = Math.sin(angle * 0.5);

        headX *= factor2;
        headY *= factor2;
        headZ *= factor2;

        return factory.getFPos4D(Math.cos(angle * 0.5), headX, headY, headZ);
    }

    private FPos3D getOffset(FPairPos3D axis) {

        return axis.getPosA();
    }

    private FMatrix3x3D getMatrix(FPos4D quaternion) {
        var origin = new double[3][3];

        double re = quaternion.getD0();
        double i = quaternion.getD1();
        double j = quaternion.getD2();
        double k = quaternion.getD3();

        origin[0][0] = 1 - (2 * j * j) - (2 * k * k);
        origin[0][1] = 2 * ((i * j) + (re * k));
        origin[0][2] = 2 * ((i * k) - (re * j));
        origin[1][0] = 2 * ((i * j) - (re * k));
        origin[1][1] = 1 - (2 * i * i) - (2 * k * k);
        origin[1][2] = 2 * ((j * k) + (re * i));
        origin[2][0] = 2 * ((i * k) + (re * j));
        origin[2][1] = 2 * ((j * k) - (re * i));
        origin[2][2] = 1 - (2 * i * i) - (2 * j * j);

        return factory.getFMatrix3x3D(origin);
    }
}
