package eu.scattering.core.main.engine.extension.plane.impl;

import eu.scattering.core.main.MainFactory;
import eu.scattering.core.main.engine.base.IBaseExtensionAssembly;
import eu.scattering.core.main.engine.base.point.IFPoint;
import eu.scattering.core.main.engine.base.vector.IFVector;
import eu.scattering.core.main.engine.extension.ExtensionPreset;
import eu.scattering.core.main.engine.extension.line.IFLine;
import eu.scattering.core.main.engine.extension.plane.IFPlane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static eu.scattering.core.Config.jitter;

public class FPlane extends ExtensionPreset<IFPlane> implements IFPlane {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    IFVector origin;

    private FPlane() { }

    public static IFPlane create() {

        return new FPlane().setOriginRef(MainFactory.getIFVector());
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

        getOrigin().set(MainFactory.getIFVector().importFromJSON(structure.getJSONObject(0)));

        return this;
    }

    @Override
    public IFPlane copy() {

        return MainFactory.getIFPlane(getOrigin().copy());
    }

    @Override
    public IFPlane self() {

        return this;
    }

    @Override
    public boolean isSimilar(IFPlane ref) {

        return (getOrigin().isParallel(ref.getOrigin()) || getOrigin().isAntiParallel(ref.getOrigin()))
                && ref.getOrigin().extBoolean(isPartOf()).get(0);
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

        return (e) -> e.disassemble()
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
    public boolean isCut(IBaseExtensionAssembly assembly) {

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

        if (getOrigin().isParallel(ref.getOrigin()) || getOrigin().isAntiParallel(ref.getOrigin())) {
            return Optional.empty();
        }

        IFPoint vPlane1 = getOrigin().copy().moveBase().getHead();
        double d1 = -vPlane1.getDotProduct(getBase());

        IFPoint vPlane2 = ref.getOrigin().copy().moveBase().getHead();
        double d2 = -vPlane2.getDotProduct(ref.getBase());

        IFPoint vPlanePar = vPlane1.copy().setCrossProduct(vPlane2);
        double vPlaneParDot = vPlanePar.getDotProduct(vPlanePar);

        IFPoint pos = vPlane1.mul(d2).sub(vPlane2.mul(d1)).setCrossProduct(vPlanePar).div(vPlaneParDot);

        return Optional.of(MainFactory.getIFLine(MainFactory.getIFVector(vPlanePar).moveBase(pos)));
    }

    // -------------------------------------------------------------------------------------------------

    private IFPoint projectOnPlane(IFPoint fPoint) {
        IFPoint opA = MainFactory.getIFPoint(getOrigin().getHead())
                .sub(getOrigin().getBase())
                .div(getOrigin().getLength());

        IFPoint opB = MainFactory.getIFPoint(fPoint)
                .sub(getOrigin().getBase());

        IFPoint opC = MainFactory.getIFPoint()
                .set(getOrigin().getBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        IFVector translation = MainFactory.getIFVector(opC, fPoint.copy())
                .moveBase(getOrigin().getBase());

        return fPoint.set(translation.getHead());
    }

    private IFPoint projectOnLine(IFPoint fPoint) {
        IFPoint opA = MainFactory.getIFPoint(getOrigin().getHead())
                .sub(getOrigin().getBase())
                .div(getOrigin().getLength());

        IFPoint opB = MainFactory.getIFPoint(fPoint)
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
// https://opentextbc.ca/calculusv3openstax/chapter/equations-of-lines-and-planes-in-space/