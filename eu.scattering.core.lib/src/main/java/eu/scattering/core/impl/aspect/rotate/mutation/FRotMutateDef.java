package eu.scattering.core.impl.aspect.rotate.mutation;

import eu.scattering.core.design.aspect.rotate.mutation.FRotMutate;
import eu.scattering.core.design.aspect.rotate.state.FRotState;
import eu.scattering.core.design.aspect.rotate.state.FRotStateFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;

import java.util.Collection;

import static eu.scattering.core.impl.ScatterCoreConfig.EPSILON;

public class FRotMutateDef implements FRotMutate {
    private final FRotStateFactory state;

    private FRotMutateDef(FRotStateFactory state) {

        this.state = state;
    }

    public static FRotMutate create(FRotStateFactory state) {

        return new FRotMutateDef(state);
    }

    //--------------------------------------------------

    @Override
    public FPoint setAngleRg(FPoint in, double x, double y, double z, double angle) {

        return in.setRgAngle(x, y, z, angle);
    }

    @Override
    public FPoint setAngleRg(FPoint in, FPoint ref, double angle) {

        return in.setRgAngle(ref, angle);
    }

    @Override
    public FPoint setAngleRg(FPoint in, FPos3D ref, double angle) {

        return in.setRgAngle(ref, angle);
    }

    @Override
    public FPoint aroundRg(FPoint in, double x, double y, double z, double angle) {

        return in.rotRgAround(x, y, z, angle);
    }

    @Override
    public FPoint aroundRg(FPoint in, FPoint ref, double angle) {

        return in.rotRgAround(ref, angle);
    }

    @Override
    public FPoint aroundRg(FPoint in, FPos3D ref, double angle) {

        return in.rotRgAround(ref, angle);
    }

    @Override
    public FPoint setAngleQt(FPoint in, double x, double y, double z, double angle) {

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

        FRotState qt = this.state.aroundAxis(axis, angle);

        return apply(in, qt);
    }

    @Override
    public FPoint setAngleQt(FPoint in, FPoint ref, double angle) {

        return setAngleQt(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FPoint setAngleQt(FPoint in, FPos3D ref, double angle) {

        return setAngleQt(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FPoint aroundQt(FPoint in, double x, double y, double z, double angle) {
        FRotState qt = this.state.aroundAxis(x, y, z, angle);

        return apply(in, qt);
    }

    @Override
    public FPoint aroundQt(FPoint in, FPoint ref, double angle) {
        FRotState qt = this.state.aroundAxis(ref.toFPos3D(), angle);

        return apply(in, qt);
    }

    @Override
    public FPoint aroundQt(FPoint in, FPos3D ref, double angle) {
        FRotState qt = this.state.aroundAxis(ref, angle);

        return apply(in, qt);
    }

    @Override
    public FPoint apply(FPoint in, FRotState qt) {

        return rotate(in, qt.getOffset(), qt.getMatrix());
    }

    //--------------------------------------------------

    @Override
    public FVector setAngleRg(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        return in.setRgAngle(bX, bY, bZ, hX, hY, hZ, angle);
    }

    @Override
    public FVector setAngleRg(FVector in, FVector ref, double angle) {

        return in.setRgAngle(ref, angle);
    }

    @Override
    public FVector setAngleRg(FVector in, FPairPos3D ref, double angle) {

        return in.setRgAngle(ref, angle);
    }

    @Override
    public FVector setAngleRgWithCommonBase(FVector in, double hX, double hY, double hZ, double angle) {

        return in.setRgAngleBaseCommon(hX, hY, hZ, angle);
    }

    @Override
    public FVector setAngleRgWithCommonBase(FVector in, FPoint ref, double angle) {

        return in.setRgAngleBaseCommon(ref, angle);
    }

    @Override
    public FVector setAngleRgWithCommonBase(FVector in, FPos3D ref, double angle) {

        return in.setRgAngleBaseCommon(ref, angle);
    }

    @Override
    public FVector setAngleRgAtZeroBase(FVector in, double hX, double hY, double hZ, double angle) {

        return in.setRgAngleBaseZero(hX, hY, hZ, angle);
    }

    @Override
    public FVector setAngleRgAtZeroBase(FVector in, FPoint ref, double angle) {

        return in.setRgAngleBaseZero(ref, angle);
    }

    @Override
    public FVector setAngleRgAtZeroBase(FVector in, FPos3D ref, double angle) {

        return in.setRgAngleBaseZero(ref, angle);
    }

    @Override
    public FPoint aroundRg(FPoint in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        in.subXYZ(bX, bY, bZ);

        aroundRg(in, hX - bX, hY - bY, hZ - bZ, angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FPoint aroundRg(FPoint in, FVector ref, double angle) {

        return aroundRg(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FPoint aroundRg(FPoint in, FPairPos3D ref, double angle) {

        return aroundRg(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector aroundRg(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        return in.rotRgAround(bX, bY, bZ, hX, hY, hZ, angle);
    }

    @Override
    public FVector aroundRg(FVector in, FVector ref, double angle) {

        return in.rotRgAround(ref, angle);
    }

    @Override
    public FVector aroundRg(FVector in, FPairPos3D ref, double angle) {

        return in.rotRgAround(ref, angle);
    }

    @Override
    public FVector aroundRgWithCommonBase(FVector in, double hX, double hY, double hZ, double angle) {

        return in.rotRgAroundBaseCommon(hX, hY, hZ, angle);
    }

    @Override
    public FVector aroundRgWithCommonBase(FVector in, FPoint ref, double angle) {

        return in.rotRgAroundBaseCommon(ref, angle);
    }

    @Override
    public FVector aroundRgWithCommonBase(FVector in, FPos3D ref, double angle) {

        return in.rotRgAroundBaseCommon(ref, angle);
    }

    @Override
    public FVector aroundRgAtZeroBase(FVector in, double hX, double hY, double hZ, double angle) {

        return in.rotRgAroundBaseZero(hX, hY, hZ, angle);
    }

    @Override
    public FVector aroundRgAtZeroBase(FVector in, FPoint ref, double angle) {

        return in.rotRgAroundBaseZero(ref, angle);
    }

    @Override
    public FVector aroundRgAtZeroBase(FVector in, FPos3D ref, double angle) {

        return in.rotRgAroundBaseZero(ref, angle);
    }

    @Override
    public FVector pivotRg(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        return in.rotRgAroundAxis(bX, bY, bZ, hX, hY, hZ, angle);
    }

    @Override
    public FVector pivotRg(FVector in, FVector ref, double angle) {

        return in.rotRgAroundAxis(ref, angle);
    }

    @Override
    public FVector pivotRg(FVector in, FPairPos3D ref, double angle) {

        return in.rotRgAroundAxis(ref, angle);
    }

    @Override
    public FVector pivotRgWithCommonBase(FVector in, double hX, double hY, double hZ, double angle) {

        return in.rotRgAroundAxisBaseCommon(hX, hY, hZ, angle);
    }

    @Override
    public FVector pivotRgWithCommonBase(FVector in, FPoint ref, double angle) {

        return in.rotRgAroundAxisBaseCommon(ref, angle);
    }

    @Override
    public FVector pivotRgWithCommonBase(FVector in, FPos3D ref, double angle) {

        return in.rotRgAroundAxisBaseCommon(ref, angle);
    }

    @Override
    public FVector pivotRgAtZeroBase(FVector in, double hX, double hY, double hZ, double angle) {

        return in.rotRgAroundAxisBaseZero(hX, hY, hZ, angle);
    }

    @Override
    public FVector pivotRgAtZeroBase(FVector in, FPoint ref, double angle) {

        return in.rotRgAroundAxisBaseZero(ref, angle);
    }

    @Override
    public FVector pivotRgAtZeroBase(FVector in, FPos3D ref, double angle) {

        return in.rotRgAroundAxisBaseZero(ref, angle);
    }

    @Override
    public FVector setAngleQt(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

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

        setAngleQt(in.getRefHead(), opX, opY, opZ, angle);

        in.moveBase(memoBX, memoBY, memoBZ);

        return in;
    }

    @Override
    public FVector setAngleQt(FVector in, FVector ref, double angle) {

        return setAngleQt(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FVector setAngleQt(FVector in, FPairPos3D ref, double angle) {

        return setAngleQt(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector setAngleQtWithCommonBase(FVector in, double hX, double hY, double hZ, double angle) {

        return setAngleQt(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                angle
        );
    }

    @Override
    public FVector setAngleQtWithCommonBase(FVector in, FPoint ref, double angle) {

        return setAngleQt(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                angle
        );
    }

    @Override
    public FVector setAngleQtWithCommonBase(FVector in, FPos3D ref, double angle) {

        return setAngleQt(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                angle
        );
    }

    @Override
    public FVector setAngleQtAtZeroBase(FVector in, double hX, double hY, double hZ, double angle) {

        return setAngleQt(in, 0, 0, 0, hX, hY, hZ, angle);
    }

    @Override
    public FVector setAngleQtAtZeroBase(FVector in, FPoint ref, double angle) {

        return setAngleQtAtZeroBase(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FVector setAngleQtAtZeroBase(FVector in, FPos3D ref, double angle) {

        return setAngleQtAtZeroBase(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FVector aroundQt(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        in.subXYZ(bX, bY, bZ);

        aroundQt(in.getRefBase(), hX - bX, hY - bY, hZ - bZ, angle);
        aroundQt(in.getRefHead(), hX - bX, hY - bY, hZ - bZ, angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FVector aroundQt(FVector in, FVector ref, double angle) {

        return aroundQt(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FVector aroundQt(FVector in, FPairPos3D ref, double angle) {

        return aroundQt(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector aroundQtWithCommonBase(FVector in, double hX, double hY, double hZ, double angle) {

        return aroundQt(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                angle
        );
    }

    @Override
    public FVector aroundQtWithCommonBase(FVector in, FPoint ref, double angle) {

        return aroundQt(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                angle
        );
    }

    @Override
    public FVector aroundQtWithCommonBase(FVector in, FPos3D ref, double angle) {

        return aroundQt(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                angle
        );
    }

    @Override
    public FVector aroundQtAtZeroBase(FVector in, double hX, double hY, double hZ, double angle) {

        return aroundQt(in, 0, 0, 0, hX, hY, hZ, angle);
    }

    @Override
    public FVector aroundQtAtZeroBase(FVector in, FPoint ref, double angle) {

        return aroundQtAtZeroBase(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FVector aroundQtAtZeroBase(FVector in, FPos3D ref, double angle) {

        return aroundQtAtZeroBase(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FPoint aroundQt(FPoint in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        in.subXYZ(bX, bY, bZ);

        aroundQt(in, hX - bX, hY - bY, hZ - bZ, angle);

        in.addXYZ(bX, bY, bZ);

        return in;
    }

    @Override
    public FPoint aroundQt(FPoint in, FVector ref, double angle) {

        return aroundQt(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FPoint aroundQt(FPoint in, FPairPos3D ref, double angle) {

        return aroundQt(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector pivotQt(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        if (in.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the provided FVector is not defined");
        }

        double memoBX = in.getBaseX();
        double memoBY = in.getBaseY();
        double memoBZ = in.getBaseZ();

        in.moveBaseToCenter();

        apply(in, this.state.aroundAxis(hX - bX, hY - bY, hZ - bZ, angle));

        in.moveBase(memoBX, memoBY, memoBZ);

        return in;
    }

    @Override
    public FVector pivotQt(FVector in, FVector ref, double angle) {

        return pivotQt(
                in, ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle
        );
    }

    @Override
    public FVector pivotQt(FVector in, FPairPos3D ref, double angle) {

        return pivotQt(
                in, ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector pivotQtWithCommonBase(FVector in, double hX, double hY, double hZ, double angle) {

        return pivotQt(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                hX, hY, hZ,
                angle
        );
    }

    @Override
    public FVector pivotQtWithCommonBase(FVector in, FPoint ref, double angle) {

        return pivotQt(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getX(), ref.getY(), ref.getZ(),
                angle
        );
    }

    @Override
    public FVector pivotQtWithCommonBase(FVector in, FPos3D ref, double angle) {

        return pivotQt(
                in, in.getBaseX(), in.getBaseY(), in.getBaseZ(),
                ref.getD0(), ref.getD1(), ref.getD2(),
                angle
        );
    }

    @Override
    public FVector pivotQAtZeroBase(FVector in, double hX, double hY, double hZ, double angle) {

        return pivotQt(in, 0, 0, 0, hX, hY, hZ, angle);
    }

    @Override
    public FVector pivotQAtZeroBase(FVector in, FPoint ref, double angle) {

        return pivotQAtZeroBase(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FVector pivotQAtZeroBase(FVector in, FPos3D ref, double angle) {

        return pivotQAtZeroBase(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FVector apply(FVector in, FRotState qt) {

        apply(in.getRefBase(), qt);
        apply(in.getRefHead(), qt);

        return in;
    }

    //--------------------------------------------------

    @Override
    public void aroundQt(Geometry in, FVector ref, double angle) {

        if (ref.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        FRotState qt = this.state.aroundAxis(ref.toFPairPos3D(), angle);

        apply(in, qt);
    }

    @Override
    public void aroundRg(Geometry in, FVector ref, double angle) {

        if (ref.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.toFPoints().forEach(p -> aroundRg(p, ref, angle));
    }

    @Override
    public void aroundQt(Geometry in, FLine ref, double angle) {

        if (ref.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        FRotState qt = this.state.aroundAxis(ref.getRefOrigin().toFPairPos3D(), angle);

        apply(in, qt);
    }

    @Override
    public void aroundRg(Geometry in, FLine ref, double angle) {
        FVector refOrigin = ref.getRefOrigin();

        if (refOrigin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.toFPoints().forEach(p -> aroundRg(p, refOrigin, angle));
    }

    @Override
    public void aroundQt(Geometry in, FRay ref, double angle) {

        if (ref.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        FRotState qt = this.state.aroundAxis(ref.getRefOrigin().toFPairPos3D(), angle);

        for (FPoint p : in.toFPoints()) {
            if (ref.isProjectable(p)) {
                apply(p, qt);
            }
        }
    }

    @Override
    public void aroundRg(Geometry in, FRay ref, double angle) {
        FVector refOrigin = ref.getRefOrigin();

        if (refOrigin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        for (FPoint p : in.toFPoints()) {
            if (ref.isProjectable(p)) {
                aroundRg(p, refOrigin, angle);
            }
        }
    }

    @Override
    public void aroundQt(Geometry in, FSegment ref, double angle) {

        if (ref.getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        FRotState qt = this.state.aroundAxis(ref.getRefOrigin().toFPairPos3D(), angle);

        for (FPoint p : in.toFPoints()) {
            if (ref.isProjectable(p)) {
                apply(p, qt);
            }
        }
    }

    @Override
    public void aroundRg(Geometry in, FSegment ref, double angle) {
        FVector refOrigin = ref.getRefOrigin();

        if (refOrigin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        for (FPoint p : in.toFPoints()) {
            if (ref.isProjectable(p)) {
                aroundRg(p, refOrigin, angle);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FAggregate aroundRg(FAggregate in, double x, double y, double z, double angle) {

        for (FPoint fPoint : in.getRefParticles().toFPoints()) {
            aroundRg(fPoint, x, y, z, angle);
        }

        return in;
    }

    @Override
    public FAggregate aroundRg(FAggregate in, FPoint ref, double angle) {

        return aroundRg(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FAggregate aroundRg(FAggregate in, FPos3D ref, double angle) {

        return aroundRg(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FAggregate aroundRg(FAggregate in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        for (FPoint fPoint : in.getRefParticles().toFPoints()) {
            aroundRg(fPoint, bX, bY, bZ, hX, hY, hZ, angle);
        }

        return in;
    }

    @Override
    public FAggregate aroundRg(FAggregate in, FVector ref, double angle) {

        return aroundRg(in,
                ref.getBaseX(), ref.getBaseY(), ref.getBaseZ(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle);
    }

    @Override
    public FAggregate aroundRg(FAggregate in, FPairPos3D ref, double angle) {

        return aroundRg(in,
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle);
    }

    @Override
    public FAggregate aroundQt(FAggregate in, double x, double y, double z, double angle) {

        for (FPoint fPoint : in.getRefParticles().toFPoints()) {
            aroundQt(fPoint, x, y, z, angle);
        }

        return in;
    }

    @Override
    public FAggregate aroundQt(FAggregate in, FPoint ref, double angle) {

        return aroundQt(in, ref.getX(), ref.getY(), ref.getZ(), angle);
    }

    @Override
    public FAggregate aroundQt(FAggregate in, FPos3D ref, double angle) {

        return aroundQt(in, ref.getD0(), ref.getD1(), ref.getD2(), angle);
    }

    @Override
    public FAggregate aroundQt(FAggregate in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        for (FPoint fPoint : in.getRefParticles().toFPoints()) {
            aroundQt(fPoint, bX, bY, bZ, hX, hY, hZ, angle);
        }

        return in;
    }

    @Override
    public FAggregate aroundQt(FAggregate in, FVector ref, double angle) {

        return aroundQt(in,
                ref.getBaseX(), ref.getBaseY(), ref.getBaseY(),
                ref.getHeadX(), ref.getHeadY(), ref.getHeadZ(),
                angle);
    }

    @Override
    public FAggregate aroundQt(FAggregate in, FPairPos3D ref, double angle) {

        return aroundQt(in,
                ref.getPosA().getD0(), ref.getPosA().getD1(), ref.getPosA().getD2(),
                ref.getPosB().getD0(), ref.getPosB().getD1(), ref.getPosB().getD2(),
                angle);
    }

    @Override
    public FAggregate apply(FAggregate in, FRotState qt) {

        for (FPoint fPoint : in.getRefParticles().toFPoints()) {
            rotate(fPoint, qt.getOffset(), qt.getMatrix());
        }

        return in;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Geometry apply(Geometry in, FRotState qt) {

        FMatrix3x3D matrix = qt.getMatrix();
        FPos3D offset = qt.getOffset();

        Collection<FPoint> assembly = in.toFPoints();

        for(FPoint point : assembly) {
            rotate(point, offset, matrix);
        }

        return in;
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint rotate(FPoint point, FPos3D offset, FMatrix3x3D matrix) {

        point.sub(offset);
        point.mul(matrix);
        point.add(offset);

        return point;
    }
}