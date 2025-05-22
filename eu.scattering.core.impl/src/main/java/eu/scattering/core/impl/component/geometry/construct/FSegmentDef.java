package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.impl.component.geometry.construct.preset.ConstructPresetDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
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
    public FSegment applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        var origin = supplyFVector().applyStateFrom(json.getJSONObject(JSON_VAL));

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

        if (object instanceof FSegment) {
            var ref = (FSegment) object;

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
    public boolean isProjectable(FPoint arg) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return getUnitDistance(arg) > -1;
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

    private void reflectUnit(FPoint in) {
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

        in.reflect(pX, pY, pZ);
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

    private FSegment supplyFSegment() {

        return factory.getFSegment();
    }

    private FVector supplyFVector() {

        return factory.getFVector();
    }
}