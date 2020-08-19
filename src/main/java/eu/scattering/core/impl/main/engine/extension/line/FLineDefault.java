package eu.scattering.core.impl.main.engine.extension.line;

import eu.scattering.core.factory.MainFactory;
import eu.scattering.core.logic.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.logic.main.engine.base.point.FPoint;
import eu.scattering.core.logic.main.engine.base.vector.FVector;
import eu.scattering.core.impl.main.engine.extension.ExtensionPreset;
import eu.scattering.core.logic.main.engine.extension.line.FLine;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static eu.scattering.core.Config.jitter;

public class FLineDefault extends ExtensionPreset<FLine> implements FLine {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    FVector origin;

    private FLineDefault() { }

    public static FLine create() {

        return new FLineDefault().setOriginRef(MainFactory.getIFVector());
    }

    @Override
    public FVector getOrigin() {

        return origin;
    }

    @Override
    public FLine setOriginRef(FVector fVector) {

        if (fVector == null) {
            throw new NullPointerException("The reference IFVector cannot be null");
        }

        origin = fVector;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();
        json.append("line", getOrigin().exportToJSON());

        return json;
    }

    @Override
    public FLine importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("line");

        getOrigin().set(MainFactory.getIFVector().importFromJSON(structure.getJSONObject(0)));

        return this;
    }

    @Override
    public FLine copy() {

        return MainFactory.getIFLine(getOrigin().copy());
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
    public boolean equals(Object object) {

        if (object instanceof FLine) {
            return isExact((FLine) object);
        }

        return false;
    }

    @Override
    public Object clone() {

        return copy();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Consumer<BaseExtensionAssembly> project() {

        return (e) -> e.disassemble()
                .forEach(this::projectIFPoint);
    }

    @Override
    public Consumer<BaseExtensionAssembly> reflect() {

        return (e) -> e.disassemble()
                .forEach(p -> p.reflect(projectIFPoint(p.copy())));
    }

    @Override
    public Function<BaseExtensionAssembly, List<Boolean>> isPartOf() {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectIFPoint(p.copy())) < jitter)
                .collect(Collectors.toList());
    }

    @Override
    public Function<BaseExtensionAssembly, List<Double>> getDistance() {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectIFPoint(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public Consumer<BaseExtensionAssembly> setDistance(double distance) {

        return (e) -> e.disassemble()
                .forEach(p -> p.setDistance(projectIFPoint(p.copy()), distance));
    }

    @Override
    public Consumer<BaseExtensionAssembly> moveForward(double distance) {

        return (e) -> e.disassemble().forEach(p -> moveForward(p, distance));
    }

    @Override
    public Consumer<BaseExtensionAssembly> moveBackward(double distance) {

        return (e) -> e.disassemble().forEach(p -> moveBackward(p, distance));
    }

    @Override
    public Function<BaseExtensionAssembly, List<Boolean>> isPartOfRay() {

        return (e) -> e.disassemble().stream()
                .map(this::isPartOfRay)
                .collect(Collectors.toList());
    }

    @Override
    public Function<BaseExtensionAssembly, List<Boolean>> isPartOfSegment() {

        return (e) -> e.disassemble().stream()
                .map(this::isPartOfSegment)
                .collect(Collectors.toList());
    }

    @Override
    public FPoint getIFPoint(double length) {
        FPoint fPoint = getOrigin().getHead().copy().sub(getOrigin().getBase());
        double tmp = length / getOrigin().getLength();

        fPoint.setX(getOrigin().getBase().getX() + (fPoint.getX() * tmp));
        fPoint.setY(getOrigin().getBase().getY() + (fPoint.getY() * tmp));
        fPoint.setZ(getOrigin().getBase().getZ() + (fPoint.getZ() * tmp));

        return fPoint;
    }

    @Override
    public Optional<FPoint> getIFPointAtX(double x) {

        if (getOrigin().getBase().getX() == getOrigin().getHead().getX()) {
            return Optional.empty();
        }

        double l = getOrigin().getHead().getX() - getOrigin().getBase().getX();
        double m = getOrigin().getHead().getY() - getOrigin().getBase().getY();
        double n = getOrigin().getHead().getZ() - getOrigin().getBase().getZ();

        double y = getOrigin().getBase().getY() + (m / l * (x - getOrigin().getBase().getX()));
        double z = getOrigin().getBase().getZ() + (n / l * (x - getOrigin().getBase().getX()));

        return Optional.of(MainFactory.getIFPoint(x, y, z));
    }

    @Override
    public Optional<FPoint> getIFPointAtY(double y) {

        if (getOrigin().getBase().getY() == getOrigin().getHead().getY()) {
            return Optional.empty();
        }

        double l = getOrigin().getHead().getX() - getOrigin().getBase().getX();
        double m = getOrigin().getHead().getY() - getOrigin().getBase().getY();
        double n = getOrigin().getHead().getZ() - getOrigin().getBase().getZ();

        double x = getOrigin().getBase().getX() + (l / m * (y - getOrigin().getBase().getY()));
        double z = getOrigin().getBase().getZ() + (n / m * (y - getOrigin().getBase().getY()));

        return Optional.of(MainFactory.getIFPoint(x, y, z));
    }

    @Override
    public Optional<FPoint> getIFPointAtZ(double z) {

        if (getOrigin().getBase().getZ() == getOrigin().getHead().getZ()) {
            return Optional.empty();
        }

        double l = getOrigin().getHead().getX() - getOrigin().getBase().getX();
        double m = getOrigin().getHead().getY() - getOrigin().getBase().getY();
        double n = getOrigin().getHead().getZ() - getOrigin().getBase().getZ();

        double x = getOrigin().getBase().getX() + (l / n * (z - getOrigin().getBase().getZ()));
        double y = getOrigin().getBase().getY() + (m / n * (z - getOrigin().getBase().getZ()));

        return Optional.of(MainFactory.getIFPoint(x, y, z));
    }

    @Override
    public Optional<FPoint> getCommonIFPoint(FLine ref) {

        if (getOrigin().isParallel(ref.getOrigin())) {
            return Optional.empty();
        }

        String dir = getProjectionType(ref.getOrigin());

        FVector u = projectOnPlane(dir, getOrigin().copy());
        FVector v = projectOnPlane(dir, ref.getOrigin().copy());
        FVector w = MainFactory.getIFVector(v.getBase(), u.getBase());

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
                ref.getBase().setZ(0);
                ref.getHead().setZ(0);

                return ref;
            case "YZ":
                ref.getBase().setX(0);
                ref.getHead().setX(0);

                return ref;
            case "XZ":
                ref.getBase().setY(0);
                ref.getHead().setY(0);

                return ref;
        }

        throw new IllegalStateException("The IFVector cannot be projected on any plane. Value " + dir);
    }

    private FVector getCrossProduct(String dir, FVector ref) {

        switch (dir) {
            case "XY":
                return ref.setCrossProduct(ref.getBase().copy().setZ(1));
            case "YZ":
                return ref.setCrossProduct(ref.getBase().copy().setX(1));
            case "XZ":
                return ref.setCrossProduct(ref.getBase().copy().setY(1));
        }

        throw new IllegalStateException("The cross product cannot be calculated. Value " + dir);
    }

    private FPoint getCandidate2D(FVector ref, double scaleFactor) {

        return ref.copy().mul(scaleFactor).moveBase(ref.getBase()).getHead();
    }

    private Optional<FPoint> getCandidate3D(String dir, FPoint ref) {

        Optional<FPoint> candidate;

        switch (dir) {
            case "XY":
                candidate = getIFPointAtX(ref.getX());

                if (candidate.isPresent()) {
                    return candidate;
                }

                candidate = getIFPointAtY(ref.getY());

                if (candidate.isPresent()) {
                    return candidate;
                }

                throw new IllegalStateException("The IFPoint candidate cannot be calculated. Value " + dir);
            case "YZ":
                candidate = getIFPointAtY(ref.getY());

                if (candidate.isPresent()) {
                    return candidate;
                }

                candidate = getIFPointAtZ(ref.getZ());

                if (candidate.isPresent()) {
                    return candidate;
                }

                throw new IllegalStateException("The IFPoint candidate cannot be calculated. Value " + dir);
            case "XZ":
                candidate = getIFPointAtX(ref.getX());

                if (candidate.isPresent()) {
                    return candidate;
                }

                candidate = getIFPointAtZ(ref.getZ());

                if (candidate.isPresent()) {
                    return candidate;
                }

                throw new IllegalStateException("The IFPoint candidate cannot be calculated. Value " + dir);
        }

        throw new IllegalStateException("The IFPoint candidate cannot be calculated. Value " + dir);
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

    private FPoint projectIFPoint(FPoint fPoint) {
        FPoint opA = MainFactory.getIFPoint(getOrigin().getHead())
                .sub(getOrigin().getBase())
                .div(getOrigin().getLength());

        FPoint opB = MainFactory.getIFPoint(fPoint)
                .sub(getOrigin().getBase());

        fPoint.set(origin.getBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        return fPoint;
    }

    private boolean isPartOfRay(FPoint projection) {
        double magnitude = getOrigin().getLength();

        double distanceBase = getOrigin().getBase().getDistance(projection);
        double distanceHead = getOrigin().getHead().getDistance(projection);

        if ((distanceBase < magnitude + jitter) && (distanceHead < magnitude + jitter)) {
            return true;
        }

        return distanceHead < distanceBase + jitter;
    }

    private boolean isPartOfSegment(FPoint projection) {
        double magnitude = getOrigin().getLength();

        double distanceBase = getOrigin().getBase().getDistance(projection);
        double distanceHead = getOrigin().getHead().getDistance(projection);

        return (distanceBase < magnitude + jitter) && (distanceHead < magnitude + jitter);
    }

    private FPoint moveForward(FPoint ref, double distance) {

        return ref.set(getOrigin().copy().moveBase(ref).moveForward(distance).getBase());
    }

    private FPoint moveBackward(FPoint ref, double distance) {

        return ref.set(getOrigin().copy().moveBase(ref).moveBackward(distance).getBase());
    }

}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html