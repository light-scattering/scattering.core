package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneHelper;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;

import java.util.List;
import java.util.Optional;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FPlaneHelperDef implements FPlaneHelper {
    private final GeometryFactory factory;

    private FPlaneHelperDef(GeometryFactory factory) {

        this.factory = factory;
    }

    public static FPlaneHelper get(GeometryFactory factory) {

        return new FPlaneHelperDef(factory);
    }
    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isPartOf(FVector origin, double x, double y, double z) {

        return isPartOf(origin, this.factory.getFPoint(x, y, z));
    }

    @Override
    public boolean isPartOf(FVector origin, FPoint arg) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return isUnitPartOf(origin, arg);
    }

    @Override
    public boolean isPartOf(FVector origin, FPos3D arg) {

        return isPartOf(origin, arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public boolean isPartOf(FVector origin, Geometry arg) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return arg.toFPoints().stream()
                .allMatch(e -> isUnitPartOf(origin, e));
    }

    @Override
    public boolean isPartOf(FVector origin, double x, double y, double z, double epsilon) {

        return isPartOf(origin, this.factory.getFPoint(x, y, z), epsilon);
    }

    @Override
    public boolean isPartOf(FVector origin, FPoint arg, double epsilon) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return isUnitPartOf(origin, arg, epsilon);
    }

    @Override
    public boolean isPartOf(FVector origin, FPos3D arg, double epsilon) {

        return isPartOf(origin, arg.getD0(), arg.getD1(), arg.getD2(), epsilon);
    }

    @Override
    public boolean isPartOf(FVector origin, Geometry arg, double epsilon) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return arg.toFPoints().stream()
                .allMatch(e -> isUnitPartOf(origin, e, epsilon));
    }

    @Override
    public double getDistance(FVector origin, double x, double y, double z) {

        return getDistance(origin, this.factory.getFPoint(x, y, z));
    }

    @Override
    public double getDistance(FVector origin, FPoint arg) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return getUnitDistance(origin, arg);
    }

    @Override
    public double getDistance(FVector origin, FPos3D arg) {

        return getDistance(origin, arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPos3D setDistance(FVector origin, double x, double y, double z, double distance) {
        FPoint fPoint = this.factory.getFPoint(x, y, z);

        setDistance(origin, fPoint, distance);

        return fPoint.toFPos3D();
    }

    @Override
    public FPos3D setDistance(FVector origin, FPos3D arg, double distance) {

        return setDistance(origin, arg.getD0(), arg.getD1(), arg.getD2(), distance);
    }

    @Override
    public void setDistance(FVector origin, FPoint in, double distance) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        setUnitDistance(origin, in, distance);
    }

    @Override
    public void setDistance(FVector origin, Geometry in, double distance) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.toFPoints()
                .forEach(p -> setUnitDistance(origin, p, distance));
    }

    @Override
    public FPos3D project(FVector origin, double x, double y, double z) {
        FPoint fPoint = this.factory.getFPoint(x, y, z);

        project(origin, fPoint);

        return fPoint.toFPos3D();
    }

    @Override
    public FPos3D project(FVector origin, FPos3D arg) {

        return project(origin, arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public void project(FVector origin, FPoint in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        projectUnit(origin, in);
    }

    @Override
    public void project(FVector origin, Geometry in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.toFPoints()
                .forEach(e -> projectUnit(origin, e));
    }

    @Override
    public FPos3D reflect(FVector origin, double x, double y, double z) {
        FPoint fPoint = this.factory.getFPoint(x, y, z);

        reflect(origin, fPoint);

        return fPoint.toFPos3D();
    }

    @Override
    public FPos3D reflect(FVector origin, FPos3D arg) {

        return reflect(origin, arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public void reflect(FVector origin, FPoint in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        reflectUnit(origin, in);
    }

    @Override
    public void reflect(FVector origin, Geometry in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.toFPoints()
                .forEach(e -> reflectUnit(origin, e));
    }

    @Override
    public void setOrigin(FVector in, FPoint ptBase, FPoint ptA, FPoint ptB) {

        in.set(ptBase, ptA).setCrossProductBaseCommon(ptB);
    }

    @Override
    public boolean isSamePlane(FVector origin, FVector arg) {

        return origin.isCollinear(arg) && isPartOf(origin, arg.getRefBase());
    }

    @Override
    public boolean isCut(FVector origin, Geometry arg) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        List<Boolean> isInHalfSpace = arg.toFPoints().stream()
                .map(e -> isUnitInHalfSpace(origin, e))
                .toList();

        boolean conditionTrue = isInHalfSpace.stream().anyMatch(e -> e);
        boolean conditionFalse = isInHalfSpace.stream().anyMatch(e -> !e);

        return conditionTrue && conditionFalse;
    }

    @Override
    public boolean isOnSide(FVector origin, FPoint arg) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return isUnitInHalfSpace(origin, arg);
    }

    @Override
    public boolean isOnSide(FVector origin, Geometry arg) {
        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return arg.toFPoints().stream()
                .allMatch(e -> isUnitInHalfSpace(origin, e));
    }

    @Override
    public Optional<FPoint> getFPointAtIntersection(FVector origin, FVector arg) {
        if (origin.isOrthogonal(arg)) {
            return Optional.empty();
        }

        FPoint result = this.factory.getFPoint();

        FVector u = origin.copy();
        FVector v = arg.copy();

        double aBX = u.getBaseX();
        double aBY = u.getBaseY();
        double aBZ = u.getBaseZ();
        double bBX = v.getBaseX();
        double bBY = v.getBaseY();
        double bBZ = v.getBaseZ();

        FPoint aHead = u.moveBaseToCenter().normalize().getRefHead();
        FPoint bHead = v.moveBaseToCenter().normalize().getRefHead();

        double dividend = aHead.getDotProduct(aBX - bBX, aBY - bBY, aBZ - bBZ);
        double divisor = aHead.getDotProduct(bHead);
        double distance = dividend / divisor;

        v.moveBase(bBX, bBY, bBZ).setMagnitude(distance);

        result.set(v.getRefHead());

        return Optional.of(result);
    }

    @Override
    public Optional<FVector> getFLineAtIntersection(FVector origin, FVector arg) {

        if (origin.isCollinear(arg)) {
            return Optional.empty();
        }

        FVector resultOrigin = this.factory.getFVector();

        FPoint resBase = resultOrigin.getRefBase();
        FPoint resHead = resultOrigin.getRefHead();

        FVector u = origin.copy();
        FVector v = arg.copy();

        double aBX = u.getBaseX();
        double aBY = u.getBaseY();
        double aBZ = u.getBaseZ();
        double bBX = v.getBaseX();
        double bBY = v.getBaseY();
        double bBZ = v.getBaseZ();

        FPoint aHead = u.moveBaseToCenter().getRefHead();
        FPoint bHead = v.moveBaseToCenter().getRefHead() ;

        resBase.set(aHead);
        resHead.set(bHead);

        double d1 = -aHead.getDotProduct(aBX, aBY, aBZ);
        double d2 = -bHead.getDotProduct(bBX, bBY, bBZ);

        aHead.setCrossProduct(bHead);

        double d3 = aHead.getDotProduct(aHead);

        resHead.mulFactor(d1);

        resBase.mulFactor(d2);
        resBase.subXYZ(resHead);
        resBase.setCrossProduct(aHead);
        resBase.divFactor(d3);

        aHead.addXYZ(resBase);

        resHead.set(aHead);

        return Optional.of(resultOrigin);
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isUnitPartOf(FVector origin, FPoint arg) {

        return getUnitDistance(origin, arg) < EPSILON;
    }

    private boolean isUnitPartOf(FVector origin, FPoint arg, double epsilon) {

        return getUnitDistance(origin, arg) < epsilon;
    }

    private double getUnitDistance(FVector origin, FPoint arg) {
        double originMag = origin.getMagnitude();

        double headX = origin.getBaseX() - arg.getX();
        double headY = origin.getBaseY() - arg.getY();
        double headZ = origin.getBaseZ() - arg.getZ();

        double opX = (origin.getHeadX() - origin.getBaseX()) / originMag;
        double opY = (origin.getHeadY() - origin.getBaseY()) / originMag;
        double opZ = (origin.getHeadZ() - origin.getBaseZ()) / originMag;

        double dotProduct = (headX * opX) + (headY * opY) + (headZ * opZ);

        opX *= dotProduct;
        opY *= dotProduct;
        opZ *= dotProduct;

        return Math.sqrt((opX * opX) + (opY * opY) + (opZ * opZ));
    }

    private void setUnitDistance(FVector origin, FPoint in, double distance) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        projectUnit(origin, in);

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ).setDistance(pX, pY, pZ, distance);
    }

    private void projectUnit(FVector origin, FPoint in) {
        double memoAX = in.getX();
        double memoAY = in.getY();
        double memoAZ = in.getZ();

        double headX = origin.getBaseX() - memoAX;
        double headY = origin.getBaseY() - memoAY;
        double headZ = origin.getBaseZ() - memoAZ;

        in.set(origin.getRefHead());

        in.subXYZ(origin.getRefBase());
        in.normalize();

        double dotProduct = in.getDotProduct(headX, headY, headZ);

        in.mulFactor(dotProduct);
        in.addXYZ(memoAX, memoAY, memoAZ);
    }

    private void reflectUnit(FVector origin, FPoint in) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        projectUnit(origin, in);

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ).reflect(pX, pY, pZ);
    }

    private boolean isUnitInHalfSpace(FVector origin, FPoint arg) {
        double originMag = origin.getMagnitude();

        double headX = arg.getX() - origin.getBaseX();
        double headY = arg.getY() - origin.getBaseY();
        double headZ = arg.getZ() - origin.getBaseZ();

        double opX = (origin.getHeadX() - origin.getBaseX()) / originMag;
        double opY = (origin.getHeadY() - origin.getBaseY()) / originMag;
        double opZ = (origin.getHeadZ() - origin.getBaseZ()) / originMag;

        double dotProduct = (headX * opX) + (headY * opY) + (headZ * opZ);

        opX *= dotProduct;
        opY *= dotProduct;
        opZ *= dotProduct;

        opX += origin.getBaseX();
        opY += origin.getBaseY();
        opZ += origin.getBaseZ();

        double distBase = origin.getRefBase().getDistance(opX, opY, opZ);
        double distHead = origin.getRefHead().getDistance(opX, opY, opZ);

        if ((distBase < originMag + EPSILON) && (distHead < originMag + EPSILON)) {
            return true;
        }

        return distHead < distBase + EPSILON;
    }
}
