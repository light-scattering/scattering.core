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
    public FPoint setRgAngle(FPoint in, double x, double y, double z, double angle) {
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
    public FPoint setRgAngle(FPoint in, FPoint ref, double angle) {

        return setRgAngle(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FPoint setRgAngle(FPoint in, FPos3D ref, double angle) {

        return setRgAngle(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FPoint rotRgAround(FPoint in, double x, double y, double z, double angle) {

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
    public FPoint rotRgAround(FPoint in, FPoint ref, double angle) {

        return rotRgAround(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FPoint rotRgAround(FPoint in, FPos3D ref, double angle) {

        return rotRgAround(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FPoint setQtAngle(FPoint in, double x, double y, double z, double angle) {

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
    public FPoint setQtAngle(FPoint in, FPoint ref, double angle) {

        return setQtAngle(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FPoint setQtAngle(FPoint in, FPos3D ref, double angle) {

        return setQtAngle(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FPoint rotQtAround(FPoint in, double x, double y, double z, double angle) {
        FRotQt qt = core.getRotQt(x, y, z, angle);

        return rotQt(in, qt);
    }

    @Override
    public FPoint rotQtAround(FPoint in, FPoint ref, double angle) {
        FRotQt qt = core.getRotQt(ref.toFPos3D(), angle);

        return rotQt(in, qt);
    }

    @Override
    public FPoint rotQtAround(FPoint in, FPos3D ref, double angle) {
        FRotQt qt = core.getRotQt(ref, angle);

        return rotQt(in, qt);
    }

    @Override
    public FPoint rotQt(FPoint in, FRotQt qt) {

        return rot(in, qt.getOffset(), qt.getMatrix());
    }

    //--------------------------------------------------

    @Override
    public FVector setRgAngle(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

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
        setRgAngle(in.getRefHead(), zeroAX, zeroAY, zeroAZ, angle);
        in.moveBase(memoOBX, memoOBY, memoOBZ);

        return in;
    }

    @Override
    public FVector setRgAngle(FVector in, FVector ref, double angle) {

        return setRgAngle(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FVector setRgAngle(FVector in, FPairPos3D ref, double angle) {

        return setRgAngle(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector setRgAngleBaseCommon(FVector in, double hX, double hY, double hZ, double angle) {

        return setRgAngle(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                angle
        );
    }

    @Override
    public FVector setRgAngleBaseCommon(FVector in, FPoint ref, double angle) {

        return setRgAngle(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                angle
        );
    }

    @Override
    public FVector setRgAngleBaseCommon(FVector in, FPos3D ref, double angle) {

        return setRgAngle(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                angle
        );
    }

    @Override
    public FVector setRgAngleBaseZero(FVector in, double hX, double hY, double hZ, double angle) {

        return setRgAngle(in, 0, 0, 0, hX, hY, hZ, angle);
    }

    @Override
    public FVector setRgAngleBaseZero(FVector in, FPoint ref, double angle) {

        return setRgAngleBaseZero(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FVector setRgAngleBaseZero(FVector in, FPos3D ref, double angle) {

        return setRgAngleBaseZero(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FPoint rotRgAround(FPoint in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        in.subXYZ(bX, bY, bZ);

        rotRgAround(in, hX - bX, hY - bY, hZ - bZ, angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FPoint rotRgAround(FPoint in, FVector ref, double angle) {

        return rotRgAround(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FPoint rotRgAround(FPoint in, FPairPos3D ref, double angle) {

        return rotRgAround(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector rotRgAround(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        in.subXYZ(bX, bY, bZ);

        rotRgAround(in.getRefBase(), hX - bX, hY - bY, hZ - bZ, angle);
        rotRgAround(in.getRefHead(), hX - bX, hY - bY, hZ - bZ, angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FVector rotRgAround(FVector in, FVector ref, double angle) {

        return rotRgAround(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FVector rotRgAround(FVector in, FPairPos3D ref, double angle) {

        return rotRgAround(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector rotRgAroundBaseCommon(FVector in, double hX, double hY, double hZ, double angle) {

        return rotRgAround(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                angle
        );
    }

    @Override
    public FVector rotRgAroundBaseCommon(FVector in, FPoint ref, double angle) {

        return rotRgAround(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                angle
        );
    }

    @Override
    public FVector rotRgAroundBaseCommon(FVector in, FPos3D ref, double angle) {

        return rotRgAround(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                angle
        );
    }

    @Override
    public FVector rotRgAroundBaseZero(FVector in, double hX, double hY, double hZ, double angle) {

        return rotRgAround(in, 0, 0, 0, hX, hY, hX, angle);
    }

    @Override
    public FVector rotRgAroundBaseZero(FVector in, FPoint ref, double angle) {

        return rotRgAroundBaseZero(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FVector rotRgAroundBaseZero(FVector in, FPos3D ref, double angle) {

        return rotRgAroundBaseZero(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FVector rotRgAroundAxis(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        if (in.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the provided FVector is not defined");
        }

        double memoBX = in.getBaseX();
        double memoBY = in.getBaseY();
        double memoBZ = in.getBaseZ();

        in.moveBaseToCenter();

        rotRgAround(in.getRefHead(), hX - bX, hY - bY, hZ - bZ, angle);

        in.moveBase(memoBX, memoBY, memoBZ);

        return in;
    }

    @Override
    public FVector rotRgAroundAxis(FVector in, FVector ref, double angle) {

        return rotRgAroundAxis(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FVector rotRgAroundAxis(FVector in, FPairPos3D ref, double angle) {

        return rotRgAroundAxis(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector rotRgAroundAxisBaseCommon(FVector in, double hX, double hY, double hZ, double angle) {

        return rotRgAroundAxis(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                angle
        );
    }

    @Override
    public FVector rotRgAroundAxisBaseCommon(FVector in, FPoint ref, double angle) {

        return rotRgAroundAxis(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                angle
        );
    }

    @Override
    public FVector rotRgAroundAxisBaseCommon(FVector in, FPos3D ref, double angle) {

        return rotRgAroundAxis(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                angle
        );
    }

    @Override
    public FVector rotRgAroundAxisBaseZero(FVector in, double hX, double hY, double hZ, double angle) {

        return rotRgAroundAxis(in, 0, 0, 0, hX, hY, hZ, angle);
    }

    @Override
    public FVector rotRgAroundAxisBaseZero(FVector in, FPoint ref, double angle) {
        return rotRgAroundAxisBaseZero(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FVector rotRgAroundAxisBaseZero(FVector in, FPos3D ref, double angle) {

        return rotRgAroundAxisBaseZero(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FVector setQtAngle(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

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

        setQtAngle(in.getRefHead(), opX, opY, opZ, angle);

        in.moveBase(memoBX, memoBY, memoBZ);

        return in;
    }

    @Override
    public FVector setQtAngle(FVector in, FVector ref, double angle) {

       return setQtAngle(
               in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
               ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
               angle
       );
    }

    @Override
    public FVector setQtAngle(FVector in, FPairPos3D ref, double angle) {

        return setQtAngle(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector setQtAngleBaseCommon(FVector in, double hX, double hY, double hZ, double angle) {

        return setQtAngle(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                angle
        );
    }

    @Override
    public FVector setQtAngleBaseCommon(FVector in, FPoint ref, double angle) {

        return setQtAngle(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                angle
        );
    }

    @Override
    public FVector setQtAngleBaseCommon(FVector in, FPos3D ref, double angle) {

        return setQtAngle(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                angle
        );
    }

    @Override
    public FVector setQtAngleBaseZero(FVector in, double hX, double hY, double hZ, double angle) {

        return setQtAngle(in, 0, 0, 0, hX, hY, hZ, angle);
    }

    @Override
    public FVector setQtAngleBaseZero(FVector in, FPoint ref, double angle) {

        return setQtAngleBaseZero(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FVector setQtAngleBaseZero(FVector in, FPos3D ref, double angle) {

        return setQtAngleBaseZero(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FVector rotQtAround(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        in.subXYZ(bX, bY, bZ);

        rotQtAround(in.getRefBase(), hX - bX, hY - bY, hZ - bZ, angle);
        rotQtAround(in.getRefHead(), hX - bX, hY - bY, hZ - bZ, angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FVector rotQtAround(FVector in, FVector ref, double angle) {

        return rotQtAround(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FVector rotQtAround(FVector in, FPairPos3D ref, double angle) {

        return rotQtAround(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector rotQtAroundBaseCommon(FVector in, double hX, double hY, double hZ, double angle) {

        return rotQtAround(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                angle
        );
    }

    @Override
    public FVector rotQtAroundBaseCommon(FVector in, FPoint ref, double angle) {

        return rotQtAround(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                angle
        );
    }

    @Override
    public FVector rotQtAroundBaseCommon(FVector in, FPos3D ref, double angle) {

        return rotQtAround(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                angle
        );
    }

    @Override
    public FVector rotQtAroundBaseZero(FVector in, double hX, double hY, double hZ, double angle) {

        return rotQtAround(in, 0, 0, 0, hX, hY, hZ, angle);
    }

    @Override
    public FVector rotQtAroundBaseZero(FVector in, FPoint ref, double angle) {

        return rotQtAroundBaseZero(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FVector rotQtAroundBaseZero(FVector in, FPos3D ref, double angle) {

        return rotQtAroundBaseZero(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FPoint rotQtAround(FPoint in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        in.subXYZ(bX, bY, bZ);

        rotQtAround(in, hX - bX, hY - bY, hZ - bZ, angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FPoint rotQtAround(FPoint in, FVector ref, double angle) {

        return rotQtAround(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FPoint rotQtAround(FPoint in, FPairPos3D ref, double angle) {

        return rotQtAround(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector rotQtAroundAxis(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

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
    public FVector rotQtAroundAxis(FVector in, FVector ref, double angle) {

        return rotQtAroundAxis(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FVector rotQtAroundAxis(FVector in, FPairPos3D ref, double angle) {

        return rotQtAroundAxis(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector rotQtAroundAxisBaseCommon(FVector in, double hX, double hY, double hZ, double angle) {

        return rotQtAroundAxis(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                angle
        );
    }

    @Override
    public FVector rotQtAroundAxisBaseCommon(FVector in, FPoint ref, double angle) {

        return rotQtAroundAxis(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                angle
        );
    }

    @Override
    public FVector rotQtAroundAxisBaseCommon(FVector in, FPos3D ref, double angle) {

        return rotQtAroundAxis(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                angle
        );
    }

    @Override
    public FVector rotQtAroundAxisBaseZero(FVector in, double hX, double hY, double hZ, double angle) {

        return rotQtAroundAxis(in, 0, 0, 0, hX, hY, hZ, angle);
    }

    @Override
    public FVector rotQtAroundAxisBaseZero(FVector in, FPoint ref, double angle) {

        return rotQtAroundAxisBaseZero(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FVector rotQtAroundAxisBaseZero(FVector in, FPos3D ref, double angle) {

        return rotQtAroundAxisBaseZero(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FVector rotQt(FVector in, FRotQt qt) {

        rotQt(in.getRefBase(), qt);
        rotQt(in.getRefHead(), qt);

        return in;
    }

    //--------------------------------------------------

    @Override
    public void rotQtAround(Geometry in, FLine ref, double angle) {

        if (ref.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        FRotQt qt = core.getRotQt(ref.getRefOrigin().toFPairPos3D(), angle);

        rot(in, qt);
    }

    @Override
    public void rotRgAround(Geometry in, FLine ref, double angle) {
        FVector refOrigin = ref.getRefOrigin();

        if (refOrigin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.toFPoints().forEach(p -> rotRgAround(p, refOrigin, angle));
    }

    @Override
    public void rotQtAround(Geometry in, FRay ref, double angle) {

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
    public void rotRgAround(Geometry in, FRay ref, double angle) {
        FVector refOrigin = ref.getRefOrigin();

        if (refOrigin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        for (FPoint p : in.toFPoints()) {
            if (ref.isProjectable(p)) {
                rotRgAround(p, refOrigin, angle);
            }
        }
    }

    @Override
    public void rotQtAround(Geometry in, FSegment ref, double angle) {

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
    public void rotRgAround(Geometry in, FSegment ref, double angle) {
        FVector refOrigin = ref.getRefOrigin();

        if (refOrigin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        for (FPoint p : in.toFPoints()) {
            if (ref.isProjectable(p)) {
                rotRgAround(p, refOrigin, angle);
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
