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
    private static double epsilon = 1E-8;

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
    public FPoint setRgAngle(double x, double y, double z, FPoint in, double angle) {
        if (in.isNearZero()) {
            throw new IllegalStateException("The input vector is non-directional");
        }

        if (in.isSimilar(x, y, z)) {
            throw new IllegalStateException("The vectors are similar");
        }

        if (Math.abs(x) < epsilon && Math.abs(y) < epsilon && Math.abs(z) < epsilon) {
            throw new IllegalArgumentException("The reference vector is non-directional");
        }

        double memoMag = in.getMagnitude();

        in.normalize();

        double opRawX = x;
        double opRawY = y;
        double opRawZ = z;

        double opRawFactor = 1 / Math.sqrt((opRawX * opRawX) + (opRawY * opRawY) + (opRawZ * opRawZ));

        opRawX *= opRawFactor;
        opRawY *= opRawFactor;
        opRawZ *= opRawFactor;

        double opX = (opRawY * in.getZ()) - (opRawZ * in.getY());
        double opY = (opRawZ * in.getX()) - (opRawX * in.getZ());
        double opZ = (opRawX * in.getY()) - (opRawY * in.getX());

        double opFactor = 1 / Math.sqrt((opX * opX) + (opY * opY) + (opZ * opZ));

        opX *= opFactor;
        opY *= opFactor;
        opZ *= opFactor;

        if (Math.abs(opX) < epsilon && Math.abs(opY) < epsilon && Math.abs(opZ) < epsilon) {
            throw new IllegalStateException("The rotation vector is non-directional");
        }

        var aDelta = angle - Math.acos(in.getDotProduct(opRawX, opRawY, opRawZ));

        var aCos = Math.cos(aDelta);
        var aSin = Math.sin(aDelta);

        var tmpSuffix = (1 - aCos) * (opX * in.getX() + opY * in.getY() + opZ * in.getZ());

        var resX = aCos * in.getX() + aSin * (opY * in.getZ() - opZ * in.getY()) + opX * tmpSuffix;
        var resY = aCos * in.getY() + aSin * (opZ * in.getX() - opX * in.getZ()) + opY * tmpSuffix;
        var resZ = aCos * in.getZ() + aSin * (opX * in.getY() - opY * in.getX()) + opZ * tmpSuffix;

        in.set(resX, resY, resZ);

        in.setMagnitude(memoMag);

        return in;
    }

    @Override
    public FPoint setRgAngle(FPoint ref, FPoint in, double angle) {

        return setRgAngle(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FPoint setRgAngle(FPos3D ref, FPoint in, double angle) {

        return setRgAngle(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
    }

    @Override
    public FPoint rotRgAround(double x, double y, double z, FPoint in, double angle) {

        if (Math.abs(x) < epsilon && Math.abs(y) < epsilon && Math.abs(z) < epsilon) {
            throw new IllegalArgumentException("The reference vector is non-directional");
        }

        double memoMag = in.getMagnitude();

        in.normalize();

        double opRawX = x;
        double opRawY = y;
        double opRawZ = z;

        double opRawFactor = 1 / Math.sqrt((opRawX * opRawX) + (opRawY * opRawY) + (opRawZ * opRawZ));

        opRawX *= opRawFactor;
        opRawY *= opRawFactor;
        opRawZ *= opRawFactor;

        if (Math.abs(opRawX) < epsilon && Math.abs(opRawY) < epsilon && Math.abs(opRawZ) < epsilon) {
            throw new IllegalStateException("The rotation vector is non-directional");
        }

        double aCos = Math.cos(-angle);
        double aSin = Math.sin(-angle);

        double tmpSuffix = (1 - aCos) * (opRawX * in.getX() + opRawY * in.getY() + opRawZ * in.getZ());

        double resX = aCos * in.getX() + aSin * (opRawY * in.getZ() - opRawZ * in.getY()) + opRawX * tmpSuffix;
        double resY = aCos * in.getY() + aSin * (opRawZ * in.getX() - opRawX * in.getZ()) + opRawY * tmpSuffix;
        double resZ = aCos * in.getZ() + aSin * (opRawX * in.getY() - opRawY * in.getX()) + opRawZ * tmpSuffix;

        in.set(resX, resY, resZ);

        in.setMagnitude(memoMag);

        return in;
    }

    @Override
    public FPoint rotRgAround(FPoint ref, FPoint in, double angle) {

        return rotRgAround(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FPoint rotRgAround(FPos3D ref, FPoint in, double angle) {

        return rotRgAround(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
    }

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
    public FVector setAngle(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle) {

        if (in.isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalArgumentException("The direction of the reference FVector is not defined");
        }

        double memoOBX = in.getBaseX();
        double memoOBY = in.getBaseY();
        double memoOBZ = in.getBaseZ();
        double zeroAX = hX - bX;
        double zeroAY = hY - bY;
        double zeroAZ = hZ - bZ;

        in.moveBaseToCenter();
        setRgAngle(zeroAX, zeroAY, zeroAZ, in.getRefHead(), angle);
        in.moveBase(memoOBX, memoOBY, memoOBZ);

        return in;
    }

    @Override
    public FVector setAngle(FVector ref, FVector in, double angle) {

        return setAngle(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FVector setAngle(FPairPos3D ref, FVector in, double angle) {

        return setAngle(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector setAngleSimple(double hX, double hY, double hZ, FVector in, double angle) {

        return setAngle(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector setAngleSimple(FPoint ref, FVector in, double angle) {

        return setAngleSimple(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector setAngleSimple(FPos3D ref, FVector in, double angle) {

        return setAngleSimple(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
    }

    @Override
    public FVector rotAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle) {

        in.subXYZ(bX, bY, bZ);

        rotRgAround(hX - bX, hY - bY, hZ - bZ, in.getRefBase(), angle);
        rotRgAround(hX - bX, hY - bY, hZ - bZ, in.getRefHead(), angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FVector rotAround(FVector ref, FVector in, double angle) {

        return rotAround(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FVector rotAround(FPairPos3D ref, FVector in, double angle) {

        return rotAround(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotAroundSimple(double hX, double hY, double hZ, FVector in, double angle) {

        return rotAround(0, 0, 0, hX, hY, hX, in, angle);
    }

    @Override
    public FVector rotAroundSimple(FPoint ref, FVector in, double angle) {

        return rotAroundSimple(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector rotAroundSimple(FPos3D ref, FVector in, double angle) {

        return rotAroundSimple(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
    }

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
    public FVector setQtAngleCompact(double hX, double hY, double hZ, FVector in, double angle) {

        return setQtAngle(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector setQtAngleCompact(FPoint ref, FVector in, double angle) {

        return setQtAngleCompact(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector setQtAngleCompact(FPos3D ref, FVector in, double angle) {

        return setQtAngleCompact(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
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
    public FVector rotQtAroundCompact(double hX, double hY, double hZ, FVector in, double angle) {

        return rotQtAround(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector rotQtAroundCompact(FPoint ref, FVector in, double angle) {

        return rotQtAroundCompact(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector rotQtAroundCompact(FPos3D ref, FVector in, double angle) {

        return rotQtAroundCompact(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
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
    public FVector rotQtAroundBase(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle) {

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
    public FVector rotQtAroundBase(FVector ref, FVector in, double angle) {

        return rotQtAroundBase(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundBase(FPairPos3D ref, FVector in, double angle) {

        return rotQtAroundBase(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundBaseCompact(double hX, double hY, double hZ, FVector in, double angle) {

        return rotQtAroundBase(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector rotQtAroundBaseCompact(FPoint ref, FVector in, double angle) {

        return rotQtAroundBaseCompact(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector rotQtAroundBaseCompact(FPos3D ref, FVector in, double angle) {

        return rotQtAroundBaseCompact(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
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

    // -------------------------------------------------------------------------------------------------

    private boolean isNearZeroLength(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        boolean posX = Math.abs(bX - hX) < epsilon;
        boolean posY = Math.abs(bY - hY) < epsilon;
        boolean posZ = Math.abs(bZ - hZ) < epsilon;

        return posX && posY && posZ;
    }

    private double getMagnitude(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double distX = hX - bX;
        double distY = hY - bY;
        double distZ = hZ - bZ;

        return Math.sqrt((distX * distX) + (distY * distY) + (distZ * distZ));
    }
}
