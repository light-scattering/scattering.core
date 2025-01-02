package eu.scattering.core.impl.production.core.immutable.rotation;

import eu.scattering.core.design.elements.algebra.geometry.Geometry;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.elements.engine.rotation.FRotation;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.containers.engine.FRot.FRot;
import eu.scattering.core.transfer.containers.grid.FMatrix3x3D.FMatrix3x3D;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import org.json.JSONObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FRotationProd implements FRotation {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    private Supplier<FVector> fVectorSupplier;

    private FRotationProd(Supplier<FVector> fVectorSupplier) {

        this.fVectorSupplier = fVectorSupplier;
    }

    public static FRotation create(Supplier<FVector> fVectorSupplier) {

        return new FRotationProd(fVectorSupplier);
    }

    @Override
    public FRot getRotation(FPairPos3D axis, double angle) {
        var rotVector = getRotVector(axis, angle);

        var rotCoreCode = getRotCoreCode(rotVector, angle);
        var rotCoreMatrix = getRotCoreMatrix(rotCoreCode);

        var rotRevAxis = getRotRevAxis(axis, rotCoreCode);
        var rotRevAngle = getRotRevAngle(rotCoreCode);

        return factory.getFRot(rotRevAxis, rotRevAngle, rotCoreCode, rotCoreMatrix);
    }

    public FRot getRotation(FPos3D axis, double angle) {

        return getRotation(factory.getFPairPos3D(factory.getFPos3D(0, 0, 0), axis), angle);
    }

    private FVector getRotVector(FPairPos3D axis, double angle) {
        var rotVector = fVectorSupplier.get().set(axis);

        if (rotVector.isNonDirectional()) {
            throw new IllegalArgumentException("The rotation axis is non-directional");
        }

        rotVector.set(axis);
        rotVector.moveBaseToCenter();
        rotVector.normalize();
        rotVector.getRefHead().mul(Math.sin(angle * 0.5));

        return rotVector;
    }

    private FPos4D getRotCoreCode(FVector rotVector, double angle) {
        var d0 = Math.cos(angle * 0.5);
        var d1 = rotVector.getRefHead().getX();
        var d2 = rotVector.getRefHead().getY();
        var d3 = rotVector.getRefHead().getZ();

        return factory.getFPos4D(d0, d1, d2, d3);
    }

    private FMatrix3x3D getRotCoreMatrix(FPos4D rotCoreCode) {
        var origin = new double[3][3];

        var re = rotCoreCode.getD0();
        var i = rotCoreCode.getD1();
        var j = rotCoreCode.getD2();
        var k = rotCoreCode.getD3();

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

    private double getRotRevAngle(FPos4D rotCoreCode) {
        var re = rotCoreCode.getD0();

        if (re <= -1) {
            return Math.PI * 2;
        }

        if (re >= 1) {
            return 0;
        }

        return Math.acos(re) * 2;
    }

    private FPairPos3D getRotRevAxis(FPairPos3D axis, FPos4D rotCoreCode) {
        var rotAxis = fVectorSupplier.get();

        var re = rotCoreCode.getD0();
        var i = rotCoreCode.getD1();
        var j = rotCoreCode.getD2();
        var k = rotCoreCode.getD3();

        double factor = 1 / Math.sqrt(1 - (re * re));

        rotAxis.getRefHead()
                .set(i, j, k)
                .mul(factor)
                .addX(axis.getPosA().getD0())
                .addY(axis.getPosA().getD1())
                .addZ(axis.getPosA().getD2());

        rotAxis.getRefBase()
                .setX(axis.getPosA().getD0())
                .setY(axis.getPosA().getD1())
                .setZ(axis.getPosA().getD2());

        return rotAxis.toTuplePos3D();
    }

    //------------------------------------------------------------------------------------

    @Override
    public Consumer<Geometry> rotate(FRot core) {
        var rotCoreMatrix = core.getCoreMatrix();
        var rotOffset = core.getAxis().getPosA();

        return (e) -> e.disassemble().forEach(p -> p
                .subX(rotOffset.getD0())
                .subY(rotOffset.getD1())
                .subZ(rotOffset.getD2())
                .set(
                        (rotCoreMatrix.get0x0() * p.getX()) + (rotCoreMatrix.get0x1() * p.getY()) + (rotCoreMatrix.get0x2() * p.getZ()),
                        (rotCoreMatrix.get1x0() * p.getX()) + (rotCoreMatrix.get1x1() * p.getY()) + (rotCoreMatrix.get1x2() * p.getZ()),
                        (rotCoreMatrix.get2x0() * p.getX()) + (rotCoreMatrix.get2x1() * p.getY()) + (rotCoreMatrix.get2x2() * p.getZ())
                )
                .addX(rotOffset.getD0())
                .addY(rotOffset.getD1())
                .addZ(rotOffset.getD2())
        );
    }

    //--------------------------------------------------

    // TODO - Not implemented
    @Override
    public JSONObject exportToJSON() {
        return null;
    }
}
