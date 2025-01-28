package eu.scattering.core.impl.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlane;
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

public class FPlaneDef extends ConstructPresetDef<FPlane> implements FPlane {
    private static final String JSON_MAIN = "plane";
    private static final String JSON_VAL = "val";

    private static double epsilon = 0;

    private static Supplier<FLine> fLineSupplier;
    private static Supplier<FVector> fVectorSupplier;

    public static void initialize(double epsilon,
                                  Supplier<FLine> fLineSupplier,
                                  Supplier<FVector> fVectorSupplier) {

        FPlaneDef.epsilon = epsilon;
        FPlaneDef.fLineSupplier = fLineSupplier;
        FPlaneDef.fVectorSupplier = fVectorSupplier;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FVector origin;

    private FPlaneDef(FVector origin) {

        this.origin = origin;
    }

    public static FPlane create(FVector origin) {

        return new FPlaneDef(origin);
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
    public FPlane applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FVector origin = fVectorSupplier.get().applyStateFrom(json.getJSONObject(JSON_VAL));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPlane self() {

        return this;
    }

    @Override
    public FPlane copy() {

        return copyZero().setRefOrigin(getRefOrigin().copy());
    }

    @Override
    public FPlane copyZero() {

        return create(fVectorSupplier.get());
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
    public boolean isCoplanar(FPlane arg) {

        return getRefOrigin().isCollinear(arg.getRefOrigin()) && isPartOf(arg.getRefOrigin().getRefBase());
    }

    @Override
    public void project(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble()
                .forEach(this::projectUnitOnPlane);
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
    public boolean isCut(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        List<Boolean> isInHalfSpace = geometry.disassemble().stream()
                .map(this::isUnitInHalfSpace)
                .collect(Collectors.toList());

        boolean conditionTrue = isInHalfSpace.stream().anyMatch(e -> e);
        boolean conditionFalse = isInHalfSpace.stream().anyMatch(e -> !e);

        return conditionTrue && conditionFalse;
    }

    @Override
    public boolean isOnSide(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .allMatch(this::isUnitInHalfSpace);
    }

    // TODO - Not optimized
    @Override
    public Optional<FLine> getFLineAtIntersection(FPlane ref) {

        if (getRefOrigin().isCollinear(ref.getRefOrigin())) {
            return Optional.empty();
        }

        FLine result = fLineSupplier.get();

        FPoint resBase = result.getRefOrigin().getRefBase();
        FPoint resHead = result.getRefOrigin().getRefHead();

        FVector u = getRefOrigin().copy();
        FVector v = ref.getRefOrigin().copy();

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

        resHead.mul(d1);

        resBase.mul(d2);
        resBase.sub(resHead);
        resBase.setCrossProduct(aHead);
        resBase.div(d3);

        aHead.add(resBase);

        resHead.applyStateFrom(aHead);

        return Optional.of(result);
    }

    // TODO - Not optimized
    @Override
    public Optional<FPoint> getFPointAtIntersection(FLine ref) {

        if (getRefOrigin().isOrthogonal(ref.getRefOrigin())) {
            return Optional.empty();
        }

        FPoint result = fVectorSupplier.get().getRefBase().copyZero();

        FVector u = getRefOrigin().copy();
        FVector v = ref.getRefOrigin().copy();

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

        in.sub(origin.getRefBase());
        in.normalize();

        double dotProduct = in.getDotProduct(headX, headY, headZ);

        in.mul(dotProduct);
        in.add(memoAX, memoAY, memoAZ);
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

        if ((distBase < originMag + epsilon) && (distHead < originMag + epsilon)) {
            return true;
        }

        return distHead < distBase + epsilon;
    }
}

// http://geomalgorithms.com/a05-_intersect-1.html
// https://opentextbc.ca/calculusv3openstax/chapter/equations-of-lines-and-planes-in-space/