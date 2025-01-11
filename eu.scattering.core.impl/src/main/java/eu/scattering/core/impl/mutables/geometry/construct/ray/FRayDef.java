package eu.scattering.core.impl.mutables.geometry.construct.ray;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRay;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.impl.mutables.geometry.construct.ConstructPresetDef;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static eu.scattering.core.impl.configurations.NameConfigDef.JSON_TYPE;

public class FRayDef extends ConstructPresetDef<FRay> implements FRay {
    private static final String JSON_MAIN = "ray";
    private static final String JSON_VAL = "val";

    private final Supplier<FVector> fVectorSupplier;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FVector origin;
    private final double epsilon;

    private FRayDef(double epsilon, Supplier<FVector> fOriginSupplier) {

        this.fVectorSupplier = fOriginSupplier;

        this.epsilon = epsilon;
        this.origin = fOriginSupplier.get();
    }

    private FRayDef(double epsilon, Supplier<FVector> fOriginSupplier, FVector origin) {

        this.fVectorSupplier = fOriginSupplier;

        this.epsilon = epsilon;
        this.origin = origin;
    }

    public static FRay create(double epsilon, Supplier<FVector> fOriginSupplier) {

        return new FRayDef(epsilon, fOriginSupplier);
    }

    public static FRay create(double epsilon, Supplier<FVector> fOriginSupplier, FVector origin) {

        return new FRayDef(epsilon, fOriginSupplier, origin);
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

        getRefOrigin().set(position);

        return this;
    }

    @Override
    public FRay applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        var structure = json.getJSONArray(JSON_VAL);
        var origin = fVectorSupplier.get().applyStateFrom(structure.getJSONObject(0));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FRay self() {

        return this;
    }

    @Override
    public FRay copy() {

        return copyZero().setRefOrigin(getRefOrigin().copy());
    }

    @Override
    public FRay copyZero() {

        return create(epsilon, fVectorSupplier);
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

        if (object instanceof FRay) {
            var ref = (FRay) object;

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
    public void project(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(this::projectUnit);
    }

    @Override
    public void reflect(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(p -> projectUnit(p.copy()).ifPresent(p::reflect));
    }

    @Override
    public boolean isPartOf(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .allMatch(this::isPartOfUnit);
    }

    @Override
    public List<OptionalDouble> getAtomicDistanceP2(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> projectUnit(p.copy())
                        .map(fPoint -> OptionalDouble.of(p.getDistanceP2(fPoint)))
                        .orElseGet(OptionalDouble::empty))
                .collect(Collectors.toList());
    }

    @Override
    public List<OptionalDouble> getAtomicDistance(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> projectUnit(p.copy())
                        .map(fPoint -> OptionalDouble.of(p.getDistance(fPoint)))
                        .orElseGet(OptionalDouble::empty))
                .collect(Collectors.toList());
    }

    @Override
    public void setDistance(Geometry geometry, double distance) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble()
                .forEach(p -> projectUnit(p.copy())
                        .ifPresent(fPoint -> p.setDistance(fPoint, distance)));
    }

    @Override
    public void shiftForward(Geometry geometry, double distance) {

        geometry.disassemble().forEach(p -> shiftForwardUnit(p, distance));
    }

    @Override
    public void shiftBackward(Geometry geometry, double distance) {

        geometry.disassemble().forEach(p -> shiftBackwardUnit(p, distance));
    }

    @Override
    public FPoint getFPointAtDistance(double length) {

        if (length < 0) {
            throw new IllegalArgumentException("The distance must be a positive value");
        }

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var fPoint = getRefOrigin().getRefHead().copy().sub(getRefOrigin().getRefBase());
        var tmp = length / getRefOrigin().getLength();

        fPoint.setX(getRefOrigin().getRefBase().getX() + (fPoint.getX() * tmp));
        fPoint.setY(getRefOrigin().getRefBase().getY() + (fPoint.getY() * tmp));
        fPoint.setZ(getRefOrigin().getRefBase().getZ() + (fPoint.getZ() * tmp));

        return fPoint;
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isPartOfUnit(FPoint ref) {
        var projection = projectUnit(ref.copy());

        if (projection.isEmpty()) {
            return false;
        }

        return projection.get().getDistance(ref) < epsilon;
    }

    private Optional<FPoint> projectUnit(FPoint ref) {
        var opA = getRefOrigin().getRefHead().copy()
                .sub(getRefOrigin().getRefBase())
                .div(getRefOrigin().getLength());

        var opB = ref.copy()
                .sub(getRefOrigin().getRefBase());

        var projection = ref.copy()
                .applyStateFrom(getRefOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        var isValid = projectUnitValidate(projection);

        return isValid ? Optional.of(ref.applyStateFrom(projection)) : Optional.empty();
    }

    private boolean projectUnitValidate(FPoint projection) {
        var distBase = getRefOrigin().getRefBase().getDistance(projection);
        var distHead = getRefOrigin().getRefHead().getDistance(projection);

        if (Math.abs(distBase + distHead - getRefOrigin().getLength()) < epsilon) {
            return true;
        }

        return distBase > distHead;
    }

    private void shiftForwardUnit(FPoint ref, double distance) {

        ref.applyStateFrom(getRefOrigin().copy().moveBase(ref).shiftForward(distance).getRefBase());
    }

    private void shiftBackwardUnit(FPoint ref, double distance) {

        ref.applyStateFrom(getRefOrigin().copy().moveBase(ref).shiftBackward(distance).getRefBase());
    }
}