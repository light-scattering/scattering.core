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

        return (e) -> e.disassemble().stream()
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
    public boolean isProjectableOnRay(IBaseExtensionAssembly ref) {

        return ref.disassemble().stream()
                .map(p -> validateProjectionOnRay(projectIFPoint(p.copy())))
                .allMatch(e -> e);
    }

    @Override
    public boolean isProjectableOnSegment(IBaseExtensionAssembly ref) {

        return ref.disassemble().stream()
                .map(p -> validateProjectionOnSegment(projectIFPoint(p.copy())))
                .allMatch(e -> e);
    }

    @Override
    public Optional<IFPoint> getIntersectingIFPoint() {
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

    private boolean validateProjectionOnRay(IFPoint projection) {
        double magnitude = getOrigin().getMagnitude();

        double distanceBase = getOrigin().getBase().getDistance(projection);
        double distanceHead = getOrigin().getHead().getDistance(projection);

        if ((distanceBase < magnitude + jitter) && (distanceHead < magnitude + jitter)) {
            return true;
        }

        return distanceHead < distanceBase + jitter;
    }

    private boolean validateProjectionOnSegment(IFPoint projection) {
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