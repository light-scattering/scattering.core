package eu.scattering.core.impl.engines.rotation;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRay;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegment;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.engines.rotation.processor.FRotationProcessor;
import eu.scattering.core.design.engines.rotation.FRotationEngine;
import eu.scattering.core.transfer.containers.engine.FRot.FRot;

public class FRotationEngineDef implements FRotationEngine {
    private final FRotationProcessor core;

    private FRotationEngineDef(FRotationProcessor core) {

        this.core = core;
    }

    public static FRotationEngine create(FRotationProcessor core) {

        return new FRotationEngineDef(core);
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
    public FPoint setAngle(FPoint origin, FPoint op, double angle) {
        var axis = origin.copy().setCrossProduct(op);
        var fPointCopy = op.copy();

        fPointCopy.apply(p -> rotate(p, core.getRotation(axis.toFPos3D(), angle)));

        return origin.applyStateFrom(fPointCopy);
    }

    @Override
    public FPoint rotate(FPoint origin, FPoint op, double angle) {

        return origin.apply(p -> rotate(p, core.getRotation(op.toFPos3D(), angle)));
    }

    @Override
    public FVector setAngle(FVector origin, FVector ref, double angle) {

        if (ref.isNonDirectional()) {
            throw new IllegalArgumentException("The direction of the provided vector is not defined");
        }

        if (origin.isSimilar(ref)) {
            throw new IllegalStateException("The two vectors are similar");
        }

        var fCopyLocal = origin.copy().moveBaseToCenter();
        var fCopyExternal = ref.copy().moveBaseToCenter();

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

        rotate(origin, core.getRotation(fCopyLocal.toFPairPos3D(), angle));

        return origin;
    }

    @Override
    public FVector rotate(FVector origin, FVector ref, double angle) {

        if (ref.isNonDirectional()) {
            throw new IllegalArgumentException("The direction of the provided FVector is not defined");
        }

        rotate(origin, core.getRotation(ref.toFPairPos3D(), angle));

        return origin;
    }

    @Override
    public void rotate(FLine origin, Geometry geometry, double angle) {

        if (origin.getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var rotor = core.getRotation(origin.getRefOrigin().toFPairPos3D(), angle);

        geometry.disassemble().forEach(p -> rotate(p, rotor));
    }

    @Override
    public void rotate(FRay origin, Geometry geometry, double angle) {

        if (origin.getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var rotor = core.getRotation(origin.getRefOrigin().toFPairPos3D(), angle);

        geometry.disassemble().forEach(p -> {
            if (p.copy().apply(origin::project).toBoolean(origin::isPartOf)) {
                rotate(p, rotor);
            }
        });
    }

    @Override
    public void rotate(FSegment origin, Geometry geometry, double angle) {

        if (origin.getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var rotor = core.getRotation(origin.getRefOrigin().toFPairPos3D(), angle);

        geometry.disassemble().forEach(p -> {
            if (p.copy().apply(origin::project).toBoolean(origin::isPartOf)) {
                rotate(p, rotor);
            }
        });
    }
}
