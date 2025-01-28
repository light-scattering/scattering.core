package eu.scattering.core.impl.engines.rotation;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRay;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegment;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.engines.rotation.processor.FRotationProcessor;
import eu.scattering.core.design.engines.rotation.FRotationEngine;
import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;
import eu.scattering.core.transfer.containers.grid.FMatrix3x3D.FMatrix3x3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public class FRotationEngineDef implements FRotationEngine {
    private final FRotationProcessor core;

    private FRotationEngineDef(FRotationProcessor core) {

        this.core = core;
    }

    public static FRotationEngine create(FRotationProcessor core) {

        return new FRotationEngineDef(core);
    }

    @Override
    public Geometry rotate(Geometry geometry, FRotQt qt) {

        FMatrix3x3D matrix = qt.getMatrix();
        FPos3D rotOffset = qt.getOffset();

        geometry.disassemble().forEach((e) -> e.disassemble().forEach(p -> p
                .subX(rotOffset.getD0())
                .subY(rotOffset.getD1())
                .subZ(rotOffset.getD2())
                .set(
                        (matrix.get0x0() * p.getX()) + (matrix.get0x1() * p.getY()) + (matrix.get0x2() * p.getZ()),
                        (matrix.get1x0() * p.getX()) + (matrix.get1x1() * p.getY()) + (matrix.get1x2() * p.getZ()),
                        (matrix.get2x0() * p.getX()) + (matrix.get2x1() * p.getY()) + (matrix.get2x2() * p.getZ())
                )
                .addX(rotOffset.getD0())
                .addY(rotOffset.getD1())
                .addZ(rotOffset.getD2())
        ));

        return geometry;
    }

//    private void rotate(FPoint in, FRotQt qt) {
//
//    }

    //--------------------------------------------------

    @Override
    public FPoint setFPointQtAngle(FPoint in, FPoint arg, double angle) {
        var axis = in.copy().setCrossProduct(arg);
        var fPointCopy = arg.copy();

        fPointCopy.apply(p -> rotFPointQt(p, core.getRotationQt(axis.toFPos3D(), angle)));

        return in.applyStateFrom(fPointCopy);
    }

    @Override
    public FPoint rotFPointQtAround(FPoint in, FPoint arg, double angle) {

        return in.apply(p -> rotFPointQt(p, core.getRotationQt(arg.toFPos3D(), angle)));
    }

    @Override
    public FPoint rotFPointQt(FPoint in, FRotQt core) {

        return in.apply(p -> rotate(p, core));
    }

    //--------------------------------------------------

    @Override
    public FVector setAngle(FVector origin, FVector ref, double angle) {

        if (ref.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the provided vector is not defined");
        }

        if (origin.isSimilar(ref)) {
            throw new IllegalStateException("The two vectors are similar");
        }

        var fCopyLocal = origin.copy().moveBaseToCenter();
        var fCopyExternal = ref.copy().moveBaseToCenter();

        setFPointQtAngle(fCopyLocal.getRefHead(), fCopyExternal.getRefHead(), angle);

        fCopyLocal.moveBase(origin.getRefBase());

        return origin.applyStateFrom(fCopyLocal);
    }

    @Override
    public FVector rotate(FVector origin, FPoint ref, double angle) {

        if (origin.getRefBase().isSimilar(ref)) {
            throw new IllegalStateException("The provided point is at the same position as the base point");
        }

        FVector fCopyLocal = origin.copy().set(origin.getRefBase(), ref);

        rotate(origin, core.getRotationQt(fCopyLocal.toFPairPos3D(), angle));

        return origin;
    }

    @Override
    public FVector rotate(FVector origin, FVector ref, double angle) {

        if (ref.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the provided FVector is not defined");
        }

        rotate(origin, core.getRotationQt(ref.toFPairPos3D(), angle));

        return origin;
    }

    @Override
    public void rotate(FLine origin, Geometry geometry, double angle) {

        if (origin.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var rotor = core.getRotationQt(origin.getRefOrigin().toFPairPos3D(), angle);

        geometry.disassemble().forEach(p -> rotFPointQt(p, rotor));
    }

    @Override
    public void rotate(FRay origin, Geometry geometry, double angle) {

        if (origin.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var rotor = core.getRotationQt(origin.getRefOrigin().toFPairPos3D(), angle);

        geometry.disassemble().forEach(p -> {
            if (p.copy().apply(origin::project).toBoolean(origin::isPartOf)) {
                rotFPointQt(p, rotor);
            }
        });
    }

    @Override
    public void rotate(FSegment origin, Geometry geometry, double angle) {

        if (origin.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var rotor = core.getRotationQt(origin.getRefOrigin().toFPairPos3D(), angle);

        geometry.disassemble().forEach(p -> {
            if (p.copy().apply(origin::project).toBoolean(origin::isPartOf)) {
                rotFPointQt(p, rotor);
            }
        });
    }
}
