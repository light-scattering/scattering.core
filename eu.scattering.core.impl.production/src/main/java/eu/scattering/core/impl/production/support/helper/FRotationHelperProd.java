package eu.scattering.core.impl.production.support.helper;

import eu.scattering.core.design.elements.algebra.geometry.Geometry;
import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.engine.rotation.FRotation;
import eu.scattering.core.design.helpers.engine.FRotationHelper;
import eu.scattering.core.transfer.containers.engine.FRot.FRot;

public class FRotationHelperProd implements FRotationHelper {
    private final FRotation rotor;

    private FRotationHelperProd(FRotation rotor) {

        this.rotor = rotor;
    }

    public static FRotationHelper create(FRotation rotor) {

        return new FRotationHelperProd(rotor);
    }

    @Override
    public Geometry rotate(Geometry geometry, FRot core) {

        var rotCoreMatrix = core.getCoreMatrix();
        var rotOffset = core.getAxis().getPosA();

        geometry.disassemble().forEach((e) -> e.disassemble().forEach(p -> p
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
        ));

        return geometry;
    }

    @Override
    public FPoint setAngle(FPoint origin, FPoint ref, double angle) {
        var axis = origin.copy().setCrossProduct(ref);
        var fPointCopy = ref.copy();

        rotate(fPointCopy, rotor.getRotation(axis.toFPos3D(), angle));

        return origin.applyStateFrom(fPointCopy);
    }

    @Override
    public FPoint rotate(FPoint origin, FPoint ref, double angle) {
        rotate(origin, rotor.getRotation(ref.toFPos3D(), angle));

        return origin;
    }
}
