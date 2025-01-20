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
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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
    public boolean isCollinear(FLine arg) {

        return arg.isPartOf(origin);
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
            var oX = p.getX();
            var oY = p.getY();
            var oZ = p.getZ();

            projectUnit(p);

            var pX = p.getX();
            var pY = p.getY();
            var pZ = p.getZ();

            p.set(oX, oY, oZ).reflect(pX, pY, pZ);
        });
    }

    @Override
    public boolean isPartOf(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .allMatch(p -> {
                    var oX = p.getX();
                    var oY = p.getY();
                    var oZ = p.getZ();

                    projectUnit(p);

                    var pX = p.getX();
                    var pY = p.getY();
                    var pZ = p.getZ();

                    return p.set(oX, oY, oZ).getDistance(pX, pY, pZ) < epsilon;
                });
    }

    @Override
    public List<Double> getAtomicDistanceP2(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> {
                    var oX = p.getX();
                    var oY = p.getY();
                    var oZ = p.getZ();

                    projectUnit(p);

                    var pX = p.getX();
                    var pY = p.getY();
                    var pZ = p.getZ();

                    return p.set(oX, oY, oZ).getDistanceP2(pX, pY, pZ);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Double> getAtomicDistance(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> {
                    var oX = p.getX();
                    var oY = p.getY();
                    var oZ = p.getZ();

                    projectUnit(p);

                    var pX = p.getX();
                    var pY = p.getY();
                    var pZ = p.getZ();

                    return p.set(oX, oY, oZ).getDistance(pX, pY, pZ);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void setDistance(Geometry geometry, double distance) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(p -> {
            var oX = p.getX();
            var oY = p.getY();
            var oZ = p.getZ();

            projectUnit(p);

            var pX = p.getX();
            var pY = p.getY();
            var pZ = p.getZ();

            p.set(oX, oY, oZ).setDistance(pX, pY, pZ, distance);
        });
    }

    @Override
    public Optional<FPoint> getFPointAtX(double x) {
        var fPoint = fPointSupplier.get().setX(x);

        setFPointAtX(fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    @Override
    public Optional<FPoint> getFPointAtY(double y) {
        var fPoint = fPointSupplier.get().setY(y);

        setFPointAtY(fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    @Override
    public Optional<FPoint> getFPointAtZ(double z) {
        var fPoint = fPointSupplier.get().setZ(z);

        setFPointAtZ(fPoint);

        return isValid(fPoint) ? Optional.of(fPoint) : Optional.empty();
    }

    @Override
    public Optional<FPoint> getFPointAtIntersection(FLine arg) {
        var refOrigin = getRefOrigin();
        var argOrigin = arg.getRefOrigin();

        if (refOrigin.isParallel(argOrigin)) {
            return Optional.empty();
        }

        Direction dir = getProjectionType(argOrigin);

        var u = projectOnPlane(refOrigin.copy(), dir);
        var v = projectOnPlane(argOrigin.copy(), dir);

        var wX = u.getRefBase().getX() - v.getRefBase().getX();
        var wY = u.getRefBase().getY() - v.getRefBase().getY();
        var wZ = u.getRefBase().getZ() - v.getRefBase().getZ();

        setCrossProduct(v, dir);

        double vuDot = v.getDotProduct(u);
        double vwDot = v.moveBaseToCenter().getRefHead().getDotProduct(wX, wY, wZ);

        double scaleFactor = -vwDot / vuDot;

        var res = setCandidate2D(u, scaleFactor);
        setCandidate3D(res, dir);
        return validateCandidate(arg, res);
//        return validateCandidate(arg, setCandidate3D(setCandidate2D(u, scaleFactor), dir).orElse(null));
    }

    // -------------------------------------------------------------------------------------------------

    private enum Direction { XY, YZ, XZ }

    private void setFPointAtX(FPoint arg) {
        var origin = getRefOrigin();

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getX() == oHead.getX()) {
            invalidate(arg);
            return;
        }

        var l = oHead.getX() - oBase.getX();
        var m = oHead.getY() - oBase.getY();
        var n = oHead.getZ() - oBase.getZ();

        var y = oBase.getY() + (m / l * (arg.getX() - oBase.getX()));
        var z = oBase.getZ() + (n / l * (arg.getX() - oBase.getX()));

        arg.setY(y).setZ(z);
    }

    private void setFPointAtY(FPoint arg) {
        var origin = getRefOrigin();

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getY() == oHead.getY()) {
            invalidate(arg);
            return;
        }

        double l = oHead.getX() - oBase.getX();
        double m = oHead.getY() - oBase.getY();
        double n = oHead.getZ() - oBase.getZ();

        double x = oBase.getX() + (l / m * (arg.getY() - oBase.getY()));
        double z = oBase.getZ() + (n / m * (arg.getY() - oBase.getY()));

        arg.setX(x).setZ(z);
    }

    private void setFPointAtZ(FPoint arg) {
        var origin = getRefOrigin();

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getZ() == oHead.getZ()) {
            invalidate(arg);
            return;
        }

        double l = oHead.getX() - oBase.getX();
        double m = oHead.getY() - oBase.getY();
        double n = oHead.getZ() - oBase.getZ();

        double x = oBase.getX() + (l / n * (arg.getZ() - oBase.getZ()));
        double y = oBase.getY() + (m / n * (arg.getZ() - oBase.getZ()));

        arg.setX(x).setY(y);
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

    private FVector projectOnPlane(FVector ref, Direction dir) {

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

    private void setCrossProduct(FVector ref, Direction dir) {
        double memoX = ref.getRefBase().getX();
        double memoY = ref.getRefBase().getY();
        double memoZ = ref.getRefBase().getZ();

        switch (dir) {
            case XY:
                ref.applyWithCenteredPosition(v -> v.getRefHead().setCrossProduct(memoX, memoY, 1));
                break;
            case YZ:
                ref.applyWithCenteredPosition(v -> v.getRefHead().setCrossProduct(1, memoY, memoZ));
                break;
            case XZ:
                ref.applyWithCenteredPosition(v -> v.getRefHead().setCrossProduct(memoX, 1, memoZ));
                break;
        }
    }

    private FPoint setCandidate2D(FVector ref, double scaleFactor) {
        double baseX = ref.getRefBase().getX();
        double baseY = ref.getRefBase().getY();
        double baseZ = ref.getRefBase().getZ();

        return ref.mul(scaleFactor).moveBase(baseX, baseY, baseZ).getRefHead();
    }

    private void setCandidate3D(FPoint ref, Direction dir) {

        switch (dir) {
            case XY:
                setCandidate3DXY(ref);
                break;
            case YZ:
                setCandidate3DYZ(ref);
                break;
            case XZ:
                setCandidate3DXZ(ref);
                break;
        }
    }

    private void setCandidate3DXY(FPoint ref) {
        double memoY = ref.getY();

        setFPointAtX(ref);

        if (!Double.isNaN(ref.getX()) && !Double.isNaN(ref.getY()) && !Double.isNaN(ref.getZ())) {
            return;
        }

        setFPointAtY(ref.setY(memoY));

        if (!Double.isNaN(ref.getX()) && !Double.isNaN(ref.getY()) && !Double.isNaN(ref.getZ())) {
            return;
        }

        ref.set(Double.NaN, Double.NaN, Double.NaN);
    }

    private void setCandidate3DYZ(FPoint ref) {
        double memoZ = ref.getZ();

        setFPointAtY(ref);

        if (!Double.isNaN(ref.getX()) && !Double.isNaN(ref.getY()) && !Double.isNaN(ref.getZ())) {
            return;
        }

        setFPointAtZ(ref.setZ(memoZ));

        if (!Double.isNaN(ref.getX()) && !Double.isNaN(ref.getY()) && !Double.isNaN(ref.getZ())) {
            return;
        }

        ref.set(Double.NaN, Double.NaN, Double.NaN);
    }

    private void setCandidate3DXZ(FPoint ref) {
        double memoZ = ref.getZ();

        setFPointAtX(ref);

        if (!Double.isNaN(ref.getX()) && !Double.isNaN(ref.getY()) && !Double.isNaN(ref.getZ())) {
            return;
        }

        setFPointAtZ(ref.setZ(memoZ));

        if (!Double.isNaN(ref.getX()) && !Double.isNaN(ref.getY()) && !Double.isNaN(ref.getZ())) {
            return;
        }

        ref.set(Double.NaN, Double.NaN, Double.NaN);
    }

    private Optional<FPoint> validateCandidate(FLine arg, FPoint candidate) {

        if (Double.isNaN(candidate.getX()) || Double.isNaN(candidate.getY()) || Double.isNaN(candidate.getZ())) {
            return Optional.empty();
        }

        if (isPartOf(candidate) && arg.isPartOf(candidate)) {
            return Optional.of(candidate);
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------------------------------------------

    private void projectUnit(FPoint fPoint) {

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
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isValid(FPoint ref) {

        return !Double.isNaN(ref.getX()) && !Double.isNaN(ref.getY()) && !Double.isNaN(ref.getZ());
    }

    private void invalidate(FPoint ref) {

        ref.set(Double.NaN, Double.NaN, Double.NaN);
    }
}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html