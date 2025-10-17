package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.impl.component.geometry.construct.preset.ConstructPresetDef;
import org.json.JSONObject;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FRayDef extends ConstructPresetDef<FRay> implements FRay {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "ray";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final ConstructFactory factory;

    private FVector origin;

    private FRayDef(ConstructFactory factory, FVector origin) {

        this.factory = factory;
        this.origin = origin;
    }

    public static FRay create(ConstructFactory factory, FVector origin) {

        return new FRayDef(factory, origin);
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public FVector getRefOrigin() {

        return origin;
    }

    @Override
    public FRay setRefOrigin(FVector refOrigin) {

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
    public FRay set(FPairPos3D position) {

        getRefOrigin().applyStateFrom(position);

        return this;
    }

    @Override
    public FRay applyStateTo(Construct<?> in) {

        getRefOrigin().applyStateTo(in.getRefOrigin());

        return this;
    }

    @Override
    public FRay applyStateFrom(Construct<?> arg) {

        getRefOrigin().applyStateFrom(arg.getRefOrigin());

        return this;
    }

    @Override
    public FRay set(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FVector origin = supplyFVector().set(json.getJSONObject(JSON_VAL));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FRay self() {

        return this;
    }

    @Override
    public FRay copy() {

        FRay element = supplyFRay();

        element.getRefOrigin().applyStateFrom(getRefOrigin().copy());

        return element;
    }

    @Override
    public boolean isExact(Geometry arg) {

        if (arg instanceof FRay) {
            return isExact((FRay) arg);
        }

        return false;
    }

    @Override
    public boolean isSimilar(Geometry arg) {

        if (arg instanceof FRay) {
            return isSimilar((FRay) arg);
        }

        return false;
    }

    @Override
    public Geometry copyGeometry() {

        return copy();
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
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isProjectable(FPoint arg) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return getUnitDistance(arg) > -1;
    }

    // TODO - Not optimized
    @Override
    public FPos3D project(double x, double y, double z) {
        FPoint fPoint = supplyFPoint().set(x, y, z);

        boolean results = project(fPoint);

        return results ? fPoint.toFPos3D() : null;
    }

    @Override
    public boolean project(FPoint in) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return projectUnit(in);
    }

    @Override
    public boolean project(Geometry in) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return in.toFPoints().stream()
                .allMatch(this::projectUnit);
    }

    // TODO - Not optimized
    @Override
    public FPos3D reflect(double x, double y, double z) {
        FPoint fPoint = supplyFPoint().set(x, y, z);

        reflect(fPoint);

        return fPoint.toFPos3D();
    }

    @Override
    public boolean reflect(FPoint in) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return reflectUnit(in);
    }

    @Override
    public boolean reflect(Geometry in) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return in.toFPoints().stream()
                .allMatch(this::reflectUnit);
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

        return arg.toFPoints().stream()
                .allMatch(this::isUnitPartOf);
    }

    @Override
    public boolean isPartOf(Geometry arg, double epsilon) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return arg.toFPoints().stream()
                .allMatch(e -> isUnitPartOf(e, epsilon));
    }

    // TODO - Not optimized
    @Override
    public double getDistance(double x, double y, double z) {
        FPoint fPoint = supplyFPoint().set(x, y, z);

        return getDistance(fPoint);
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

        in.toFPoints()
                .forEach(p -> setUnitDistance(p, distance));
    }

    @Override
    public void shiftForward(FPoint in, double distance) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        shiftUnitForward(in, distance);
    }

    @Override
    public void shiftForward(Geometry in, double distance) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.toFPoints()
                .forEach(p -> shiftUnitForward(p, distance));
    }

    @Override
    public void shiftBackward(FPoint in, double distance) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        shiftUnitBackward(in, distance);
    }

    @Override
    public void shiftBackward(Geometry in, double distance) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.toFPoints()
                .forEach(p -> shiftUnitBackward(p, distance));
    }

    @Override
    public FPoint getFPointAtDistance(double length) {

        if (length < 0) {
            throw new IllegalArgumentException("The distance must be a positive value");
        }

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var fPoint = supplyFPoint();

        fPoint.applyStateFrom(getRefOrigin().getRefHead());
        fPoint.subXYZ(getRefOrigin().getRefBase());

        var tmp = length / getRefOrigin().getMagnitude();

        fPoint.setX(getRefOrigin().getRefBase().getX() + (fPoint.getX() * tmp));
        fPoint.setY(getRefOrigin().getRefBase().getY() + (fPoint.getY() * tmp));
        fPoint.setZ(getRefOrigin().getRefBase().getZ() + (fPoint.getZ() * tmp));

        return fPoint;
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isUnitPartOf(FPoint arg) {
        double dist = getUnitDistance(arg);

        return dist != -1 && dist < EPSILON;
    }

    private boolean isUnitPartOf(FPoint arg, double epsilon) {
        double dist = getUnitDistance(arg);

        return dist != -1 && dist < epsilon;
    }

    private boolean isUnitPartOfRay(double x, double y, double z) {
        FPoint oBase = getRefOrigin().getRefBase();
        FPoint oHead = getRefOrigin().getRefHead();

        double oMagnitude = getRefOrigin().getMagnitude();

        double distBase = oBase.getDistance(x, y, z);
        double distHead = oHead.getDistance(x, y, z);

        if (Math.abs(distBase + distHead - oMagnitude) < EPSILON) {
            return true;
        }

        return distBase > distHead;
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

        boolean isValid = isUnitPartOfRay(opX, opY, opZ);

        if (!isValid) {
            return -1;
        }

        double distX = arg.getX() - opX;
        double distY = arg.getY() - opY;
        double distZ = arg.getZ() - opZ;

        return Math.sqrt((distX * distX) + (distY * distY) + (distZ * distZ));
    }

    private void setUnitDistance(FPoint in, double distance) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        boolean isValid = projectUnit(in);

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ);

        if (!isValid) {
            return;
        }

        in.setDistance(pX, pY, pZ, distance);
    }

    private boolean reflectUnit(FPoint in) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        boolean isValid = projectUnit(in);

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

    private void shiftUnitForward(FPoint in, double dist) {

        if (dist < 0) {
            shiftUnitBackward(in, -dist);

            return;
        }

        FVector origin = getRefOrigin();

        double memoX = in.getX();
        double memoY = in.getY();
        double memoZ = in.getZ();
        double zeroOHX = origin.getHeadX() - origin.getBaseX();
        double zeroOHY = origin.getHeadY() - origin.getBaseY();
        double zeroOHZ = origin.getHeadZ() - origin.getBaseZ();

        in.set(zeroOHX, zeroOHY, zeroOHZ);
        in.setMagnitude(dist);
        in.addXYZ(memoX, memoY, memoZ);
    }

    private void shiftUnitBackward(FPoint in, double dist) {

        if (dist < 0) {
            shiftUnitForward(in, -dist);

            return;
        }

        FVector origin = getRefOrigin();

        double memoX = in.getX();
        double memoY = in.getY();
        double memoZ = in.getZ();
        double zeroOHX = origin.getHeadX() - origin.getBaseX();
        double zeroOHY = origin.getHeadY() - origin.getBaseY();
        double zeroOHZ = origin.getHeadZ() - origin.getBaseZ();

        in.set(zeroOHX, zeroOHY, zeroOHZ);
        in.setMagnitude(dist);
        in.reflectThroughCenter();
        in.addXYZ(memoX, memoY, memoZ);
    }

    private boolean projectUnit(FPoint in) {
        FVector origin = getRefOrigin();

        double memoX = in.getX();
        double memoY = in.getY();
        double memoZ = in.getZ();

        double headX = in.getX() - origin.getBaseX();
        double headY = in.getY() - origin.getBaseY();
        double headZ = in.getZ() - origin.getBaseZ();

        in.applyStateFrom(origin.getRefHead());

        in.subXYZ(origin.getRefBase());
        in.normalize();

        double dotProduct = in.getDotProduct(headX, headY, headZ);

        in.mulFactor(dotProduct);
        in.addXYZ(origin.getRefBase());

        boolean isValid = isUnitPartOfRay(in.getX(), in.getY(), in.getZ());

        if (isValid) {
            return true;
        }

        in.set(memoX, memoY, memoZ);

        return false;
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint supplyFPoint() {

        return factory.getFPoint();
    }

    private FVector supplyFVector() {

        return factory.getFVector();
    }

    private FRay supplyFRay() {

        return factory.getFRay();
    }
}