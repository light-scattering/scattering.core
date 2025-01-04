package eu.scattering.core.impl.engines.rotation;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.engines.rotation.processor.FRotationProcessor;
import eu.scattering.core.design.engines.rotation.FRotationEngine;
import eu.scattering.core.transfer.containers.engine.FRot.FRot;

public class FRotationEngineDef implements FRotationEngine {
    private final FRotationProcessor rotor;

    private FRotationEngineDef(FRotationProcessor rotor) {

        this.rotor = rotor;
    }

    public static FRotationEngine create(FRotationProcessor rotor) {

        return new FRotationEngineDef(rotor);
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

    @Override
    public FVector setAngle(FVector origin, FVector ref, double angle) {

        if (ref.isNonDirectional()) {
            throw new IllegalArgumentException("The direction of the provided vector is not defined");
        }

        if (origin.isSimilar(ref)) {
            throw new IllegalStateException("The two vectors are similar");
        }

        FVector fCopyLocal = origin.copy().moveBaseToCenter();
        FVector fCopyExternal = ref.copy().moveBaseToCenter();

        setAngle(fCopyLocal.getRefHead(), fCopyExternal.getRefHead(), angle);

        fCopyLocal.moveBase(origin.getRefBase());

        return origin.applyStateFrom(fCopyLocal);
    }

    @Override
    public FVector rotate(FVector origin, FPoint ref, double angle) {

        if (origin.getRefBase().isSimilar(ref)) {
            throw new IllegalStateException("The provided point is at the same position as the base point");
        }

        FVector fCopyLocal = origin.copy().set(origin.getRefBase(), ref);

        rotate(origin, rotor.getRotation(fCopyLocal.toFPairPos3D(), angle));

        return origin;
    }

    @Override
    public FVector rotate(FVector origin, FVector ref, double angle) {

        if (ref.isNonDirectional()) {
            throw new IllegalArgumentException("The direction of the provided FVector is not defined");
        }

        rotate(origin, rotor.getRotation(ref.toFPairPos3D(), angle));

        return origin;
    }
}
