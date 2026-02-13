package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.GeometryFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.line.FLineHelper;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

import java.util.Optional;

import static eu.scattering.core.impl.ScatConfigDef.EPSILON;

public class FLineHelperDef implements FLineHelper {
    private final GeometryFactory factory;

    private FLineHelperDef(GeometryFactory factory) {

        this.factory = factory;
    }

    public static FLineHelper create(GeometryFactory factory) {

        return new FLineHelperDef(factory);
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
    public boolean isSameLine(FVector origin, FVector arg) {

        return isPartOf(origin, arg);
    }

    @Override
    public Optional<FPoint> getFPointAtX(FVector origin, double x) {
        FPoint fPoint = this.factory.getFPoint(x, 0, 0);

        setFPointAtX(origin, fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    @Override
    public Optional<FPoint> getFPointAtY(FVector origin, double y) {
        FPoint fPoint = this.factory.getFPoint(0, y, 0);

        setFPointAtY(origin, fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    @Override
    public Optional<FPoint> getFPointAtZ(FVector origin, double z) {
        FPoint fPoint = this.factory.getFPoint(0, 0, z);

        setFPointAtZ(origin, fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    @Override
    public Optional<FPoint> getFPointAtIntersection(FVector origin, FVector arg) {

        if (origin.isParallel(arg)) {
            return Optional.empty();
        }

        Plane dir = getPlaneProjection(origin, arg);

        FVector u = origin.copy();
        FVector v = arg.copy();

        projectOnPlane(u, dir);
        projectOnPlane(v, dir);

        double wX = u.getRefBase().getX() - v.getRefBase().getX();
        double wY = u.getRefBase().getY() - v.getRefBase().getY();
        double wZ = u.getRefBase().getZ() - v.getRefBase().getZ();

        setPlaneCrossProduct(v, dir);

        double vuDot = v.getDotProduct(u);
        double vwDot = v.moveBaseToCenter().getRefHead().getDotProduct(wX, wY, wZ);

        double scaleFactor = -vwDot / vuDot;

        return parseCandidate(origin, arg, setCandidate3D(origin, setCandidate2D(u, scaleFactor), dir));
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

        double distX = arg.getX() - opX;
        double distY = arg.getY() - opY;
        double distZ = arg.getZ() - opZ;

        return Math.sqrt((distX * distX) + (distY * distY) + (distZ * distZ));
    }

    private void setUnitDistance(FVector origin, FPoint in, double distance) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        boolean isMoved = projectUnit(origin, in);

        if (!isMoved) {
            if (distance > EPSILON) {
                throw new IllegalStateException("The unit distance cannot be changed");
            }

            return;
        }

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ).setDistance(pX, pY, pZ, distance);
    }

    private void reflectUnit(FVector origin, FPoint in) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        boolean isMoved = projectUnit(origin, in);

        if (!isMoved) {
            return;
        }

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ).reflect(pX, pY, pZ);
    }

    private boolean projectUnit(FVector origin, FPoint in) {
        double memoX = in.getX();
        double memoY = in.getY();
        double memoZ = in.getZ();

        double headX = memoX - origin.getBaseX();
        double headY = memoY - origin.getBaseY();
        double headZ = memoZ - origin.getBaseZ();

        in.set(origin.getRefHead());

        in.subXYZ(origin.getRefBase());
        in.normalize();

        double dotProduct = in.getDotProduct(headX, headY, headZ);

        in.mulFactor(dotProduct);
        in.addXYZ(origin.getRefBase());

        if (in.isSimilar(memoX, memoY, memoZ)) {
            in.set(memoX, memoY, memoZ);

            return false;
        }

        return true;
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isValid(FPoint arg) {

        return !Double.isNaN(arg.getX()) && !Double.isNaN(arg.getY()) && !Double.isNaN(arg.getZ());
    }

    private void invalidate(FPoint in) {

        in.set(Double.NaN, Double.NaN, Double.NaN);
    }

    // -------------------------------------------------------------------------------------------------

    private enum Plane { XY, YZ, XZ }

    private Plane getPlaneProjection(FVector origin, FVector arg) {
        double oX = origin.getLengthX();
        double oY = origin.getLengthY();
        double oZ = origin.getLengthZ();
        double aX = arg.getLengthX();
        double aY = arg.getLengthY();
        double aZ = arg.getLengthZ();

        if ((oX > 0 || oY > 0) && (aX > 0 || aY > 0)) {
            return Plane.XY;
        }

        if ((oY > 0 || oZ > 0) && (aY > 0 || aZ > 0)) {
            return Plane.YZ;
        }

        if ((oX > 0 || oZ > 0) && (aX > 0 || aZ > 0)) {
            return Plane.XZ;
        }

        throw new IllegalStateException("The projection plane cannot be determined");
    }

    private void setFPointAtX(FVector origin, FPoint in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getX() == oHead.getX()) {
            invalidate(in);
            return;
        }

        var l = oHead.getX() - oBase.getX();
        var m = oHead.getY() - oBase.getY();
        var n = oHead.getZ() - oBase.getZ();

        var y = oBase.getY() + (m / l * (in.getX() - oBase.getX()));
        var z = oBase.getZ() + (n / l * (in.getX() - oBase.getX()));

        in.setY(y).setZ(z);
    }

    private void setFPointAtY(FVector origin, FPoint in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getY() == oHead.getY()) {
            invalidate(in);
            return;
        }

        double l = oHead.getX() - oBase.getX();
        double m = oHead.getY() - oBase.getY();
        double n = oHead.getZ() - oBase.getZ();

        double x = oBase.getX() + (l / m * (in.getY() - oBase.getY()));
        double z = oBase.getZ() + (n / m * (in.getY() - oBase.getY()));

        in.setX(x).setZ(z);
    }

    private void setFPointAtZ(FVector origin, FPoint in) {

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getZ() == oHead.getZ()) {
            invalidate(in);
            return;
        }

        double l = oHead.getX() - oBase.getX();
        double m = oHead.getY() - oBase.getY();
        double n = oHead.getZ() - oBase.getZ();

        double x = oBase.getX() + (l / n * (in.getZ() - oBase.getZ()));
        double y = oBase.getY() + (m / n * (in.getZ() - oBase.getZ()));

        in.setX(x).setY(y);
    }

    private void setPlaneCrossProduct(FVector in, Plane plane) {
        double memoABX = in.getRefBase().getX();
        double memoABY = in.getRefBase().getY();
        double memoABZ = in.getRefBase().getZ();

        in.moveBaseToCenter();

        switch (plane) {
            case XY -> in.getRefHead().setCrossProduct(memoABX, memoABY, 1);
            case YZ -> in.getRefHead().setCrossProduct(1, memoABY, memoABZ);
            case XZ -> in.getRefHead().setCrossProduct(memoABX, 1, memoABZ);
        }

        in.moveBase(memoABX, memoABY, memoABZ);
    }

    private void projectOnPlane(FVector in, Plane plane) {

        switch (plane) {
            case XY -> {
                in.getRefBase().setZ(0);
                in.getRefHead().setZ(0);
            }
            case YZ -> {
                in.getRefBase().setX(0);
                in.getRefHead().setX(0);
            }
            case XZ -> {
                in.getRefBase().setY(0);
                in.getRefHead().setY(0);
            }
        }
    }

    private FPoint setCandidate2D(FVector in, double factor) {
        double baseX = in.getRefBase().getX();
        double baseY = in.getRefBase().getY();
        double baseZ = in.getRefBase().getZ();

        return in.mulFactor(factor).moveBase(baseX, baseY, baseZ).getRefHead();
    }

    private FPoint setCandidate3D(FVector origin, FPoint in, Plane plane) {

        switch (plane) {
            case XY -> setCandidate3DXY(origin, in);
            case YZ -> setCandidate3DYZ(origin, in);
            case XZ -> setCandidate3DXZ(origin, in);
        }

        return in;
    }

    private void setCandidate3DXY(FVector origin, FPoint in) {
        double memoY = in.getY();

        setFPointAtX(origin, in);

        if (isValid(in)) {
            return;
        }

        setFPointAtY(origin, in.setY(memoY));

        if (isValid(in)) {
            return;
        }

        invalidate(in);
    }

    private void setCandidate3DYZ(FVector origin, FPoint in) {
        double memoZ = in.getZ();

        setFPointAtY(origin, in);

        if (isValid(in)) {
            return;
        }

        setFPointAtZ(origin, in.setZ(memoZ));

        if (isValid(in)) {
            return;
        }

        invalidate(in);
    }

    private void setCandidate3DXZ(FVector origin, FPoint in) {
        double memoZ = in.getZ();

        setFPointAtX(origin, in);

        if (isValid(in)) {
            return;
        }

        setFPointAtZ(origin, in.setZ(memoZ));

        if (isValid(in)) {
            return;
        }

        invalidate(in);
    }

    private Optional<FPoint> parseCandidate(FVector origin, FVector arg, FPoint argCandidate) {

        if (!isValid(argCandidate)) {
            return Optional.empty();
        }

        if (isPartOf(origin, argCandidate) && isPartOf(arg, argCandidate)) {
            return Optional.of(argCandidate);
        }

        return Optional.empty();
    }
}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html

