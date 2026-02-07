package eu.scattering.core.impl.aspect.rotate;

import eu.scattering.core.design.aspect.rotate.FRotAspect;
import eu.scattering.core.design.aspect.rotate.generator.FRotGenerator;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos3D;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;
import eu.scattering.core.design.transfer.primitive.FMatrix3x3D;
import eu.scattering.core.design.transfer.complex.FRotQt;

import java.util.Collection;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FRotAspectDef implements FRotAspect {
    private final FRotGenerator core;

    private FRotAspectDef(FRotGenerator core) {

        this.core = core;
    }

    public static FRotAspect create(FRotGenerator core) {

        return new FRotAspectDef(core);
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

        return in.setRgAngle(x, y, z, angle);
    }

    @Override
    public FPoint setRgAngle(FPoint in, FPoint ref, double angle) {

        return in.setRgAngle(ref, angle);
    }

    @Override
    public FPoint setRgAngle(FPoint in, FPos3D ref, double angle) {

        return in.setRgAngle(ref, angle);
    }

    @Override
    public FPoint rotRgAround(FPoint in, double x, double y, double z, double angle) {

        return in.rotRgAround(x, y, z, angle);
    }

    @Override
    public FPoint rotRgAround(FPoint in, FPoint ref, double angle) {

        return in.rotRgAround(ref, angle);
    }

    @Override
    public FPoint rotRgAround(FPoint in, FPos3D ref, double angle) {

        return in.rotRgAround(ref, angle);
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

        return in.setRgAngle(bX, bY, bZ, hX, hY, hZ, angle);
    }

    @Override
    public FVector setRgAngle(FVector in, FVector ref, double angle) {

        return in.setRgAngle(ref, angle);
    }

    @Override
    public FVector setRgAngle(FVector in, FPairPos3D ref, double angle) {

        return in.setRgAngle(ref, angle);
    }

    @Override
    public FVector setRgAngleBaseCommon(FVector in, double hX, double hY, double hZ, double angle) {

        return in.setRgAngleBaseCommon(hX, hY, hZ, angle);
    }

    @Override
    public FVector setRgAngleBaseCommon(FVector in, FPoint ref, double angle) {

        return in.setRgAngleBaseCommon(ref, angle);
    }

    @Override
    public FVector setRgAngleBaseCommon(FVector in, FPos3D ref, double angle) {

        return in.setRgAngleBaseCommon(ref, angle);
    }

    @Override
    public FVector setRgAngleBaseZero(FVector in, double hX, double hY, double hZ, double angle) {

        return in.setRgAngleBaseZero(hX, hY, hZ, angle);
    }

    @Override
    public FVector setRgAngleBaseZero(FVector in, FPoint ref, double angle) {

        return in.setRgAngleBaseZero(ref, angle);
    }

    @Override
    public FVector setRgAngleBaseZero(FVector in, FPos3D ref, double angle) {

        return in.setRgAngleBaseZero(ref, angle);
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

        return in.rotRgAround(bX, bY, bZ, hX, hY, hZ, angle);
    }

    @Override
    public FVector rotRgAround(FVector in, FVector ref, double angle) {

        return in.rotRgAround(ref, angle);
    }

    @Override
    public FVector rotRgAround(FVector in, FPairPos3D ref, double angle) {

        return in.rotRgAround(ref, angle);
    }

    @Override
    public FVector rotRgAroundBaseCommon(FVector in, double hX, double hY, double hZ, double angle) {

        return in.rotRgAroundBaseCommon(hX, hY, hZ, angle);
    }

    @Override
    public FVector rotRgAroundBaseCommon(FVector in, FPoint ref, double angle) {

        return in.rotRgAroundBaseCommon(ref, angle);
    }

    @Override
    public FVector rotRgAroundBaseCommon(FVector in, FPos3D ref, double angle) {

        return in.rotRgAroundBaseCommon(ref, angle);
    }

    @Override
    public FVector rotRgAroundBaseZero(FVector in, double hX, double hY, double hZ, double angle) {

        return in.rotRgAroundBaseZero(hX, hY, hZ, angle);
    }

    @Override
    public FVector rotRgAroundBaseZero(FVector in, FPoint ref, double angle) {

        return in.rotRgAroundBaseZero(ref, angle);
    }

    @Override
    public FVector rotRgAroundBaseZero(FVector in, FPos3D ref, double angle) {

        return in.rotRgAroundBaseZero(ref, angle);
    }

    @Override
    public FVector rotRgAroundAxis(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        return in.rotRgAroundAxis(bX, bY, bZ, hX, hY, hZ, angle);
    }

    @Override
    public FVector rotRgAroundAxis(FVector in, FVector ref, double angle) {

        return in.rotRgAroundAxis(ref, angle);
    }

    @Override
    public FVector rotRgAroundAxis(FVector in, FPairPos3D ref, double angle) {

        return in.rotRgAroundAxis(ref, angle);
    }

    @Override
    public FVector rotRgAroundAxisBaseCommon(FVector in, double hX, double hY, double hZ, double angle) {

        return in.rotRgAroundAxisBaseCommon(hX, hY, hZ, angle);
    }

    @Override
    public FVector rotRgAroundAxisBaseCommon(FVector in, FPoint ref, double angle) {

        return in.rotRgAroundAxisBaseCommon(ref, angle);
    }

    @Override
    public FVector rotRgAroundAxisBaseCommon(FVector in, FPos3D ref, double angle) {

        return in.rotRgAroundAxisBaseCommon(ref, angle);
    }

    @Override
    public FVector rotRgAroundAxisBaseZero(FVector in, double hX, double hY, double hZ, double angle) {

        return in.rotRgAroundAxisBaseZero(hX, hY, hZ, angle);
    }

    @Override
    public FVector rotRgAroundAxisBaseZero(FVector in, FPoint ref, double angle) {

        return in.rotRgAroundAxisBaseZero(ref, angle);
    }

    @Override
    public FVector rotRgAroundAxisBaseZero(FVector in, FPos3D ref, double angle) {

        return in.rotRgAroundAxisBaseZero(ref, angle);
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
    public void rotQtAround(Geometry in, FVector ref, double angle) {

        if (ref.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        FRotQt qt = core.getRotQt(ref.toFPairPos3D(), angle);

        rot(in, qt);
    }

    @Override
    public void rotRgAround(Geometry in, FVector ref, double angle) {

        if (ref.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.toFPoints().forEach(p -> rotRgAround(p, ref, angle));
    }

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

    @Override
    public FAggregate rotRgAround(FAggregate in, double x, double y, double z, double angle) {

        for (FPoint fPoint : in.getRefParticles().toFPoints()) {
            rotRgAround(fPoint, x, y, z, angle);
        }

        return in;
    }

    @Override
    public FAggregate rotRgAround(FAggregate in, FPoint ref, double angle) {

        return rotRgAround(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FAggregate rotRgAround(FAggregate in, FPos3D ref, double angle) {

        return rotRgAround(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FAggregate rotRgAround(FAggregate in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        for (FPoint fPoint : in.getRefParticles().toFPoints()) {
            rotRgAround(fPoint, bX, bY, bZ, hX, hY, hZ, angle);
        }

        return in;
    }

    @Override
    public FAggregate rotRgAround(FAggregate in, FVector ref, double angle) {

        return rotRgAround(in,
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle);
    }

    @Override
    public FAggregate rotRgAround(FAggregate in, FPairPos3D ref, double angle) {

        return rotRgAround(in,
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle);
    }

    @Override
    public FAggregate rotQtAround(FAggregate in, double x, double y, double z, double angle) {

        for (FPoint fPoint : in.getRefParticles().toFPoints()) {
            rotQtAround(fPoint, x, y, z, angle);
        }

        return in;
    }

    @Override
    public FAggregate rotQtAround(FAggregate in, FPoint ref, double angle) {

        return rotQtAround(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FAggregate rotQtAround(FAggregate in, FPos3D ref, double angle) {

        return rotQtAround(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FAggregate rotQtAround(FAggregate in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        for (FPoint fPoint : in.getRefParticles().toFPoints()) {
            rotQtAround(fPoint, bX, bY, bZ, hX, hY, hZ, angle);
        }

        return in;
    }

    @Override
    public FAggregate rotQtAround(FAggregate in, FVector ref, double angle) {

        return rotQtAround(in,
                ref.getBaseX(), ref.getBaseY(), ref.getBaseY(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle);
    }

    @Override
    public FAggregate rotQtAround(FAggregate in, FPairPos3D ref, double angle) {

        return rotQtAround(in,
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle);
    }

    @Override
    public FAggregate rotQt(FAggregate in, FRotQt qt) {

        for (FPoint fPoint : in.getRefParticles().toFPoints()) {
            rot(fPoint, qt.getOffset(), qt.getMatrix());
        }

        return in;
    }
}
