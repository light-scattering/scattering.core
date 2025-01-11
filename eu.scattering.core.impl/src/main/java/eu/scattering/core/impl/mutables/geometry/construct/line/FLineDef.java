package eu.scattering.core.impl.mutables.geometry.construct.line;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
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

public class FLineDef extends ConstructPresetDef<FLine> implements FLine {
    private static final String JSON_MAIN = "line";
    private static final String JSON_VAL = "val";

    private final Supplier<FVector> fVectorSupplier;
    private final Supplier<FPoint> fPointSupplier;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FVector origin;
    private final double epsilon;

    private FLineDef(double epsilon, Supplier<FVector> fOriginSupplier) {

        this.fVectorSupplier = fOriginSupplier;
        this.fPointSupplier = () -> getRefOrigin().getRefBase().copyZero();

        this.epsilon = epsilon;
        this.origin = fOriginSupplier.get();
    }

    private FLineDef(double epsilon, Supplier<FVector> fOriginSupplier, FVector origin) {

        this.fVectorSupplier = fOriginSupplier;
        this.fPointSupplier = () -> getRefOrigin().getRefBase().copyZero();

        this.epsilon = epsilon;
        this.origin = origin;
    }

    public static FLine create(double epsilon, Supplier<FVector> fOriginSupplier) {

        return new FLineDef(epsilon, fOriginSupplier);
    }

    public static FLine create(double epsilon, Supplier<FVector> fOriginSupplier, FVector origin) {

        return new FLineDef(epsilon, fOriginSupplier, origin);
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

        var structure = json.getJSONArray(JSON_VAL);
        var origin = fVectorSupplier.get().applyStateFrom(structure.getJSONObject(0));

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
    public boolean isCollinear(FLine ref) {

        return ref.isPartOf(origin);
    }

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

        geometry.disassemble().forEach(p -> p.reflect(projectUnit(p.copy())));
    }

    @Override
    public boolean isPartOf(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .allMatch(p -> p.getDistance(projectUnit(p.copy())) < epsilon);
    }

    @Override
    public List<Double> getAtomicDistanceP2(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> p.getDistanceP2(projectUnit(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Double> getAtomicDistance(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> p.getDistance(projectUnit(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public void setDistance(Geometry geometry, double distance) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(p -> p.setDistance(projectUnit(p.copy()), distance));
    }

    @Override
    public Optional<FPoint> getFPointAtX(double x) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        if (getRefOrigin().getRefBase().getX() == getRefOrigin().getRefHead().getX()) {
            return Optional.empty();
        }

        var l = getRefOrigin().getRefHead().getX() - getRefOrigin().getRefBase().getX();
        var m = getRefOrigin().getRefHead().getY() - getRefOrigin().getRefBase().getY();
        var n = getRefOrigin().getRefHead().getZ() - getRefOrigin().getRefBase().getZ();

        var y = getRefOrigin().getRefBase().getY() + (m / l * (x - getRefOrigin().getRefBase().getX()));
        var z = getRefOrigin().getRefBase().getZ() + (n / l * (x - getRefOrigin().getRefBase().getX()));

        return Optional.of(fPointSupplier.get().set(x, y, z));
    }

    @Override
    public Optional<FPoint> getFPointAtY(double y) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        if (getRefOrigin().getRefBase().getY() == getRefOrigin().getRefHead().getY()) {
            return Optional.empty();
        }

        double l = getRefOrigin().getRefHead().getX() - getRefOrigin().getRefBase().getX();
        double m = getRefOrigin().getRefHead().getY() - getRefOrigin().getRefBase().getY();
        double n = getRefOrigin().getRefHead().getZ() - getRefOrigin().getRefBase().getZ();

        double x = getRefOrigin().getRefBase().getX() + (l / m * (y - getRefOrigin().getRefBase().getY()));
        double z = getRefOrigin().getRefBase().getZ() + (n / m * (y - getRefOrigin().getRefBase().getY()));

        return Optional.of(fPointSupplier.get().set(x, y, z));
    }

    @Override
    public Optional<FPoint> getFPointAtZ(double z) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        if (getRefOrigin().getRefBase().getZ() == getRefOrigin().getRefHead().getZ()) {
            return Optional.empty();
        }

        double l = getRefOrigin().getRefHead().getX() - getRefOrigin().getRefBase().getX();
        double m = getRefOrigin().getRefHead().getY() - getRefOrigin().getRefBase().getY();
        double n = getRefOrigin().getRefHead().getZ() - getRefOrigin().getRefBase().getZ();

        double x = getRefOrigin().getRefBase().getX() + (l / n * (z - getRefOrigin().getRefBase().getZ()));
        double y = getRefOrigin().getRefBase().getY() + (m / n * (z - getRefOrigin().getRefBase().getZ()));

        return Optional.of(fPointSupplier.get().set(x, y, z));
    }

    @Override
    public Optional<FPoint> getFPointAtIntersection(FLine ref) {

        if (getRefOrigin().isParallel(ref.getRefOrigin())) {
            return Optional.empty();
        }

        String dir = getProjectionType(ref.getRefOrigin());

        var u = projectOnPlane(dir, getRefOrigin().copy());
        var v = projectOnPlane(dir, ref.getRefOrigin().copy());
        var w = fVectorSupplier.get().set(v.getRefBase(), u.getRefBase());

        v = getCrossProduct(dir, v);

        double scaleFactor = v.copy().reflectHead().getDotProduct(w) / v.getDotProduct(u);

        return validateCandidate(ref, getCandidate3D(dir, getCandidate2D(u, scaleFactor)).orElse(null));
    }

    // -------------------------------------------------------------------------------------------------

    private String getProjectionType(FVector ref) {

        if ((getRefOrigin().getLengthX() > 0 || getRefOrigin().getLengthY() > 0) &&
                (ref.getLengthX() > 0 || ref.getLengthY() > 0)) {

            return "XY";
        }

        if ((getRefOrigin().getLengthY() > 0 || getRefOrigin().getLengthZ() > 0) &&
                (ref.getLengthY() > 0 || ref.getLengthZ() > 0)) {

            return "YZ";
        }

        if ((getRefOrigin().getLengthX() > 0 || getRefOrigin().getLengthZ() > 0) &&
                (ref.getLengthX() > 0 || ref.getLengthZ() > 0)) {

            return "XZ";
        }

        throw new IllegalStateException("The projection plane cannot be determined");
    }

    private FVector projectOnPlane(String dir, FVector ref) {

        switch (dir) {
            case "XY":
                ref.getRefBase().setZ(0);
                ref.getRefHead().setZ(0);

                return ref;
            case "YZ":
                ref.getRefBase().setX(0);
                ref.getRefHead().setX(0);

                return ref;
            case "XZ":
                ref.getRefBase().setY(0);
                ref.getRefHead().setY(0);

                return ref;
        }

        throw new IllegalStateException("The FVector cannot be projected on any plane. Value " + dir);
    }

    private FVector getCrossProduct(String dir, FVector ref) {

        switch (dir) {
            case "XY":
                return ref.setCrossProduct(fVectorSupplier.get().setRefHead(ref.getRefBase().copy().setZ(1)));
            case "YZ":
                return ref.setCrossProduct(fVectorSupplier.get().setRefHead(ref.getRefBase().copy().setX(1)));
            case "XZ":
                return ref.setCrossProduct(fVectorSupplier.get().setRefHead(ref.getRefBase().copy().setY(1)));
        }

        throw new IllegalStateException("The cross product cannot be calculated. Value " + dir);
    }

    private FPoint getCandidate2D(FVector ref, double scaleFactor) {

        return ref.copy().mul(scaleFactor).moveBase(ref.getRefBase()).getRefHead();
    }

    private Optional<FPoint> getCandidate3D(String dir, FPoint ref) {

        Optional<FPoint> candidate;

        switch (dir) {
            case "XY":
                candidate = getFPointAtX(ref.getX());

                if (candidate.isPresent()) {
                    return candidate;
                }

                candidate = getFPointAtY(ref.getY());

                if (candidate.isPresent()) {
                    return candidate;
                }

                throw new IllegalStateException("The FPoint candidate cannot be calculated. Value " + dir);
            case "YZ":
                candidate = getFPointAtY(ref.getY());

                if (candidate.isPresent()) {
                    return candidate;
                }

                candidate = getFPointAtZ(ref.getZ());

                if (candidate.isPresent()) {
                    return candidate;
                }

                throw new IllegalStateException("The FPoint candidate cannot be calculated. Value " + dir);
            case "XZ":
                candidate = getFPointAtX(ref.getX());

                if (candidate.isPresent()) {
                    return candidate;
                }

                candidate = getFPointAtZ(ref.getZ());

                if (candidate.isPresent()) {
                    return candidate;
                }

                throw new IllegalStateException("The FPoint candidate cannot be calculated. Value " + dir);
        }

        throw new IllegalStateException("The FPoint candidate cannot be calculated. Value " + dir);
    }

    private Optional<FPoint> validateCandidate(FLine ref, FPoint candidate) {

        if (candidate == null) {
            return Optional.empty();
        }

        if (isPartOf(candidate) && ref.isPartOf(candidate)) {
            return Optional.of(candidate);
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint projectUnit(FPoint fPoint) {
        var opA = getRefOrigin().getRefHead().copy()
                .sub(getRefOrigin().getRefBase())
                .div(getRefOrigin().getLength());

        var opB = fPoint.copy()
                .sub(getRefOrigin().getRefBase());

        fPoint.applyStateFrom(getRefOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        return fPoint;
    }
}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html