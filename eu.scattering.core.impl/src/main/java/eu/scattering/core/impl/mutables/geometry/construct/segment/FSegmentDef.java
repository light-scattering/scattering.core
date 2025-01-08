package eu.scattering.core.impl.mutables.geometry.construct.segment;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.segment.FSegment;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.impl.mutables.geometry.construct.ConstructPresetDef;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static eu.scattering.core.impl.configurations.NameConfigDef.JSON_TYPE;

public class FSegmentDef extends ConstructPresetDef<FSegment> implements FSegment {
    private static final String JSON_MAIN = "segment";
    private static final String JSON_VAL = "val";

    private final Supplier<FVector> fVectorSupplier;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FVector origin;
    private final double epsilon;

    private FSegmentDef(double epsilon, Supplier<FVector> fOriginSupplier) {

        this.fVectorSupplier = fOriginSupplier;

        this.epsilon = epsilon;
        this.origin = fOriginSupplier.get();
    }

    private FSegmentDef(double epsilon, Supplier<FVector> fOriginSupplier, FVector origin) {

        this.fVectorSupplier = fOriginSupplier;

        this.epsilon = epsilon;
        this.origin = origin;
    }

    public static FSegment create(double epsilon, Supplier<FVector> fOriginSupplier) {

        return new FSegmentDef(epsilon, fOriginSupplier);
    }

    public static FSegment create(double epsilon, Supplier<FVector> fOriginSupplier, FVector origin) {

        return new FSegmentDef(epsilon, fOriginSupplier, origin);
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

        getRefOrigin().set(position);

        return this;
    }

    @Override
    public FSegment applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        var origin = fVectorSupplier.get().applyStateFrom(structure.getJSONObject(0));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSegment self() {

        return this;
    }

    @Override
    public FSegment copy() {

        return copyZero().setRefOrigin(getRefOrigin().copy());
    }

    @Override
    public FSegment copyZero() {

        return create(epsilon, fVectorSupplier);
    }

    @Override
    public FPairPos3D toFPairPos3D() {

        return getRefOrigin().toFPairPos3D();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

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

        if (object instanceof FSegment) {
            FSegment ref = (FSegment) object;

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

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(this::projectUnit);
    }

    @Override
    public void reflect(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(p -> projectUnit(p.copy()).ifPresent(p::reflect));
    }

    @Override
    public List<Boolean> isPartOf(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(this::isPartOfUnit)
                .collect(Collectors.toList());
    }

    @Override
    public List<OptionalDouble> getDistanceP2(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> projectUnit(p.copy())
                        .map(fPoint -> OptionalDouble.of(p.getDistanceP2(fPoint)))
                        .orElseGet(OptionalDouble::empty))
                .collect(Collectors.toList());
    }

    @Override
    public List<OptionalDouble> getDistance(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
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

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble()
                .forEach(p -> projectUnit(p.copy()).ifPresent(fPoint -> p.setDistance(fPoint, distance)));
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
        FPoint opA = getRefOrigin().getRefHead().copy()
                .sub(getRefOrigin().getRefBase())
                .div(getRefOrigin().getLength());

        FPoint opB = ref.copy()
                .sub(getRefOrigin().getRefBase());

        FPoint projection = ref.copy()
                .applyStateFrom(getRefOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        boolean isValid = projectUnitValidate(projection);

        return isValid ? Optional.of(ref.applyStateFrom(projection)) : Optional.empty();
    }

    private boolean projectUnitValidate(FPoint projection) {

        var distBase = getRefOrigin().getRefBase().getDistance(projection);
        var distHead = getRefOrigin().getRefHead().getDistance(projection);

        if (Math.abs(distBase + distHead - getRefOrigin().getLength()) < epsilon) {
            return true;
        }

        return false;
    }
}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html