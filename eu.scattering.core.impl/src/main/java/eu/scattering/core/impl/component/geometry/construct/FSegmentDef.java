package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.impl.component.geometry.construct.preset.ConstructPresetDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONObject;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FSegmentDef extends ConstructPresetDef<FSegment> implements FSegment {
    private static final String JSON_MAIN = "segment";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final ConstructFactory factory;

    private FVector origin;

    private FSegmentDef(ConstructFactory factory, FVector origin) {

        this.factory = factory;
        this.origin = origin;
    }

    public static FSegment create(ConstructFactory factory, FVector origin) {

        return new FSegmentDef(factory, origin);
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public FVector getRefOrigin() {

        return origin;
    }

    @Override
    public FSegment setRefOrigin(FVector refOrigin) {

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
    public FSegment set(FPairPos3D position) {

        getRefOrigin().applyStateFrom(position);

        return this;
    }

    @Override
    public FSegment applyStateTo(Construct<?> in) {

        getRefOrigin().applyStateTo(in.getRefOrigin());

        return this;
    }

    @Override
    public FSegment applyStateFrom(Construct<?> arg) {

        getRefOrigin().applyStateFrom(arg.getRefOrigin());

        return this;
    }

    @Override
    public FSegment set(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        var origin = supplyFVector().set(json.getJSONObject(JSON_VAL));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSegment self() {

        return this;
    }

    @Override
    public FSegment copy() {

        FSegment element = supplyFSegment();

        element.getRefOrigin().applyStateFrom(getRefOrigin().copy());

        return element;
    }

    @Override
    public boolean isExact(Geometry arg) {

        if (arg instanceof FSegment) {
            return isExact((FSegment) arg);
        }

        return false;
    }

    @Override
    public boolean isSimilar(Geometry arg) {

        if (arg instanceof FSegment) {
            return isSimilar((FSegment) arg);
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
        var json = new JSONObject();

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

        if (object instanceof FSegment fSegment) {

            return getRefOrigin().equals(fSegment.getRefOrigin());
        }

        return false;
    }

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

        project(fPoint);

        return fPoint.toFPos3D();
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

    // -------------------------------------------------------------------------------------------------

    private boolean isUnitPartOf(FPoint arg) {
        double dist = getUnitDistance(arg);

        return dist != -1 && dist < EPSILON;
    }

    private boolean isUnitPartOf(FPoint arg, double epsilon) {
        double dist = getUnitDistance(arg);

        return dist != -1 && dist < epsilon;
    }

    private boolean isUnitPartOfSegment(double x, double y, double z) {
        FPoint oBase = getRefOrigin().getRefBase();
        FPoint oHead = getRefOrigin().getRefHead();

        double oMagnitude = getRefOrigin().getMagnitude();

        double distBase = oBase.getDistance(x, y, z);
        double distHead = oHead.getDistance(x, y, z);

        return Math.abs(distBase + distHead - oMagnitude) < EPSILON;
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

        boolean isValid = isUnitPartOfSegment(opX, opY, opZ);

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

        boolean isValid = isUnitPartOfSegment(in.getX(), in.getY(), in.getZ());

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

    private FSegment supplyFSegment() {

        return factory.getFSegment();
    }
}