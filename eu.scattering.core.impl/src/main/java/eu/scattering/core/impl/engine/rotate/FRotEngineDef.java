package eu.scattering.core.impl.engine.rotate;

import eu.scattering.core.design.engine.rotate.FRotEngine;
import eu.scattering.core.design.engine.rotate.generator.FRotGenerator;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.transfer.container.storage.FRotQt.FRotQt;
import eu.scattering.core.transfer.container.storage.FMatrix3x3D.FMatrix3x3D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.Collection;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FRotEngineDef implements FRotEngine {
    private final FRotGenerator core;

    private FRotEngineDef(FRotGenerator core) {

        this.core = core;
    }

    public static FRotEngine create(FRotGenerator core) {

        return new FRotEngineDef(core);
    }

    @Override
    public Geometry rot(Geometry in, FRotQt qt) {

        FMatrix3x3D matrix = qt.getMatrix();
        FPos3D offset = qt.getOffset();

        Collection<FPoint> assembly = in.toFPoints();

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

        if (Math.abs(x) < EPSILON && Math.abs(y) < EPSILON && Math.abs(z) < EPSILON) {
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

        if (Math.abs(opX) < EPSILON && Math.abs(opY) < EPSILON && Math.abs(opZ) < EPSILON) {
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

        if (Math.abs(x) < EPSILON && Math.abs(y) < EPSILON && Math.abs(z) < EPSILON) {
            throw new IllegalArgumentException("The reference vector is non-directional");
        }

        double memoMag = in.getMagnitude();

        if (memoMag < EPSILON) {
            return in;
        }

        in.normalize();

        double opRawX = x;
        double opRawY = y;
        double opRawZ = z;

        double opRawFactor = 1 / Math.sqrt((opRawX * opRawX) + (opRawY * opRawY) + (opRawZ * opRawZ));

        opRawX *= opRawFactor;
        opRawY *= opRawFactor;
        opRawZ *= opRawFactor;

        if (Math.abs(opRawX) < EPSILON && Math.abs(opRawY) < EPSILON && Math.abs(opRawZ) < EPSILON) {
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

        if (in.isNearZero()) {
            throw new IllegalArgumentException("The input vector is non-directional");
        }

        if (in.isSimilar(x, y, z)) {
            throw new IllegalStateException("The vectors are similar");
        }

        if (Math.abs(x) < EPSILON && Math.abs(y) < EPSILON && Math.abs(z) < EPSILON) {
            throw new IllegalArgumentException("The reference vector is non-directional");
        }

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
    public FVector setRgAngle(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle) {

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
    public FVector setRgAngle(FVector ref, FVector in, double angle) {

        return setRgAngle(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FVector setRgAngle(FPairPos3D ref, FVector in, double angle) {

        return setRgAngle(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector setRgAngleBaseCommon(double hX, double hY, double hZ, FVector in, double angle) {

        return setRgAngle(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                in, angle
        );
    }

    @Override
    public FVector setRgAngleBaseCommon(FPoint ref, FVector in, double angle) {

        return setRgAngle(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                in, angle
        );
    }

    @Override
    public FVector setRgAngleBaseCommon(FPos3D ref, FVector in, double angle) {

        return setRgAngle(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                in, angle
        );
    }

    @Override
    public FVector setRgAngleBaseZero(double hX, double hY, double hZ, FVector in, double angle) {

        return setRgAngle(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector setRgAngleBaseZero(FPoint ref, FVector in, double angle) {

        return setRgAngleBaseZero(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector setRgAngleBaseZero(FPos3D ref, FVector in, double angle) {

        return setRgAngleBaseZero(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
    }

    @Override
    public FPoint rotRgAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FPoint in, double angle) {

        in.subXYZ(bX, bY, bZ);

        rotRgAround(hX - bX, hY - bY, hZ - bZ, in, angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FPoint rotRgAround(FVector ref, FPoint in, double angle) {

        return rotRgAround(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FPoint rotRgAround(FPairPos3D ref, FPoint in, double angle) {

        return rotRgAround(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotRgAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle) {

        in.subXYZ(bX, bY, bZ);

        rotRgAround(hX - bX, hY - bY, hZ - bZ, in.getRefBase(), angle);
        rotRgAround(hX - bX, hY - bY, hZ - bZ, in.getRefHead(), angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FVector rotRgAround(FVector ref, FVector in, double angle) {

        return rotRgAround(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FVector rotRgAround(FPairPos3D ref, FVector in, double angle) {

        return rotRgAround(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotRgAroundBaseCommon(double hX, double hY, double hZ, FVector in, double angle) {

        return rotRgAround(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                in, angle
        );
    }

    @Override
    public FVector rotRgAroundBaseCommon(FPoint ref, FVector in, double angle) {

        return rotRgAround(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                in, angle
        );
    }

    @Override
    public FVector rotRgAroundBaseCommon(FPos3D ref, FVector in, double angle) {

        return rotRgAround(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotRgAroundBaseZero(double hX, double hY, double hZ, FVector in, double angle) {

        return rotRgAround(0, 0, 0, hX, hY, hX, in, angle);
    }

    @Override
    public FVector rotRgAroundBaseZero(FPoint ref, FVector in, double angle) {

        return rotRgAroundBaseZero(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector rotRgAroundBaseZero(FPos3D ref, FVector in, double angle) {

        return rotRgAroundBaseZero(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
    }

    @Override
    public FVector rotRgAroundFixed(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle) {

        if (in.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the provided FVector is not defined");
        }

        double memoBX = in.getBaseX();
        double memoBY = in.getBaseY();
        double memoBZ = in.getBaseZ();

        in.moveBaseToCenter();

        rotRgAround(hX - bX, hY - bY, hZ - bZ, in.getRefHead(), angle);

        in.moveBase(memoBX, memoBY, memoBZ);

        return in;
    }

    @Override
    public FVector rotRgAroundFixed(FVector ref, FVector in, double angle) {

        return rotRgAroundFixed(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FVector rotRgAroundFixed(FPairPos3D ref, FVector in, double angle) {

        return rotRgAroundFixed(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotRgAroundFixedBaseCommon(double hX, double hY, double hZ, FVector in, double angle) {

        return rotRgAroundFixed(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                in, angle
        );
    }

    @Override
    public FVector rotRgAroundFixedBaseCommon(FPoint ref, FVector in, double angle) {

        return rotRgAroundFixed(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                in, angle
        );
    }

    @Override
    public FVector rotRgAroundFixedBaseCommon(FPos3D ref, FVector in, double angle) {

        return rotRgAroundFixed(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotRgAroundFixedBaseZero(double hX, double hY, double hZ, FVector in, double angle) {

        return rotRgAroundFixed(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector rotRgAroundFixedBaseZero(FPoint ref, FVector in, double angle) {
        return rotRgAroundFixedBaseZero(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector rotRgAroundFixedBaseZero(FPos3D ref, FVector in, double angle) {

        return rotRgAroundFixedBaseZero(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
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
    public FVector setQtAngleBaseCommon(double hX, double hY, double hZ, FVector in, double angle) {

        return setQtAngle(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                in, angle
        );
    }

    @Override
    public FVector setQtAngleBaseCommon(FPoint ref, FVector in, double angle) {

        return setQtAngle(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                in, angle
        );
    }

    @Override
    public FVector setQtAngleBaseCommon(FPos3D ref, FVector in, double angle) {

        return setQtAngle(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                in, angle
        );
    }

    @Override
    public FVector setQtAngleBaseZero(double hX, double hY, double hZ, FVector in, double angle) {

        return setQtAngle(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector setQtAngleBaseZero(FPoint ref, FVector in, double angle) {

        return setQtAngleBaseZero(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector setQtAngleBaseZero(FPos3D ref, FVector in, double angle) {

        return setQtAngleBaseZero(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
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
    public FVector rotQtAroundBaseCommon(double hX, double hY, double hZ, FVector in, double angle) {

        return rotQtAround(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundBaseCommon(FPoint ref, FVector in, double angle) {

        return rotQtAround(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundBaseCommon(FPos3D ref, FVector in, double angle) {

        return rotQtAround(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundBaseZero(double hX, double hY, double hZ, FVector in, double angle) {

        return rotQtAround(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector rotQtAroundBaseZero(FPoint ref, FVector in, double angle) {

        return rotQtAroundBaseZero(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector rotQtAroundBaseZero(FPos3D ref, FVector in, double angle) {

        return rotQtAroundBaseZero(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
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
    public FVector rotQtAroundFixed(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle) {

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
    public FVector rotQtAroundFixed(FVector ref, FVector in, double angle) {

        return rotQtAroundFixed(
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundFixed(FPairPos3D ref, FVector in, double angle) {

        return rotQtAroundFixed(
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundFixedBaseCommon(double hX, double hY, double hZ, FVector in, double angle) {

        return rotQtAroundFixed(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundFixedBaseCommon(FPoint ref, FVector in, double angle) {

        return rotQtAroundFixed(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundFixedBaseCommon(FPos3D ref, FVector in, double angle) {

        return rotQtAroundFixed(
                in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                in, angle
        );
    }

    @Override
    public FVector rotQtAroundFixedBaseZero(double hX, double hY, double hZ, FVector in, double angle) {

        return rotQtAroundFixed(0, 0, 0, hX, hY, hZ, in, angle);
    }

    @Override
    public FVector rotQtAroundFixedBaseZero(FPoint ref, FVector in, double angle) {

        return rotQtAroundFixedBaseZero(ref.getX(), ref.getY(), ref.getZ(), in, angle);
    }

    @Override
    public FVector rotQtAroundFixedBaseZero(FPos3D ref, FVector in, double angle) {

        return rotQtAroundFixedBaseZero(ref.getD0(), ref.getD1(), ref.getD2(), in, angle);
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

        FRotQt qt = core.getRotQt(ref.getRefOrigin().toFPairPos3D(), angle);

        rot(in, qt);
    }

    @Override
    public void rotRgAround(FLine ref, Geometry in, double angle) {
        FVector refOrigin = ref.getRefOrigin();

        if (refOrigin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.toFPoints().forEach(p -> rotRgAround(refOrigin, p, angle));
    }

    @Override
    public void rotQtAround(FRay ref, Geometry in, double angle) {

        if (ref.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        FRotQt qt = core.getRotQt(ref.getRefOrigin().toFPairPos3D(), angle);

        for (FPoint p : in.toFPoints()) {
            if (ref.isProjectable(p)) {
                rotQt(p, qt);
            }
        }
    }

    @Override
    public void rotRgAround(FRay ref, Geometry in, double angle) {
        FVector refOrigin = ref.getRefOrigin();

        if (refOrigin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        for (FPoint p : in.toFPoints()) {
            if (ref.isProjectable(p)) {
                rotRgAround(refOrigin, p, angle);
            }
        }
    }

    @Override
    public void rotQtAround(FSegment ref, Geometry in, double angle) {

        if (ref.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        FRotQt qt = core.getRotQt(ref.getRefOrigin().toFPairPos3D(), angle);

        for (FPoint p : in.toFPoints()) {
            if (ref.isProjectable(p)) {
                rotQt(p, qt);
            }
        }
    }

    @Override
    public void rotRgAround(FSegment ref, Geometry in, double angle) {
        FVector refOrigin = ref.getRefOrigin();

        if (refOrigin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        for (FPoint p : in.toFPoints()) {
            if (ref.isProjectable(p)) {
                rotRgAround(refOrigin, p, angle);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isNearZeroLength(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        boolean posX = Math.abs(bX - hX) < EPSILON;
        boolean posY = Math.abs(bY - hY) < EPSILON;
        boolean posZ = Math.abs(bZ - hZ) < EPSILON;

        return posX && posY && posZ;
    }
}
