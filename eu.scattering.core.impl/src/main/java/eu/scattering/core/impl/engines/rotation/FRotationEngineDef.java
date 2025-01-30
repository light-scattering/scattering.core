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

import java.util.Collection;

public class FRotationEngineDef implements FRotationEngine {
    private final FRotationProcessor core;

    private FRotationEngineDef(FRotationProcessor core) {

        this.core = core;
    }

    public static FRotationEngine create(FRotationProcessor core) {

        return new FRotationEngineDef(core);
    }

    @Override
    public Geometry rot(Geometry in, FRotQt qt) {

        FMatrix3x3D matrix = qt.getMatrix();
        FPos3D offset = qt.getOffset();

        Collection<FPoint> assembly = in.disassemble();

        for(FPoint point : assembly) {
            rot(point, offset, matrix);
        }

        return in;
    }

    private FPoint rot(FPoint point, FPos3D offset, FMatrix3x3D matrix) {

        point.sub(offset);
        point.mul(matrix);
        point.add(offset);

        return point;
    }

    //--------------------------------------------------

    @Override
    public FPoint setQtAngle(FPoint in, FPoint arg, double angle) {
        in.setCrossProduct(arg);

        FPos3D axis = in.toFPos3D();

        in.applyStateFrom(arg);

        FRotQt qt = core.getRotQt(axis, angle);

        return rotQt(in, qt);
    }

    @Override
    public FPoint rotQtAround(FPoint in, FPoint arg, double angle) {
        FRotQt qt = core.getRotQt(arg.toFPos3D(), angle);

        return rotQt(in, qt);
    }

    @Override
    public FPoint rotQt(FPoint in, FRotQt qt) {

        return rot(in, qt.getOffset(), qt.getMatrix());
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

        setQtAngle(fCopyLocal.getRefHead(), fCopyExternal.getRefHead(), angle);

        fCopyLocal.moveBase(origin.getRefBase());

        return origin.applyStateFrom(fCopyLocal);
    }

    @Override
    public FVector rotate(FVector origin, FPoint ref, double angle) {

        if (origin.getRefBase().isSimilar(ref)) {
            throw new IllegalStateException("The provided point is at the same position as the base point");
        }

        FVector fCopyLocal = origin.copy().set(origin.getRefBase(), ref);

        rot(origin, core.getRotQt(fCopyLocal.toFPairPos3D(), angle));

        return origin;
    }

    @Override
    public FVector rotate(FVector origin, FVector ref, double angle) {

        if (ref.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the provided FVector is not defined");
        }

        rot(origin, core.getRotQt(ref.toFPairPos3D(), angle));

        return origin;
    }

    @Override
    public void rotQtAround(FLine ref, Geometry in, double angle) {

        if (ref.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var rotor = core.getRotQt(ref.getRefOrigin().toFPairPos3D(), angle);

        in.disassemble().forEach(p -> rotQt(p, rotor));
    }

    @Override
    public void rotQtAround(FRay ref, Geometry in, double angle) {

        if (ref.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var rotor = core.getRotQt(ref.getRefOrigin().toFPairPos3D(), angle);

        in.disassemble().forEach(p -> {
            if (p.copy().apply(ref::project).toBoolean(ref::isPartOf)) {
                rotQt(p, rotor);
            }
        });
    }

    @Override
    public void rotQtAround(FSegment ref, Geometry in, double angle) {

        if (ref.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var rotor = core.getRotQt(ref.getRefOrigin().toFPairPos3D(), angle);

        in.disassemble().forEach(p -> {
            if (p.copy().apply(ref::project).toBoolean(ref::isPartOf)) {
                rotQt(p, rotor);
            }
        });
    }
}
