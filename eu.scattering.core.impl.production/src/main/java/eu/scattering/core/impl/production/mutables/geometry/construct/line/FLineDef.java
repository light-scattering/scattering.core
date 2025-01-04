package eu.scattering.core.impl.production.mutables.algebra.geometry.construct.line;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.impl.production.mutables.algebra.geometry.construct.AdvancedPresetDef;
import eu.scattering.core.transfer.containers.engine.FRot.FRot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FLineDef extends AdvancedPresetDef<FLine> implements FLine {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FVector origin;
    private final FactoryDesignConcrete factory;
    private final double epsilon;

    private FLineDef(FactoryDesignConcrete factory, double epsilon) {

        this.factory = factory;
        this.epsilon = epsilon;
    }

    public static FLine create(FactoryDesignConcrete factory, double epsilon) {

        return new FLineDef(factory, epsilon).setOriginRef(factory.getFVector());
    }

    @Override
    public FVector getOrigin() {

        return origin;
    }

    @Override
    public FLine setOriginRef(FVector fVector) {

        if (fVector == null) {
            throw new NullPointerException("The reference FVector cannot be null");
        }

        origin = fVector;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.append("line", getOrigin().toJSON());

        return json;
    }

    @Override
    public FLine applyStateFrom(JSONObject json) {
        JSONArray structure = json.getJSONArray("line");

        getOrigin().applyStateFrom(factory.getFVector().applyStateFrom(structure.getJSONObject(0)));

        return this;
    }

    @Override
    public FLine copy() {

        return factory.getFLine(getOrigin().copy());
    }

    @Override
    public FLine copyZero() {

        return factory.getFLine();
    }

    @Override
    public FLine self() {

        return this;
    }

    @Override
    public boolean isSimilar(FLine ref) {

        return getOrigin().extBoolean(ref.isPartOf()).stream().allMatch(e -> e);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Consumer<Geometry> project() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble()
                .forEach(this::projectFPoint);
    }

    @Override
    public Consumer<Geometry> reflect() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble()
                .forEach(p -> p.reflect(projectFPoint(p.copy())));
    }

    @Override
    public Function<Geometry, List<Boolean>> isPartOf() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectFPoint(p.copy())) < epsilon)
                .collect(Collectors.toList());
    }

    @Override
    public Function<Geometry, List<Double>> getDistance() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectFPoint(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public Function<Geometry, List<Double>> getDistanceP2() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistanceP2(projectFPoint(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public Consumer<Geometry> setDistance(double distance) {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble()
                .forEach(p -> p.setDistance(projectFPoint(p.copy()), distance));
    }

    @Override
    public Consumer<Geometry> moveForward(double distance) {

        return (e) -> e.disassemble().forEach(p -> moveForward(p, distance));
    }

    @Override
    public Consumer<Geometry> moveBackward(double distance) {

        return (e) -> e.disassemble().forEach(p -> moveBackward(p, distance));
    }

    @Override
    public Function<Geometry, List<Boolean>> isPartOfRay() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble().stream()
                .map(this::isPartOfRay)
                .collect(Collectors.toList());
    }

    @Override
    public Function<Geometry, List<Boolean>> isPartOfSegment() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble().stream()
                .map(this::isPartOfSegment)
                .collect(Collectors.toList());
    }

    @Override
    public Consumer<Geometry> rotate(double angle) {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var fRot = factory.getFRotationProcessor();
        var fRotHelper = factory.getFRotationEngine();

        FRot rotor = fRot.getRotation(getOrigin().toFPairPos3D(), angle);

        return (e) -> e.disassemble()
                .forEach(p -> fRotHelper.rotate(p, rotor));
    }

    @Override
    public FPoint getFPoint(double length) {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        FPoint fPoint = getOrigin().getRefHead().copy().sub(getOrigin().getRefBase());
        double tmp = length / getOrigin().getLength();

        fPoint.setX(getOrigin().getRefBase().getX() + (fPoint.getX() * tmp));
        fPoint.setY(getOrigin().getRefBase().getY() + (fPoint.getY() * tmp));
        fPoint.setZ(getOrigin().getRefBase().getZ() + (fPoint.getZ() * tmp));

        return fPoint;
    }

    @Override
    public Optional<FPoint> getFPointAtX(double x) {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        if (getOrigin().getRefBase().getX() == getOrigin().getRefHead().getX()) {
            return Optional.empty();
        }

        double l = getOrigin().getRefHead().getX() - getOrigin().getRefBase().getX();
        double m = getOrigin().getRefHead().getY() - getOrigin().getRefBase().getY();
        double n = getOrigin().getRefHead().getZ() - getOrigin().getRefBase().getZ();

        double y = getOrigin().getRefBase().getY() + (m / l * (x - getOrigin().getRefBase().getX()));
        double z = getOrigin().getRefBase().getZ() + (n / l * (x - getOrigin().getRefBase().getX()));

        return Optional.of(factory.getFPoint(x, y, z));
    }

    @Override
    public Optional<FPoint> getFPointAtY(double y) {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        if (getOrigin().getRefBase().getY() == getOrigin().getRefHead().getY()) {
            return Optional.empty();
        }

        double l = getOrigin().getRefHead().getX() - getOrigin().getRefBase().getX();
        double m = getOrigin().getRefHead().getY() - getOrigin().getRefBase().getY();
        double n = getOrigin().getRefHead().getZ() - getOrigin().getRefBase().getZ();

        double x = getOrigin().getRefBase().getX() + (l / m * (y - getOrigin().getRefBase().getY()));
        double z = getOrigin().getRefBase().getZ() + (n / m * (y - getOrigin().getRefBase().getY()));

        return Optional.of(factory.getFPoint(x, y, z));
    }

    @Override
    public Optional<FPoint> getFPointAtZ(double z) {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        if (getOrigin().getRefBase().getZ() == getOrigin().getRefHead().getZ()) {
            return Optional.empty();
        }

        double l = getOrigin().getRefHead().getX() - getOrigin().getRefBase().getX();
        double m = getOrigin().getRefHead().getY() - getOrigin().getRefBase().getY();
        double n = getOrigin().getRefHead().getZ() - getOrigin().getRefBase().getZ();

        double x = getOrigin().getRefBase().getX() + (l / n * (z - getOrigin().getRefBase().getZ()));
        double y = getOrigin().getRefBase().getY() + (m / n * (z - getOrigin().getRefBase().getZ()));

        return Optional.of(factory.getFPoint(x, y, z));
    }

    @Override
    public Optional<FPoint> getCommonFPoint(FLine ref) {

        if (getOrigin().isParallel(ref.getOrigin())) {
            return Optional.empty();
        }

        String dir = getProjectionType(ref.getOrigin());

        FVector u = projectOnPlane(dir, getOrigin().copy());
        FVector v = projectOnPlane(dir, ref.getOrigin().copy());
        FVector w = factory.getFVector(v.getRefBase(), u.getRefBase());

        v = getCrossProduct(dir, v);

        double scaleFactor = v.copy().reflectHead().getDotProduct(w) / v.getDotProduct(u);

        return validateCandidate(ref, getCandidate3D(dir, getCandidate2D(u, scaleFactor)));
    }

    // -------------------------------------------------------------------------------------------------

    private String getProjectionType(FVector ref) {

        if ((getOrigin().getLengthX() > 0 || getOrigin().getLengthY() > 0) &&
                (ref.getLengthX() > 0 || ref.getLengthY() > 0)) {

            return "XY";
        }

        if ((getOrigin().getLengthY() > 0 || getOrigin().getLengthZ() > 0) &&
                (ref.getLengthY() > 0 || ref.getLengthZ() > 0)) {

            return "YZ";
        }

        if ((getOrigin().getLengthX() > 0 || getOrigin().getLengthZ() > 0) &&
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
                return ref.setCrossProduct(factory.getFVector(ref.getRefBase().copy().setZ(1)));
            case "YZ":
                return ref.setCrossProduct(factory.getFVector(ref.getRefBase().copy().setX(1)));
            case "XZ":
                return ref.setCrossProduct(factory.getFVector(ref.getRefBase().copy().setY(1)));
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

    private Optional<FPoint> validateCandidate(FLine ref, Optional<FPoint> candidate) {

        if (candidate.isEmpty()) {
            return Optional.empty();
        }

        if (candidate.get().extBoolean(isPartOf()).get(0) && candidate.get().extBoolean(ref.isPartOf()).get(0)) {
            return candidate;
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint projectFPoint(FPoint fPoint) {
        FPoint opA = getOrigin().getRefHead().copy()
                .sub(getOrigin().getRefBase())
                .div(getOrigin().getLength());

        FPoint opB = fPoint.copy()
                .sub(getOrigin().getRefBase());

        fPoint.applyStateFrom(origin.getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        return fPoint;
    }

    private boolean isPartOfRay(FPoint projection) {
        double magnitude = getOrigin().getLength();

        double distanceBase = getOrigin().getRefBase().getDistance(projection);
        double distanceHead = getOrigin().getRefHead().getDistance(projection);

        if ((distanceBase < magnitude + epsilon) && (distanceHead < magnitude + epsilon)) {
            return true;
        }

        return distanceHead < distanceBase + epsilon;
    }

    private boolean isPartOfSegment(FPoint projection) {
        double magnitude = getOrigin().getLength();

        double distanceBase = getOrigin().getRefBase().getDistance(projection);
        double distanceHead = getOrigin().getRefHead().getDistance(projection);

        return (distanceBase < magnitude + epsilon) && (distanceHead < magnitude + epsilon);
    }

    private FPoint moveForward(FPoint ref, double distance) {

        return ref.applyStateFrom(getOrigin().copy().moveBase(ref).shiftForward(distance).getRefBase());
    }

    private FPoint moveBackward(FPoint ref, double distance) {

        return ref.applyStateFrom(getOrigin().copy().moveBase(ref).shiftBackward(distance).getRefBase());
    }

}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html