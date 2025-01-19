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

    private final double epsilon;
    private FVector origin;

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

        geometry.disassemble().forEach(p -> {
            var x = p.getX();
            var y = p.getY();
            var z = p.getZ();

            p.reflect(projectUnit(p.copy()));
        });
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

        return setFPointAtX(fPointSupplier.get().setX(x));
    }

    @Override
    public Optional<FPoint> getFPointAtY(double y) {

        return setFPointAtY(fPointSupplier.get().setY(y));
    }

    @Override
    public Optional<FPoint> getFPointAtZ(double z) {

        return setFPointAtZ(fPointSupplier.get().setZ(z));
    }

    @Override
    public Optional<FPoint> getFPointAtIntersection(FLine ref) {

        if (getRefOrigin().isParallel(ref.getRefOrigin())) {
            return Optional.empty();
        }

        Direction dir = getProjectionType(ref.getRefOrigin());

        var u = projectOnPlane(dir, getRefOrigin().copy());
        var v = projectOnPlane(dir, ref.getRefOrigin().copy());
        var w = fVectorSupplier.get().set(v.getRefBase(), u.getRefBase());

        v = getCrossProduct(dir, v);

        double scaleFactor = v.copy().reflectHead().getDotProduct(w) / v.getDotProduct(u);

        return validateCandidate(ref, setCandidate3D(setCandidate2D(u, scaleFactor), dir).orElse(null));
    }

    // -------------------------------------------------------------------------------------------------

    private enum Direction { XY, YZ, XZ };

    private Optional<FPoint> setFPointAtX(FPoint arg) {
        var origin = getRefOrigin();

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getX() == oHead.getX()) {
            return Optional.empty();
        }

        var l = oHead.getX() - oBase.getX();
        var m = oHead.getY() - oBase.getY();
        var n = oHead.getZ() - oBase.getZ();

        var y = oBase.getY() + (m / l * (arg.getX() - oBase.getX()));
        var z = oBase.getZ() + (n / l * (arg.getX() - oBase.getX()));

        return Optional.of(arg.setY(y).setZ(z));
    }

    private Optional<FPoint> setFPointAtY(FPoint arg) {
        var origin = getRefOrigin();

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getY() == oHead.getY()) {
            return Optional.empty();
        }

        double l = oHead.getX() - oBase.getX();
        double m = oHead.getY() - oBase.getY();
        double n = oHead.getZ() - oBase.getZ();

        double x = oBase.getX() + (l / m * (arg.getY() - oBase.getY()));
        double z = oBase.getZ() + (n / m * (arg.getY() - oBase.getY()));

        return Optional.of(arg.setX(x).setZ(z));
    }

    private Optional<FPoint> setFPointAtZ(FPoint arg) {
        var origin = getRefOrigin();

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getZ() == oHead.getZ()) {
            return Optional.empty();
        }

        double l = oHead.getX() - oBase.getX();
        double m = oHead.getY() - oBase.getY();
        double n = oHead.getZ() - oBase.getZ();

        double x = oBase.getX() + (l / n * (arg.getZ() - oBase.getZ()));
        double y = oBase.getY() + (m / n * (arg.getZ() - oBase.getZ()));

        return Optional.of(arg.setX(x).setY(y));
    }

    private Direction getProjectionType(FVector ref) {

        if ((getRefOrigin().getLengthX() > 0 || getRefOrigin().getLengthY() > 0) &&
                (ref.getLengthX() > 0 || ref.getLengthY() > 0)) {

            return Direction.XY;
        }

        if ((getRefOrigin().getLengthY() > 0 || getRefOrigin().getLengthZ() > 0) &&
                (ref.getLengthY() > 0 || ref.getLengthZ() > 0)) {

            return Direction.YZ;
        }

        if ((getRefOrigin().getLengthX() > 0 || getRefOrigin().getLengthZ() > 0) &&
                (ref.getLengthX() > 0 || ref.getLengthZ() > 0)) {

            return Direction.XZ;
        }

        throw new IllegalStateException("The projection plane cannot be determined");
    }

    private FVector projectOnPlane(Direction dir, FVector ref) {

        switch (dir) {
            case XY:
                ref.getRefBase().setZ(0);
                ref.getRefHead().setZ(0);

                return ref;
            case YZ:
                ref.getRefBase().setX(0);
                ref.getRefHead().setX(0);

                return ref;
            case XZ:
                ref.getRefBase().setY(0);
                ref.getRefHead().setY(0);

                return ref;
        }

        throw new IllegalStateException("The FVector cannot be projected on any plane. Direction " + dir);
    }

    private FVector getCrossProduct(Direction dir, FVector ref) {

        switch (dir) {
            case XY:
                return ref.setCrossProduct(fVectorSupplier.get().setRefHead(ref.getRefBase().copy().setZ(1)));
            case YZ:
                return ref.setCrossProduct(fVectorSupplier.get().setRefHead(ref.getRefBase().copy().setX(1)));
            case XZ:
                return ref.setCrossProduct(fVectorSupplier.get().setRefHead(ref.getRefBase().copy().setY(1)));
        }

        throw new IllegalStateException("The cross product cannot be calculated. Value " + dir);
    }

    private FPoint setCandidate2D(FVector ref, double scaleFactor) {
        double baseX = ref.getRefBase().getX();
        double baseY = ref.getRefBase().getY();
        double baseZ = ref.getRefBase().getZ();

        return ref.mul(scaleFactor).moveBase(baseX, baseY, baseZ).getRefHead();
    }

    private Optional<FPoint> setCandidate3D(FPoint ref, Direction dir) {

        switch (dir) {
            case XY: return setCandidate3DXY(ref, dir);
            case YZ: return setCandidate3DYZ(ref, dir);
            case XZ: return setCandidate3DXZ(ref, dir);
        }

        throw new IllegalStateException("The FPoint candidate cannot be calculated. Direction " + dir);
    }

    private Optional<FPoint> setCandidate3DXY(FPoint ref, Direction dir) {
        Optional<FPoint> candidate;
        double memoY = ref.getY();

        candidate = setFPointAtX(ref);

        if (candidate.isPresent()) {
            return candidate;
        }

        candidate = setFPointAtY(ref.setY(memoY));

        if (candidate.isPresent()) {
            return candidate;
        }

        throw new IllegalStateException("The FPoint candidate cannot be calculated. Direction - " + dir);
    }

    private Optional<FPoint> setCandidate3DYZ(FPoint ref, Direction dir) {
        Optional<FPoint> candidate;
        double memoZ = ref.getZ();

        candidate = setFPointAtY(ref);

        if (candidate.isPresent()) {
            return candidate;
        }

        candidate = setFPointAtZ(ref.setZ(memoZ));

        if (candidate.isPresent()) {
            return candidate;
        }

        throw new IllegalStateException("The FPoint candidate cannot be calculated. Direction - " + dir);
    }

    private Optional<FPoint> setCandidate3DXZ(FPoint ref, Direction dir) {
        Optional<FPoint> candidate;
        double memoZ = ref.getZ();

        candidate = setFPointAtX(ref);

        if (candidate.isPresent()) {
            return candidate;
        }

        candidate = setFPointAtZ(ref.setZ(memoZ));

        if (candidate.isPresent()) {
            return candidate;
        }

        throw new IllegalStateException("The FPoint candidate cannot be calculated. Direction - " + dir);
    }

    private Optional<FPoint> validateCandidate(FLine arg, FPoint candidate) {

        if (candidate == null) {
            return Optional.empty();
        }

        if (isPartOf(candidate) && arg.isPartOf(candidate)) {
            return Optional.of(candidate);
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint projectUnit(FPoint fPoint) {

        getRefOrigin().applyWithFixedState(o -> {
            var oBase = o.getRefBase();
            var oHead = o.getRefHead();

            var oMagnitude = o.getMagnitude();

            fPoint.sub(oBase);

            oHead.sub(oBase);
            oHead.div(oMagnitude);

            oHead.mul(fPoint.getDotProduct(oHead));
            oBase.add(oHead);

            fPoint.applyStateFrom(oBase);
        });

        return fPoint;
    }
}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html