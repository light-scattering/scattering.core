package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.impl.component.geometry.construct.preset.ConstructPresetDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import org.json.JSONObject;

import java.util.Optional;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FLineDef extends ConstructPresetDef<FLine> implements FLine {
    private static final String JSON_MAIN = "line";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final ConstructFactory factory;

    private FVector origin;

    private FLineDef(ConstructFactory factory, FVector origin) {

        this.factory = factory;
        this.origin = origin;
    }

    public static FLine create(ConstructFactory factory, FVector origin) {

        return new FLineDef(factory, origin);
    }

    @Override
    public FVector getRefOrigin() {

        return origin;
    }

    @Override
    public FLine setRefOrigin(FVector refOrigin) {

        if (refOrigin == null) {
            throw new NullPointerException("The reference FVector cannot be null");
        }

        origin = refOrigin;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FLine set(FPairPos3D position) {

        getRefOrigin().applyStateFrom(position);

        return this;
    }

    @Override
    public FLine applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FVector origin = supplyFVector().applyStateFrom(json.getJSONObject(JSON_VAL));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FLine self() {

        return this;
    }

    @Override
    public FLine copy() {

        FLine element = supplyFLine();

        element.getRefOrigin().applyStateFrom(getRefOrigin().copy());

        return element;
    }

    @Override
    public FPairPos3D toFPairPos3D() {

        return getRefOrigin().toFPairPos3D();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_VAL, getRefOrigin().toJSON());

        return json;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {

        return getRefOrigin().hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FLine) {
            FLine ref = (FLine) object;

            return getRefOrigin().equals(ref.getRefOrigin());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isSameLine(FLine arg) {

        return arg.isPartOf(origin);
    }

    @Override
    public void project(FPoint in) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        projectUnit(in);
    }

    @Override
    public void project(Geometry in) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.disassemble()
                .forEach(this::projectUnit);
    }

    @Override
    public void reflect(FPoint in) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        reflectUnit(in);
    }

    @Override
    public void reflect(Geometry in) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.disassemble()
                .forEach(this::reflectUnit);
    }

    @Override
    public boolean isPartOf(FPoint arg) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return isUnitPartOf(arg);
    }

    @Override
    public boolean isPartOf(FPoint arg, double epsilon) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return isUnitPartOf(arg, epsilon);
    }

    @Override
    public boolean isPartOf(Geometry arg) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return arg.disassemble().stream()
                .allMatch(this::isUnitPartOf);
    }

    @Override
    public boolean isPartOf(Geometry arg, double epsilon) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return arg.disassemble().stream()
                .allMatch(e -> isUnitPartOf(e, epsilon));
    }


    @Override
    public double getDistance(FPoint arg) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return getUnitDistance(arg);
    }

    @Override
    public void setDistance(FPoint in, double distance) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        setUnitDistance(in, distance);
    }

    @Override
    public void setDistance(Geometry in, double distance) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.disassemble()
                .forEach(p -> setUnitDistance(p, distance));
    }

    @Override
    public Optional<FPoint> getFPointAtX(double x) {
        FPoint fPoint = supplyFPoint().setX(x);

        setFPointAtX(fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    @Override
    public Optional<FPoint> getFPointAtY(double y) {
        FPoint fPoint = supplyFPoint().setY(y);

        setFPointAtY(fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    @Override
    public Optional<FPoint> getFPointAtZ(double z) {
        FPoint fPoint = supplyFPoint().setZ(z);

        setFPointAtZ(fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    // TODO - Not optimized
    @Override
    public Optional<FPoint> getFPointAtIntersection(FLine arg) {
        FVector refOrigin = getRefOrigin();
        FVector argOrigin = arg.getRefOrigin();

        if (refOrigin.isParallel(argOrigin)) {
            return Optional.empty();
        }

        Plane dir = getPlaneProjection(argOrigin);

        FVector u = refOrigin.copy();
        FVector v = argOrigin.copy();

        projectOnPlane(u, dir);
        projectOnPlane(v, dir);

        double wX = u.getRefBase().getX() - v.getRefBase().getX();
        double wY = u.getRefBase().getY() - v.getRefBase().getY();
        double wZ = u.getRefBase().getZ() - v.getRefBase().getZ();

        setPlaneCrossProduct(v, dir);

        double vuDot = v.getDotProduct(u);
        double vwDot = v.moveBaseToCenter().getRefHead().getDotProduct(wX, wY, wZ);

        double scaleFactor = -vwDot / vuDot;

        return parseCandidate(arg, setCandidate3D(setCandidate2D(u, scaleFactor), dir));
    }

    // -------------------------------------------------------------------------------------------------

    private enum Plane { XY, YZ, XZ }

    private Plane getPlaneProjection(FVector arg) {
        double oX = getRefOrigin().getLengthX();
        double oY = getRefOrigin().getLengthY();
        double oZ = getRefOrigin().getLengthZ();
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

    private void setFPointAtX(FPoint in) {
        var origin = getRefOrigin();

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

    private void setFPointAtY(FPoint in) {
        var origin = getRefOrigin();

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

    private void setFPointAtZ(FPoint in) {
        var origin = getRefOrigin();

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
            case XY:
                in.getRefHead().setCrossProduct(memoABX, memoABY, 1);
                break;
            case YZ:
                in.getRefHead().setCrossProduct(1, memoABY, memoABZ);
                break;
            case XZ:
                in.getRefHead().setCrossProduct(memoABX, 1, memoABZ);
                break;
        }

        in.moveBase(memoABX, memoABY, memoABZ);
    }

    private void projectOnPlane(FVector in, Plane plane) {

        switch (plane) {
            case XY:
                in.getRefBase().setZ(0);
                in.getRefHead().setZ(0);
                break;
            case YZ:
                in.getRefBase().setX(0);
                in.getRefHead().setX(0);
                break;
            case XZ:
                in.getRefBase().setY(0);
                in.getRefHead().setY(0);
                break;
        }
    }

    private FPoint setCandidate2D(FVector in, double factor) {
        double baseX = in.getRefBase().getX();
        double baseY = in.getRefBase().getY();
        double baseZ = in.getRefBase().getZ();

        return in.mulFactor(factor).moveBase(baseX, baseY, baseZ).getRefHead();
    }

    private FPoint setCandidate3D(FPoint in, Plane plane) {

        switch (plane) {
            case XY:
                setCandidate3DXY(in);
                break;
            case YZ:
                setCandidate3DYZ(in);
                break;
            case XZ:
                setCandidate3DXZ(in);
                break;
        }

        return in;
    }

    private void setCandidate3DXY(FPoint in) {
        double memoY = in.getY();

        setFPointAtX(in);

        if (isValid(in)) {
            return;
        }

        setFPointAtY(in.setY(memoY));

        if (isValid(in)) {
            return;
        }

        invalidate(in);
    }

    private void setCandidate3DYZ(FPoint in) {
        double memoZ = in.getZ();

        setFPointAtY(in);

        if (isValid(in)) {
            return;
        }

        setFPointAtZ(in.setZ(memoZ));

        if (isValid(in)) {
            return;
        }

        invalidate(in);
    }

    private void setCandidate3DXZ(FPoint in) {
        double memoZ = in.getZ();

        setFPointAtX(in);

        if (isValid(in)) {
            return;
        }

        setFPointAtZ(in.setZ(memoZ));

        if (isValid(in)) {
            return;
        }

        invalidate(in);
    }

    private Optional<FPoint> parseCandidate(FLine arg, FPoint argCandidate) {

        if (!isValid(argCandidate)) {
            return Optional.empty();
        }

        if (isPartOf(argCandidate) && arg.isPartOf(argCandidate)) {
            return Optional.of(argCandidate);
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isUnitPartOf(FPoint arg) {

        return getUnitDistance(arg) < EPSILON;
    }

    private boolean isUnitPartOf(FPoint arg, double epsilon) {

        return getUnitDistance(arg) < epsilon;
    }

    private double getUnitDistance(FPoint arg) {
        FVector origin = getRefOrigin();
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

    private void setUnitDistance(FPoint in, double distance) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        boolean isMoved = projectUnit(in);

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

    private void reflectUnit(FPoint in) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        boolean isMoved = projectUnit(in);

        if (!isMoved) {
            return;
        }

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ).reflect(pX, pY, pZ);
    }

    private boolean projectUnit(FPoint in) {
        FVector origin = getRefOrigin();

        double memoX = in.getX();
        double memoY = in.getY();
        double memoZ = in.getZ();

        double headX = memoX - origin.getBaseX();
        double headY = memoY - origin.getBaseY();
        double headZ = memoZ - origin.getBaseZ();

        in.applyStateFrom(origin.getRefHead());

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

    private FLine supplyFLine() {

        return factory.getFLine();
    }

    private FVector supplyFVector() {

        return factory.getFVector();
    }

    private FPoint supplyFPoint() {

        return factory.getFPoint();
    }
}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html