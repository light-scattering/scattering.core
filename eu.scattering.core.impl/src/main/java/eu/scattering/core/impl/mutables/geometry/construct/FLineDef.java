package eu.scattering.core.impl.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.impl.mutables.geometry.construct.support.ConstructPresetDef;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static eu.scattering.core.impl.configurations.NameConfigDef.JSON_TYPE;

public class FLineDef extends ConstructPresetDef<FLine> implements FLine {
    private static final String JSON_MAIN = "line";
    private static final String JSON_VAL = "val";

    private final Supplier<FVector> fVectorSupplier;
    private final Supplier<FPoint> fPointSupplier;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double epsilon;
    private FVector origin;

    private FLineDef(double epsilon, FVector origin) {

        this.fVectorSupplier = origin::copyZero;
        this.fPointSupplier = () -> getRefOrigin().getRefBase().copyZero();

        this.epsilon = epsilon;
        this.origin = origin;
    }

    public static FLine create(double epsilon, FVector origin) {

        return new FLineDef(epsilon, origin);
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

        getRefOrigin().set(position);

        return this;
    }

    @Override
    public FLine applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FVector origin = fVectorSupplier.get().applyStateFrom(json.getJSONObject(JSON_VAL));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FLine self() {

        return this;
    }

    @Override
    public FLine copy() {

        return copyZero().setRefOrigin(getRefOrigin().copy());
    }

    @Override
    public FLine copyZero() {

        return create(epsilon, fVectorSupplier.get());
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
    public boolean isCollinear(FLine arg) {

        return arg.isPartOf(origin);
    }

    @Override
    public void project(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble()
                .forEach(this::projectUnit);
    }

    @Override
    public void reflect(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble()
                .forEach(this::reflectUnit);
    }

    @Override
    public boolean isPartOf(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .allMatch(this::isUnitPartOf);
    }

    @Override
    public List<Double> getAtomicDistance(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(this::getUnitDistance)
                .collect(Collectors.toList());
    }

    @Override
    public void setDistance(Geometry geometry, double distance) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble()
                .forEach(p -> setUnitDistance(p, distance));
    }

    @Override
    public Optional<FPoint> getFPointAtX(double x) {
        FPoint fPoint = fPointSupplier.get().setX(x);

        setFPointAtX(fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    @Override
    public Optional<FPoint> getFPointAtY(double y) {
        FPoint fPoint = fPointSupplier.get().setY(y);

        setFPointAtY(fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    @Override
    public Optional<FPoint> getFPointAtZ(double z) {
        FPoint fPoint = fPointSupplier.get().setZ(z);

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

        return in.mul(factor).moveBase(baseX, baseY, baseZ).getRefHead();
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

        projectUnit(in);

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ).setDistance(pX, pY, pZ, distance);
    }

    private void reflectUnit(FPoint in) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        projectUnit(in);

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ).reflect(pX, pY, pZ);
    }

    private void projectUnit(FPoint in) {
        FVector origin = getRefOrigin();

        double headX = in.getX() - origin.getBaseX();
        double headY = in.getY() - origin.getBaseY();
        double headZ = in.getZ() - origin.getBaseZ();

        in.applyStateFrom(origin.getRefHead());

        in.sub(origin.getRefBase());
        in.normalize();

        double dotProduct = in.getDotProduct(headX, headY, headZ);

        in.mul(dotProduct);
        in.add(origin.getRefBase());
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isValid(FPoint arg) {

        return !Double.isNaN(arg.getX()) && !Double.isNaN(arg.getY()) && !Double.isNaN(arg.getZ());
    }

    private void invalidate(FPoint in) {

        in.set(Double.NaN, Double.NaN, Double.NaN);
    }
}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html