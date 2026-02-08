package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentHelper;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FSegmentHelperDef implements FSegmentHelper {
    private final GeometryFactory factory;

    private FSegmentHelperDef(GeometryFactory factory) {

        this.factory = factory;
    }

    public static FSegmentHelper create(GeometryFactory factory) {

        return new FSegmentHelperDef(factory);
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

        return isPartOf(origin, this.factory.getFPoint(arg));
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

        return isPartOf(origin, this.factory.getFPoint(arg), epsilon);
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
    public boolean isProjectable(FVector origin, double x, double y, double z) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return getUnitDistance(origin, x, y, z) > -1;
    }

    @Override
    public boolean isProjectable(FVector origin, FPoint arg) {

        return isProjectable(origin, arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public boolean isProjectable(FVector origin, FPos3D arg) {

        return isProjectable(origin, this.factory.getFPoint(arg));
    }

    @Override
    public double getDistance(FVector origin, double x, double y, double z) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return getUnitDistance(origin, x, y, z);
    }

    @Override
    public double getDistance(FVector origin, FPoint arg) {

        return getDistance(origin, arg.getX(), arg.getY(), arg.getZ());
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
    public boolean setDistance(FVector origin, FPoint in, double distance) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return setUnitDistance(origin, in, distance);
    }

    @Override
    public boolean setDistance(FVector origin, Geometry in, double distance) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return in.toFPoints().stream()
                .allMatch(p -> setUnitDistance(origin, p, distance));
    }

    @Override
    public FPos3D project(FVector origin, double x, double y, double z) {
        FPoint fPoint = this.factory.getFPoint(x, y, z);

        boolean results = project(origin, fPoint);

        return results ? fPoint.toFPos3D() : null;
    }

    @Override
    public FPos3D project(FVector origin, FPos3D arg) {

        return project(origin, arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public boolean project(FVector origin, FPoint in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return projectUnit(origin, in);
    }

    @Override
    public boolean project(FVector origin, Geometry in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return in.toFPoints().stream()
                .allMatch(e -> projectUnit(origin, e));
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
    public boolean reflect(FVector origin, FPoint in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return reflectUnit(origin, in);
    }

    @Override
    public boolean reflect(FVector origin, Geometry in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return in.toFPoints().stream()
                .allMatch(e -> reflectUnit(origin, e));
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isUnitPartOf(FVector origin, FPoint arg) {
        double dist = getUnitDistance(origin, arg.getX(), arg.getY(), arg.getZ());

        return dist != -1 && dist < EPSILON;
    }

    private boolean isUnitPartOf(FVector origin, FPoint arg, double epsilon) {
        double dist = getUnitDistance(origin, arg.getX(), arg.getY(), arg.getZ());

        return dist != -1 && dist < epsilon;
    }

    private boolean isUnitPartOfSegment(FVector origin, double x, double y, double z) {
        FPoint oBase = origin.getRefBase();
        FPoint oHead = origin.getRefHead();

        double oMagnitude = origin.getMagnitude();

        double distBase = oBase.getDistance(x, y, z);
        double distHead = oHead.getDistance(x, y, z);

        return Math.abs(distBase + distHead - oMagnitude) < EPSILON;
    }

    private double getUnitDistance(FVector origin, double x, double y, double z) {
        double originMag = origin.getMagnitude();

        double headX = x - origin.getBaseX();
        double headY = y - origin.getBaseY();
        double headZ = z - origin.getBaseZ();

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

        boolean isValid = isUnitPartOfSegment(origin, opX, opY, opZ);

        if (!isValid) {
            return -1;
        }

        double distX = x - opX;
        double distY = y - opY;
        double distZ = z - opZ;

        return Math.sqrt((distX * distX) + (distY * distY) + (distZ * distZ));
    }

    private boolean setUnitDistance(FVector origin, FPoint in, double distance) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        boolean isValid = projectUnit(origin, in);

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ);

        if (!isValid) {
            return false;
        }

        in.setDistance(pX, pY, pZ, distance);

        return true;
    }

    private boolean reflectUnit(FVector origin, FPoint in) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        boolean isValid = projectUnit(origin, in);

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ);

        if (!isValid) {
            return false;
        }

        in.reflect(pX, pY, pZ);

        return true;
    }

    private boolean projectUnit(FVector origin, FPoint in) {
        double memoX = in.getX();
        double memoY = in.getY();
        double memoZ = in.getZ();

        double headX = in.getX() - origin.getBaseX();
        double headY = in.getY() - origin.getBaseY();
        double headZ = in.getZ() - origin.getBaseZ();

        in.set(origin.getRefHead());

        in.subXYZ(origin.getRefBase());
        in.normalize();

        double dotProduct = in.getDotProduct(headX, headY, headZ);

        in.mulFactor(dotProduct);
        in.addXYZ(origin.getRefBase());

        boolean isValid = isUnitPartOfSegment(origin, in.getX(), in.getY(), in.getZ());

        if (isValid) {
            return true;
        }

        in.set(memoX, memoY, memoZ);

        return false;
    }
}
