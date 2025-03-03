package eu.scattering.core.impl.engine.rotate;

import eu.scattering.core.design.engine.rotate.processor.FRotProcessor;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FRotQt.FRotQt;
import eu.scattering.core.transfer.container.storage.FMatrix3x3D.FMatrix3x3D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;

public class FRotProcessorDef implements FRotProcessor {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    public static FRotProcessor create() {

        return new FRotProcessorDef();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FRotQt getRotQt(double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        return getRotQt(factory.getFPairPos3D(
                factory.getFPos3D(bX, bY, bZ),
                factory.getFPos3D(hX, hY, hZ)),
                angle
        );
    }

    @Override
    public FRotQt getRotQt(double x, double y, double z, double angle) {

        return getRotQt(factory.getFPairPos3D(
                factory.getFPos3D(0, 0, 0),
                factory.getFPos3D(x, y, z)),
                angle
        );
    }

    @Override
    public FRotQt getRotQt(FPos3D axis, double angle) {

        return getRotQt(factory.getFPairPos3D(factory.getFPos3D(0, 0, 0), axis), angle);
    }

    @Override
    public FRotQt getRotQt(FPairPos3D axis, double angle) {
        FPos4D quaternion = getQuaternion(axis, angle);
        FPos3D offset = getOffset(axis);
        FMatrix3x3D matrix = getMatrix(quaternion);

        return factory.getFRotQt(quaternion, offset, matrix);
    }

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

    // -------------------------------------------------------------------------------------------------

    @Deprecated
    @Override
    public double getAngle(FRotQt core) {
        FPos4D quaternion = core.getQuaternion();

        double re = quaternion.getD0();

        if (re <= -1) {
            return Math.PI * 2;
        }

        if (re >= 1) {
            return 0;
        }

        return Math.acos(re) * 2;
    }

    @Deprecated
    @Override
    public FPairPos3D getAxis(FRotQt core) {
        FPos4D quaternion = core.getQuaternion();
        FPos3D offset = core.getOffset();

        double re = quaternion.getD0();
        double i = quaternion.getD1();
        double j = quaternion.getD2();
        double k = quaternion.getD3();

        double factor = 1 / Math.sqrt(1 - (re * re));

        FPos3D head = factory.getFPos3D(
                (i * factor) + offset.getD0(),
                (j * factor) + offset.getD1(),
                (k * factor) + offset.getD2()
        );

        return factory.getFPairPos3D(offset, head);
    }
}
