package eu.scattering.core.geometry.support.line.impl;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.PresetSupport;
import eu.scattering.core.geometry.support.line.IFLine;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static eu.scattering.core.Configuration.jitter;

public class FLine extends PresetSupport<IFLine> implements IFLine {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    IFVector origin;

    private FLine() { }

    public static IFLine create() {

        return new FLine().setOriginRef(FactoryGeometry.getIFVector());
    }

    @Override
    public IFVector getOrigin() {

        return origin;
    }

    @Override
    public IFLine setOriginRef(IFVector fVector) {

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
    public IFLine importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("line");

        getOrigin().set(FactoryGeometry.getIFVector().importFromJSON(structure.getJSONObject(0)));

        return this;
    }

    @Override
    public IFLine copy() {

        return FactoryGeometry.getIFLine(getOrigin().copy());
    }

    @Override
    public IFLine self() {

        return this;
    }

    @Override
    public boolean isSimilar(IFLine ref) {

        return getOrigin().extLog(ref.isPartOf()).stream().allMatch(e -> e);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object object) {

        if (object instanceof IFLine) {
            return isExact((IFLine) object);
        }

        return false;
    }

    @Override
    public Object clone() {

        return copy();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Consumer<IBaseExtensionAssembly> project() {

        return (e) -> e.disassemble()
                .forEach(this::projectIFPoint);
    }

    @Override
    public Consumer<IBaseExtensionAssembly> reflect() {

        return (e) -> e.disassemble()
                .forEach(p -> p.reflect(projectIFPoint(p.copy())));
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Boolean>> isPartOf() {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectIFPoint(p.copy())) < jitter)
                .collect(Collectors.toList());
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Double>> getDistance() {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectIFPoint(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public Consumer<IBaseExtensionAssembly> setDistance(double distance) {

        return (e) -> e.disassemble()
                .forEach(p -> p.setDistance(projectIFPoint(p.copy()), distance));
    }

    @Override
    public Consumer<IBaseExtensionAssembly> moveForward(double distance) {

        return (e) -> e.disassemble().forEach(p -> moveForward(p, distance));
    }

    @Override
    public Consumer<IBaseExtensionAssembly> moveBackward(double distance) {

        return (e) -> e.disassemble().forEach(p -> moveBackward(p, distance));
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Boolean>> isPartOfRay() {

        return (e) -> e.disassemble().stream()
                .map(this::isPartOfRay)
                .collect(Collectors.toList());
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Boolean>> isPartOfSegment() {

        return (e) -> e.disassemble().stream()
                .map(this::isPartOfSegment)
                .collect(Collectors.toList());
    }

    @Override
    public IFPoint getIFPoint(double length) {
        IFPoint fPoint = getOrigin().getHead().copy().sub(getOrigin().getBase());
        double tmp = length / getOrigin().getMagnitude();

        fPoint.setX(getOrigin().getBase().getX() + (fPoint.getX() * tmp));
        fPoint.setY(getOrigin().getBase().getY() + (fPoint.getY() * tmp));
        fPoint.setZ(getOrigin().getBase().getZ() + (fPoint.getZ() * tmp));

        return fPoint;
    }

    @Override
    public Optional<IFPoint> getIFPointAtX(double x) {

        if (getOrigin().getBase().getX() == getOrigin().getHead().getX()) {
            return Optional.empty();
        }

        double l = getOrigin().getHead().getX() - getOrigin().getBase().getX();
        double m = getOrigin().getHead().getY() - getOrigin().getBase().getY();
        double n = getOrigin().getHead().getZ() - getOrigin().getBase().getZ();

        double y = getOrigin().getBase().getY() + (m / l * (x - getOrigin().getBase().getX()));
        double z = getOrigin().getBase().getZ() + (n / l * (x - getOrigin().getBase().getX()));

        return Optional.of(FactoryGeometry.getIFPoint(x, y, z));
    }

    @Override
    public Optional<IFPoint> getIFPointAtY(double y) {

        if (getOrigin().getBase().getY() == getOrigin().getHead().getY()) {
            return Optional.empty();
        }

        double l = getOrigin().getHead().getX() - getOrigin().getBase().getX();
        double m = getOrigin().getHead().getY() - getOrigin().getBase().getY();
        double n = getOrigin().getHead().getZ() - getOrigin().getBase().getZ();

        double x = getOrigin().getBase().getX() + (l / m * (y - getOrigin().getBase().getY()));
        double z = getOrigin().getBase().getZ() + (n / m * (y - getOrigin().getBase().getY()));

        return Optional.of(FactoryGeometry.getIFPoint(x, y, z));
    }

    @Override
    public Optional<IFPoint> getIFPointAtZ(double z) {

        if (getOrigin().getBase().getZ() == getOrigin().getHead().getZ()) {
            return Optional.empty();
        }

        double l = getOrigin().getHead().getX() - getOrigin().getBase().getX();
        double m = getOrigin().getHead().getY() - getOrigin().getBase().getY();
        double n = getOrigin().getHead().getZ() - getOrigin().getBase().getZ();

        double x = getOrigin().getBase().getX() + (l / n * (z - getOrigin().getBase().getZ()));
        double y = getOrigin().getBase().getY() + (m / n * (z - getOrigin().getBase().getZ()));

        return Optional.of(FactoryGeometry.getIFPoint(x, y, z));
    }

    @Override
    public Optional<IFPoint> getCommonIFPoint(IFLine ref) {

        if (getOrigin().isParallel(ref.getOrigin())) {
            return Optional.empty();
        }

        String dir = getProjectionType(ref.getOrigin());

        IFVector u = projectOnPlane(dir, getOrigin().copy());
        IFVector v = projectOnPlane(dir, ref.getOrigin().copy());
        IFVector w = FactoryGeometry.getIFVector(v.getBase(), u.getBase());

        v = getCrossProduct(dir, v);

        double scaleFactor = v.copy().reflectHead().getDotProduct(w) / v.getDotProduct(u);

        return validateCandidate(ref, getCandidate3D(dir, getCandidate2D(u, scaleFactor)));
    }

    // -------------------------------------------------------------------------------------------------

    private String getProjectionType(IFVector ref) {

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

    private IFVector projectOnPlane(String dir, IFVector ref) {

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

    private IFVector getCrossProduct(String dir, IFVector ref) {

        switch (dir) {
            case "XY":
                return ref.getCrossProduct(ref.getBase().copy().setZ(1));
            case "YZ":
                return ref.getCrossProduct(ref.getBase().copy().setX(1));
            case "XZ":
                return ref.getCrossProduct(ref.getBase().copy().setY(1));
        }

        throw new IllegalStateException("The cross product cannot be calculated. Value " + dir);
    }

    private IFPoint getCandidate2D(IFVector ref, double scaleFactor) {

        return ref.copy().mul(scaleFactor).relocateBase(ref.getBase()).getHead();
    }

    private Optional<IFPoint> getCandidate3D(String dir, IFPoint ref) {

        Optional<IFPoint> candidate;

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

    private Optional<IFPoint> validateCandidate(IFLine ref, Optional<IFPoint> candidate) {

        if (candidate.isEmpty()) {
            return Optional.empty();
        }

        if (candidate.get().extLog(isPartOf()).get(0) && candidate.get().extLog(ref.isPartOf()).get(0)) {
            return candidate;
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------------------------------------------

    private IFPoint projectIFPoint(IFPoint fPoint) {
        IFPoint opA = FactoryGeometry.getIFPoint(getOrigin().getHead())
                .sub(getOrigin().getBase())
                .div(getOrigin().getMagnitude());

        IFPoint opB = FactoryGeometry.getIFPoint(fPoint)
                .sub(getOrigin().getBase());

        fPoint.set(origin.getBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        return fPoint;
    }

    private boolean isPartOfRay(IFPoint projection) {
        double magnitude = getOrigin().getMagnitude();

        double distanceBase = getOrigin().getBase().getDistance(projection);
        double distanceHead = getOrigin().getHead().getDistance(projection);

        if ((distanceBase < magnitude + jitter) && (distanceHead < magnitude + jitter)) {
            return true;
        }

        return distanceHead < distanceBase + jitter;
    }

    private boolean isPartOfSegment(IFPoint projection) {
        double magnitude = getOrigin().getMagnitude();

        double distanceBase = getOrigin().getBase().getDistance(projection);
        double distanceHead = getOrigin().getHead().getDistance(projection);

        return (distanceBase < magnitude + jitter) && (distanceHead < magnitude + jitter);
    }

    private IFPoint moveForward(IFPoint ref, double distance) {

        return ref.set(getOrigin().copy().relocateBase(ref).moveForward(distance).getBase());
    }

    private IFPoint moveBackward(IFPoint ref, double distance) {

        return ref.set(getOrigin().copy().relocateBase(ref).moveBackward(distance).getBase());
    }

}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
// http://sites.science.oregonstate.edu/math/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html