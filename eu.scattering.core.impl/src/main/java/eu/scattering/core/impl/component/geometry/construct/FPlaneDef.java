package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.impl.component.geometry.construct.preset.ConstructPresetDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FPlaneDef extends ConstructPresetDef<FPlane> implements FPlane {
    private static final String JSON_MAIN = "plane";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final ConstructFactory factory;

    private FVector origin;

    private FPlaneDef(ConstructFactory factory, FVector origin) {

        this.factory = factory;
        this.origin = origin;
    }

    public static FPlane create(ConstructFactory factory, FVector origin) {

        return new FPlaneDef(factory, origin);
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public FVector getRefOrigin() {

        return origin;
    }

    @Override
    public FPlane setRefOrigin(FVector refOrigin) {

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
    public FPlane set(FPairPos3D position) {

        getRefOrigin().applyStateFrom(position);

        return this;
    }

    @Override
    public FPlane set(FPoint ptBase, FPoint ptA, FPoint ptB) {

        getRefOrigin().set(ptBase, ptA).setCrossProductBaseCommon(ptB);

        return this;
    }

    @Override
    public FPlane set(FPos3D ptBase, FPos3D ptA, FPos3D ptB) {

        getRefOrigin().set(ptBase, ptA).setCrossProductBaseCommon(ptB);

        return this;
    }

    @Override
    public FPlane applyStateTo(Construct<?> in) {

        getRefOrigin().applyStateTo(in.getRefOrigin());

        return this;
    }

    @Override
    public FPlane applyStateFrom(Construct<?> arg) {

        getRefOrigin().applyStateFrom(arg.getRefOrigin());

        return this;
    }

    @Override
    public FPlane set(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FVector origin = supplyFVector().set(json.getJSONObject(JSON_VAL));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPlane self() {

        return this;
    }

    @Override
    public FPlane copy() {

        FPlane element = supplyFPlane();

        element.getRefOrigin().applyStateFrom(getRefOrigin().copy());

        return element;
    }

    @Override
    public Geometry replicate() {

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
    public int hashCode() {

        return getRefOrigin().hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPlane) {
            FPlane ref = (FPlane) object;

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
    public boolean isSamePlane(FPlane arg) {

        return getRefOrigin().isCollinear(arg.getRefOrigin()) && isPartOf(arg.getRefOrigin().getRefBase());
    }

    @Override
    public void project(FPoint in) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        projectUnitOnPlane(in);
    }

    @Override
    public void project(Geometry in) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        in.explode()
                .forEach(this::projectUnitOnPlane);
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

        in.explode()
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

        return arg.explode().stream()
                .allMatch(this::isUnitPartOf);
    }

    @Override
    public boolean isPartOf(Geometry arg, double epsilon) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return arg.explode().stream()
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

        in.explode()
                .forEach(p -> setUnitDistance(p, distance));
    }

    @Override
    public boolean isCut(Geometry arg) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        List<Boolean> isInHalfSpace = arg.explode().stream()
                .map(this::isUnitInHalfSpace)
                .collect(Collectors.toList());

        boolean conditionTrue = isInHalfSpace.stream().anyMatch(e -> e);
        boolean conditionFalse = isInHalfSpace.stream().anyMatch(e -> !e);

        return conditionTrue && conditionFalse;
    }

    @Override
    public boolean isOnSide(FPoint arg) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return isUnitInHalfSpace(arg);
    }

    @Override
    public boolean isOnSide(Geometry arg) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return arg.explode().stream()
                .allMatch(this::isUnitInHalfSpace);
    }

    // TODO - Not optimized
    @Override
    public Optional<FLine> getFLineAtIntersection(FPlane arg) {

        if (getRefOrigin().isCollinear(arg.getRefOrigin())) {
            return Optional.empty();
        }

        FLine result = supplyFLine();
        FVector resultOrigin = result.getRefOrigin();

        FPoint resBase = resultOrigin.getRefBase();
        FPoint resHead = resultOrigin.getRefHead();

        FVector u = getRefOrigin().copy();
        FVector v = arg.getRefOrigin().copy();

        double aBX = u.getBaseX();
        double aBY = u.getBaseY();
        double aBZ = u.getBaseZ();
        double bBX = v.getBaseX();
        double bBY = v.getBaseY();
        double bBZ = v.getBaseZ();

        FPoint aHead = u.moveBaseToCenter().getRefHead();
        FPoint bHead = v.moveBaseToCenter().getRefHead() ;

        resBase.applyStateFrom(aHead);
        resHead.applyStateFrom(bHead);

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

        resHead.applyStateFrom(aHead);

        return Optional.of(result);
    }

    // TODO - Not optimized
    @Override
    public Optional<FPoint> getFPointAtIntersection(FLine arg) {

        if (getRefOrigin().isOrthogonal(arg.getRefOrigin())) {
            return Optional.empty();
        }

        FPoint result = supplyFPoint();

        FVector u = getRefOrigin().copy();
        FVector v = arg.getRefOrigin().copy();

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

        result.applyStateFrom(v.getRefHead());

        return Optional.of(result);
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

    private void setUnitDistance(FPoint in, double distance) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        projectUnitOnPlane(in);

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ).setDistance(pX, pY, pZ, distance);
    }

    private void projectUnitOnPlane(FPoint in) {
        FVector origin = getRefOrigin();

        double memoAX = in.getX();
        double memoAY = in.getY();
        double memoAZ = in.getZ();

        double headX = origin.getBaseX() - memoAX;
        double headY = origin.getBaseY() - memoAY;
        double headZ = origin.getBaseZ() - memoAZ;

        in.applyStateFrom(origin.getRefHead());

        in.subXYZ(origin.getRefBase());
        in.normalize();

        double dotProduct = in.getDotProduct(headX, headY, headZ);

        in.mulFactor(dotProduct);
        in.addXYZ(memoAX, memoAY, memoAZ);
    }

    private void reflectUnit(FPoint in) {
        double oX = in.getX();
        double oY = in.getY();
        double oZ = in.getZ();

        projectUnitOnPlane(in);

        double pX = in.getX();
        double pY = in.getY();
        double pZ = in.getZ();

        in.set(oX, oY, oZ).reflect(pX, pY, pZ);
    }

    private boolean isUnitInHalfSpace(FPoint arg) {
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

        double distBase = origin.getRefBase().getDistance(opX, opY, opZ);
        double distHead = origin.getRefHead().getDistance(opX, opY, opZ);

        if ((distBase < originMag + EPSILON) && (distHead < originMag + EPSILON)) {
            return true;
        }

        return distHead < distBase + EPSILON;
    }

    // -------------------------------------------------------------------------------------------------

    private FPlane supplyFPlane() {

        return factory.getFPlane();
    }

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

// http://geomalgorithms.com/a05-_intersect-1.html
// https://opentextbc.ca/calculusv3openstax/chapter/equations-of-lines-and-planes-in-space/