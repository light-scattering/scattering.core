package eu.scattering.core.impl.mutables.geometry.construct.plane;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlane;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.impl.mutables.geometry.construct.ConstructPresetDef;
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

    private final Supplier<FLine> fLineSupplier;
    private final Supplier<FVector> fVectorSupplier;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FVector origin;
    private final double epsilon;

    private FPlaneDef(double epsilon, Supplier<FLine> fLineSupplier, Supplier<FVector> fOriginSupplier) {

        this.fLineSupplier = fLineSupplier;
        this.fVectorSupplier = fOriginSupplier;

        this.epsilon = epsilon;
        this.origin = fOriginSupplier.get();
    }

    private FPlaneDef(double epsilon, Supplier<FLine> fLineSupplier, Supplier<FVector> fOriginSupplier, FVector origin) {

        this.fLineSupplier = fLineSupplier;
        this.fVectorSupplier = fOriginSupplier;

        this.epsilon = epsilon;
        this.origin = origin;
    }

    public static FPlane create(double epsilon, Supplier<FLine> fLineSupplier, Supplier<FVector> fOriginSupplier) {

        return new FPlaneDef(epsilon, fLineSupplier, fOriginSupplier);
    }

    public static FPlane create(double epsilon, Supplier<FLine> fLineSupplier, Supplier<FVector> fOriginSupplier, FVector origin) {

        return new FPlaneDef(epsilon, fLineSupplier, fOriginSupplier, origin);
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

        getRefOrigin().set(position);

        return this;
    }

    @Override
    public FPlane applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        var structure = json.getJSONArray(JSON_VAL);
        var origin = fVectorSupplier.get().applyStateFrom(structure.getJSONObject(0));

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

        return create(epsilon, fLineSupplier, fVectorSupplier);
    }

    @Override
    public FPairPos3D toFPairPos3D() {

        return getRefOrigin().toFPairPos3D();
    }

    @Override
    public JSONObject toJSON() {
        var json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getRefOrigin().toJSON());

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
            var ref = (FLine) object;

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
    public boolean isCoplanar(FPlane ref) {

        return (getRefOrigin().isParallel(ref.getRefOrigin()) || getRefOrigin().isAntiParallel(ref.getRefOrigin()))
                && isAtomicPartOf(ref.getRefOrigin()).get(0);
    }

    @Override
    public void project(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(this::projectOnPlane);
    }

    @Override
    public void reflect(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(p -> p.reflect(projectOnPlane(p.copy())));
    }

    @Override
    public List<Boolean> isAtomicPartOf(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())) < epsilon)
                .collect(Collectors.toList());
    }

    @Override
    public List<Double> getAtomicDistanceP2(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> p.getDistanceP2(projectOnPlane(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Double> getAtomicDistance(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public void setDistance(Geometry geometry, double distance) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(p -> p.setDistance(projectOnPlane(p.copy()), distance));
    }

    @Override
    public boolean isCut(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        List<Boolean> isInHalfSpace = geometry.disassemble().stream()
                .map(p -> isInHalfSpace(projectOnLine(p.copy())))
                .collect(Collectors.toList());

        boolean conditionTrue = isInHalfSpace.stream().anyMatch(e -> e);
        boolean conditionFalse = isInHalfSpace.stream().anyMatch(e -> !e);

        return conditionTrue && conditionFalse;
    }

    @Override
    public List<Boolean> isAtomicOnSide(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> isInHalfSpace(projectOnLine(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FLine> getFLineAtIntersection(FPlane ref) {

        if (getRefOrigin().isParallel(ref.getRefOrigin()) || getRefOrigin().isAntiParallel(ref.getRefOrigin())) {
            return Optional.empty();
        }

        FPoint vPlane1 = getRefOrigin().copy().moveBaseToCenter().getRefHead();
        double d1 = -vPlane1.getDotProduct(getRefOrigin().getRefBase());

        FPoint vPlane2 = ref.getRefOrigin().copy().moveBaseToCenter().getRefHead();
        double d2 = -vPlane2.getDotProduct(ref.getRefOrigin().getRefBase());

        FPoint vPlanePar = vPlane1.copy().setCrossProduct(vPlane2);
        double vPlaneParDot = vPlanePar.getDotProduct(vPlanePar);

        FPoint pos = vPlane1.mul(d2).sub(vPlane2.mul(d1)).setCrossProduct(vPlanePar).div(vPlaneParDot);

        return Optional.of(fLineSupplier.get().setRefOrigin(fVectorSupplier.get().setRefHead(vPlanePar).moveBase(pos)));
    }

    @Override
    public Optional<FPoint> getFPointAtIntersection(FLine ref) {

        if (getRefOrigin().isOrthogonal(ref.getRefOrigin())) {
            return Optional.empty();
        }

        FPoint vPlane = getRefOrigin().copy().moveBaseToCenter().normalize().getRefHead();
        FPoint vLine = ref.getRefOrigin().copy().moveBaseToCenter().normalize().getRefHead();

        double dividend = vPlane.getDotProduct(getRefOrigin().getRefBase().copy().sub(ref.getRefOrigin().getRefBase()));
        double divisor = vPlane.getDotProduct(vLine);
        double distance = dividend / divisor;

        FVector extension = ref.getRefOrigin().copy().setLength(distance);

        return Optional.of(extension.getRefHead());
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint projectOnPlane(FPoint fPoint) {
        FPoint opA = getRefOrigin().getRefHead().copy()
                .sub(getRefOrigin().getRefBase())
                .div(getRefOrigin().getLength());

        FPoint opB = fPoint.copy()
                .sub(getRefOrigin().getRefBase());

        FPoint opC = fPoint.copyZero()
                .applyStateFrom(getRefOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        FVector translation = fVectorSupplier.get().set(opC, fPoint.copy())
                .moveBase(getRefOrigin().getRefBase());

        return fPoint.applyStateFrom(translation.getRefHead());
    }

    private FPoint projectOnLine(FPoint fPoint) {
        FPoint opA = getRefOrigin().getRefHead().copy()
                .sub(getRefOrigin().getRefBase())
                .div(getRefOrigin().getLength());

        FPoint opB = fPoint.copy()
                .sub(getRefOrigin().getRefBase());

        return fPoint.applyStateFrom(getRefOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));
    }

    private boolean isInHalfSpace(FPoint projection) {
        double magnitude = getRefOrigin().getLength();

        double distanceBase = getRefOrigin().getRefBase().getDistance(projection);
        double distanceHead = getRefOrigin().getRefHead().getDistance(projection);

        if ((distanceBase < magnitude + epsilon) && (distanceHead < magnitude + epsilon)) {
            return true;
        }

        return distanceHead < distanceBase + epsilon;
    }
}

// http://geomalgorithms.com/a05-_intersect-1.html
// https://opentextbc.ca/calculusv3openstax/chapter/equations-of-lines-and-planes-in-space/