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
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
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
    public FPoint setQtAngle(double x, double y, double z, FPoint in, double angle) {
        in.setCrossProduct(x, y, z);

        FPos3D axis = in.toFPos3D();

        in.set(x, y, z);

        FRotQt qt = core.getRotQt(axis, angle);

        return rotQt(in, qt);
    }

    @Override
    public FPoint setQtAngle(FPoint ref, FPoint in, double angle) {

        return setQtAngle(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FPoint setQtAngle(FPos3D ref, FPoint in, double angle) {

        return setQtAngle(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
    }

    @Override
    public FPoint rotQtAround(double x, double y, double z, FPoint in, double angle) {
        FRotQt qt = core.getRotQt(x, y, z, angle);

        return rotQt(in, qt);
    }

    @Override
    public FPoint rotQtAround(FPoint ref, FPoint in, double angle) {
        FRotQt qt = core.getRotQt(ref.toFPos3D(), angle);

        return rotQt(in, qt);
    }

    @Override
    public FPoint rotQtAround(FPos3D ref, FPoint in, double angle) {
        FRotQt qt = core.getRotQt(ref, angle);

        return rotQt(in, qt);
    }

    @Override
    public FPoint rotQt(FPoint in, FRotQt qt) {

        return rot(in, qt.getOffset(), qt.getMatrix());
    }

    //--------------------------------------------------

    @Override
    public FVector setQtAngle(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle) {

        if (in.isSimilar(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalStateException("The two vectors are similar");
        }

        double memoBX = in.getBaseX();
        double memoBY = in.getBaseY();
        double memoBZ = in.getBaseZ();

        in.moveBaseToCenter();

        double opX = hX - bX;
        double opY = hY - bY;
        double opZ = hZ - bZ;

        setQtAngle(opX, opY, opZ, in.getRefHead(), angle);

        in.moveBase(memoBX, memoBY, memoBZ);

        return in;
    }

    @Override
    public FVector setQtAngle(FVector ref, FVector in, double angle) {

       return setQtAngle(
               ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
               ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
               in, angle
       );
    }

    @Override
    public FVector setQtAngle(FPairPos3D ref, FVector in, double angle) {

        return setQtAngle(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector setQtAngleSimple(double hX, double hY, double hZ, FVector in, double angle) {

        return setQtAngle(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector setQtAngleSimple(FPoint ref, FVector in, double angle) {

        return setQtAngleSimple(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector setQtAngleSimple(FPos3D ref, FVector in, double angle) {

        return setQtAngleSimple(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
    }

    @Override
    public FVector rotQtAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle) {

        in.subXYZ(bX, bY, bZ);

        rotQtAround(hX - bX, hY - bY, hZ - bZ, in.getRefBase(), angle);
        rotQtAround(hX - bX, hY - bY, hZ - bZ, in.getRefHead(), angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FVector rotQtAround(FVector ref, FVector in, double angle) {

        return rotQtAround(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAround(FPairPos3D ref, FVector in, double angle) {

        return rotQtAround(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundSimple(double hX, double hY, double hZ, FVector in, double angle) {

        return rotQtAround(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector rotQtAroundSimple(FPoint ref, FVector in, double angle) {

        return rotQtAroundSimple(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector rotQtAroundSimple(FPos3D ref, FVector in, double angle) {

        return rotQtAroundSimple(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
    }

    @Override
    public FPoint rotQtAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FPoint in, double angle) {

        in.subXYZ(bX, bY, bZ);

        rotQtAround(hX - bX, hY - bY, hZ - bZ, in, angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FPoint rotQtAround(FVector ref, FPoint in, double angle) {

        return rotQtAround(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FPoint rotQtAround(FPairPos3D ref, FPoint in, double angle) {

        return rotQtAround(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundAxis(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle) {

        if (in.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the provided FVector is not defined");
        }

        double memoBX = in.getBaseX();
        double memoBY = in.getBaseY();
        double memoBZ = in.getBaseZ();

        in.moveBaseToCenter();

        rot(in, core.getRotQt(hX - bX, hY - bY, hZ - bZ, angle));

        in.moveBase(memoBX, memoBY, memoBZ);

        return in;
    }

    @Override
    public FVector rotQtAroundAxis(FVector ref, FVector in, double angle) {

        return rotQtAroundAxis(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundAxis(FPairPos3D ref, FVector in, double angle) {

        return rotQtAroundAxis(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundAxisSimple(double hX, double hY, double hZ, FVector in, double angle) {

        return rotQtAroundAxis(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector rotQtAroundAxisSimple(FPoint ref, FVector in, double angle) {

        return rotQtAroundAxisSimple(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector rotQtAroundAxisSimple(FPos3D ref, FVector in, double angle) {

        return rotQtAroundAxisSimple(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
    }

    @Override
    public FVector rotQt(FVector in, FRotQt qt) {

        rotQt(in.getRefBase(), qt);
        rotQt(in.getRefHead(), qt);

        return in;
    }

    //--------------------------------------------------

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
