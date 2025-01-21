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

    private FLineDef(double epsilon, FVector origin) {

        this.fVectorSupplier = origin::copyZero;
        this.fPointSupplier = () -> getRefOrigin().getRefBase().copyZero();

        this.epsilon = epsilon;
        this.origin = origin;
    }

    public static FLine create(double epsilon, FVector origin) {

        return new FLineDef(epsilon, origin);
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

        var origin = fVectorSupplier.get().applyStateFrom(json.getJSONObject(JSON_VAL));

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

        return create(epsilon, fVectorSupplier.get());
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
        FVector refOrigin = getRefOrigin();
        FVector argOrigin = arg.getRefOrigin();

        if (refOrigin.isParallel(argOrigin)) {
            return Optional.empty();
        }

        Direction dir = getProjectionDirection(argOrigin);

        FVector u = refOrigin.copy();
        FVector v = argOrigin.copy();

        projectOnPlane(u, dir);
        projectOnPlane(v, dir);

        double wX = u.getRefBase().getX() - v.getRefBase().getX();
        double wY = u.getRefBase().getY() - v.getRefBase().getY();
        double wZ = u.getRefBase().getZ() - v.getRefBase().getZ();

        setCrossProduct(v, dir);

        double vuDot = v.getDotProduct(u);
        double vwDot = v.moveBaseToCenter().getRefHead().getDotProduct(wX, wY, wZ);

        double scaleFactor = -vwDot / vuDot;

        return parseCandidate(arg, setCandidate3D(setCandidate2D(u, scaleFactor), dir));
    }

    // -------------------------------------------------------------------------------------------------

    private enum Direction { XY, YZ, XZ }

    private Direction getProjectionDirection(FVector arg) {
        double oX = getRefOrigin().getLengthX();
        double oY = getRefOrigin().getLengthY();
        double oZ = getRefOrigin().getLengthZ();
        double aX = arg.getLengthX();
        double aY = arg.getLengthY();
        double aZ = arg.getLengthZ();

        if ((oX > 0 || oY > 0) && (aX > 0 || aY > 0)) {
            return Direction.XY;
        }

        if ((oY > 0 || oZ > 0) && (aY > 0 || aZ > 0)) {
            return Direction.YZ;
        }

        if ((oX > 0 || oZ > 0) && (aX > 0 || aZ > 0)) {
            return Direction.XZ;
        }

        throw new IllegalStateException("The projection plane cannot be determined");
    }

    private void setFPointAtX(FPoint in) {
        var origin = getRefOrigin();

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getX() == oHead.getX()) {
            invalidate(in);
            return;
        }

        var l = oHead.getX() - oBase.getX();
        var m = oHead.getY() - oBase.getY();
        var n = oHead.getZ() - oBase.getZ();

        var y = oBase.getY() + (m / l * (in.getX() - oBase.getX()));
        var z = oBase.getZ() + (n / l * (in.getX() - oBase.getX()));

        in.setY(y).setZ(z);
    }

    private void setFPointAtY(FPoint in) {
        var origin = getRefOrigin();

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getY() == oHead.getY()) {
            invalidate(in);
            return;
        }

        double l = oHead.getX() - oBase.getX();
        double m = oHead.getY() - oBase.getY();
        double n = oHead.getZ() - oBase.getZ();

        double x = oBase.getX() + (l / m * (in.getY() - oBase.getY()));
        double z = oBase.getZ() + (n / m * (in.getY() - oBase.getY()));

        in.setX(x).setZ(z);
    }

    private void setFPointAtZ(FPoint in) {
        var origin = getRefOrigin();

        if (origin.isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var oBase = origin.getRefBase();
        var oHead = origin.getRefHead();

        if (oBase.getZ() == oHead.getZ()) {
            invalidate(in);
            return;
        }

        double l = oHead.getX() - oBase.getX();
        double m = oHead.getY() - oBase.getY();
        double n = oHead.getZ() - oBase.getZ();

        double x = oBase.getX() + (l / n * (in.getZ() - oBase.getZ()));
        double y = oBase.getY() + (m / n * (in.getZ() - oBase.getZ()));

        in.setX(x).setY(y);
    }

    private void setCrossProduct(FVector in, Direction dir) {
        double memoX = in.getRefBase().getX();
        double memoY = in.getRefBase().getY();
        double memoZ = in.getRefBase().getZ();

        switch (dir) {
            case XY:
                in.applyWithCenteredPosition(v -> v.getRefHead().setCrossProduct(memoX, memoY, 1));
                break;
            case YZ:
                in.applyWithCenteredPosition(v -> v.getRefHead().setCrossProduct(1, memoY, memoZ));
                break;
            case XZ:
                in.applyWithCenteredPosition(v -> v.getRefHead().setCrossProduct(memoX, 1, memoZ));
                break;
        }
    }

    private void projectOnPlane(FVector in, Direction dir) {

        switch (dir) {
            case XY:
                in.getRefBase().setZ(0);
                in.getRefHead().setZ(0);
                break;
            case YZ:
                in.getRefBase().setX(0);
                in.getRefHead().setX(0);
                break;
            case XZ:
                in.getRefBase().setY(0);
                in.getRefHead().setY(0);
                break;
        }
    }

    private FPoint setCandidate2D(FVector in, double factor) {
        double baseX = in.getRefBase().getX();
        double baseY = in.getRefBase().getY();
        double baseZ = in.getRefBase().getZ();

        return in.mul(factor).moveBase(baseX, baseY, baseZ).getRefHead();
    }

    private FPoint setCandidate3D(FPoint in, Direction dir) {

        switch (dir) {
            case XY:
                setCandidate3DXY(in);
                break;
            case YZ:
                setCandidate3DYZ(in);
                break;
            case XZ:
                setCandidate3DXZ(in);
                break;
        }

        return in;
    }

    private void setCandidate3DXY(FPoint in) {
        double memoY = in.getY();

        setFPointAtX(in);

        if (isValid(in)) {
            return;
        }

        setFPointAtY(in.setY(memoY));

        if (isValid(in)) {
            return;
        }

        invalidate(in);
    }

    private void setCandidate3DYZ(FPoint in) {
        double memoZ = in.getZ();

        setFPointAtY(in);

        if (isValid(in)) {
            return;
        }

        setFPointAtZ(in.setZ(memoZ));

        if (isValid(in)) {
            return;
        }

        invalidate(in);
    }

    private void setCandidate3DXZ(FPoint in) {
        double memoZ = in.getZ();

        setFPointAtX(in);

        if (isValid(in)) {
            return;
        }

        setFPointAtZ(in.setZ(memoZ));

        if (isValid(in)) {
            return;
        }

        invalidate(in);
    }

    private Optional<FPoint> parseCandidate(FLine arg, FPoint argCandidate) {

        if (!isValid(argCandidate)) {
            return Optional.empty();
        }

        if (isPartOf(argCandidate) && arg.isPartOf(argCandidate)) {
            return Optional.of(argCandidate);
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------------------------------------------

    private void projectUnit(FPoint in) {

        getRefOrigin().applyWithFixedState(o -> {
            FPoint oBase = o.getRefBase();
            FPoint oHead = o.getRefHead();

            double oMagnitude = o.getMagnitude();

            in.sub(oBase);

            oHead.sub(oBase);
            oHead.div(oMagnitude);

            oHead.mul(in.getDotProduct(oHead));
            oBase.add(oHead);

            in.applyStateFrom(oBase);
        });
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isValid(FPoint arg) {

        return !Double.isNaN(arg.getX()) && !Double.isNaN(arg.getY()) && !Double.isNaN(arg.getZ());
    }

    private void invalidate(FPoint in) {

        in.set(Double.NaN, Double.NaN, Double.NaN);
    }
}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html