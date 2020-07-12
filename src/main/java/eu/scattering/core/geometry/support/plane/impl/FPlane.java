package eu.scattering.core.geometry.support.plane.impl;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.PresetSupport;
import eu.scattering.core.geometry.support.line.IFLine;
import eu.scattering.core.geometry.support.plane.IFPlane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static eu.scattering.core.Configuration.jitter;

public class FPlane extends PresetSupport<IFPlane> implements IFPlane {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    IFVector origin;

    private FPlane() { }

    public static IFPlane create() {

        return new FPlane().setOriginRef(FactoryGeometry.getIFVector());
    }

    @Override
    public IFVector getOrigin() {

        return origin;
    }

    @Override
    public IFPlane setOriginRef(IFVector fVector) {

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
        json.append("plane", getOrigin().exportToJSON());

        return json;
    }

    @Override
    public IFPlane importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("plane");

        getOrigin().set(FactoryGeometry.getIFVector().importFromJSON(structure.getJSONObject(0)));

        return this;
    }

    @Override
    public IFPlane copy() {

        return FactoryGeometry.getIFPlane(getOrigin().copy());
    }

    @Override
    public IFPlane self() {

        return this;
    }

    @Override
    public boolean isSimilar(IFPlane ref) {

        return getOrigin().isParallel(ref.getOrigin()) && ref.getOrigin().extLog(isPartOf()).get(0);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object object) {

        if (object instanceof IFPlane) {
            return isExact((IFPlane) object);
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
                .forEach(this::projectOnPlane);
    }

    @Override
    public Consumer<IBaseExtensionAssembly> reflect() {

        return (e) -> e.disassemble()
                .forEach(p -> p.reflect(projectOnPlane(p.copy())));
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Boolean>> isPartOf() {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())) < jitter)
                .collect(Collectors.toList());
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Double>> getDistance() {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public Consumer<IBaseExtensionAssembly> setDistance(double distance) {

        return (e) -> e.disassemble().stream()
                .forEach(p -> p.setDistance(projectOnPlane(p.copy()), distance));
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Boolean>> isInHalfSpace() {

        return (e) -> e.disassemble().stream()
                .map(p -> isInHalfSpace(projectOnLine(p.copy())))
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isIntersecting(IBaseExtensionAssembly assembly) {

        List<Boolean> isInHalfSpace = assembly.disassemble().stream()
                .map(p -> isInHalfSpace(projectOnLine(p.copy())))
                .collect(Collectors.toList());

        boolean conditionTrue = isInHalfSpace.stream().anyMatch(e -> e);
        boolean conditionFalse = isInHalfSpace.stream().anyMatch(e -> !e);

        return conditionTrue && conditionFalse;
    }

    @Override
    public Optional<IFPoint> getCommonIFPoint(IFLine ref) {

        if (getOrigin().isOrthogonal(ref.getOrigin())) {
            return Optional.empty();
        }

        IFPoint vPlane = getOrigin().copy().moveBase().normalize().getHead();
        IFPoint vLine = ref.getOrigin().copy().moveBase().normalize().getHead();

        double dividend = vPlane.getDotProduct(getOrigin().getBase().copy().sub(ref.getOrigin().getBase()));
        double divisor = vPlane.getDotProduct(vLine);
        double distance = dividend / divisor;

        IFVector extension = ref.getOrigin().copy().setLength(distance);

        return Optional.of(extension.getHead());
    }

    @Override
    public Optional<IFLine> getCommonIFLine(IFPlane ref) {
        return null;
    }

    // -------------------------------------------------------------------------------------------------

    private IFPoint projectOnPlane(IFPoint fPoint) {
        IFPoint opA = FactoryGeometry.getIFPoint(getOrigin().getHead())
                .sub(getOrigin().getBase())
                .div(getOrigin().getLength());

        IFPoint opB = FactoryGeometry.getIFPoint(fPoint)
                .sub(getOrigin().getBase());

        IFPoint opC = FactoryGeometry.getIFPoint()
                .set(getOrigin().getBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        IFVector translation = FactoryGeometry.getIFVector(opC, fPoint.copy())
                .moveBase(getOrigin().getBase());

        return fPoint.set(translation.getHead());
    }

    private IFPoint projectOnLine(IFPoint fPoint) {
        IFPoint opA = FactoryGeometry.getIFPoint(getOrigin().getHead())
                .sub(getOrigin().getBase())
                .div(getOrigin().getLength());

        IFPoint opB = FactoryGeometry.getIFPoint(fPoint)
                .sub(getOrigin().getBase());

        return fPoint.set(getOrigin().getBase().copy().add(opA.mul(opB.getDotProduct(opA))));
    }

    private boolean isInHalfSpace(IFPoint projection) {
        double magnitude = getOrigin().getLength();

        double distanceBase = getOrigin().getBase().getDistance(projection);
        double distanceHead = getOrigin().getHead().getDistance(projection);

        if ((distanceBase < magnitude + jitter) && (distanceHead < magnitude + jitter)) {
            return true;
        }

        return distanceHead < distanceBase + jitter;
    }

}

// http://geomalgorithms.com/a05-_intersect-1.html
