package eu.scattering.core.impl.production.core.mutable.geometry.advanced.plane;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.elements.algebra.geometry.Geometry;
import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.elements.algebra.geometry.construct.line.FLine;
import eu.scattering.core.design.elements.algebra.geometry.construct.plane.FPlane;
import eu.scattering.core.impl.production.core.mutable.geometry.advanced.AdvancedPresetProd;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FPlaneProd extends AdvancedPresetProd<FPlane> implements FPlane {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FVector origin;
    private final Factory factory;

    private FPlaneProd(Factory factory) {

        this.factory = factory;
    }

    public static FPlane create(Factory factory) {

        return new FPlaneProd(factory).setOriginRef(factory.getFVector());
    }

    @Override
    public FVector getOrigin() {

        return origin;
    }

    @Override
    public FPlane setOriginRef(FVector fVector) {

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
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();
        json.append("plane", getOrigin().exportToJSON());

        return json;
    }

    @Override
    public FPlane importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("plane");

        getOrigin().set(factory.getFVector().importFromJSON(structure.getJSONObject(0)));

        return this;
    }

    @Override
    public FPlane copy() {

        return factory.getFPlane(getOrigin().copy());
    }

    @Override
    public FPlane self() {

        return this;
    }

    @Override
    public boolean isSimilar(FPlane ref) {

        return (getOrigin().isParallel(ref.getOrigin()) || getOrigin().isAntiParallel(ref.getOrigin()))
                && ref.getOrigin().extBoolean(isPartOf()).get(0);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Consumer<Geometry> project() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble()
                .forEach(this::projectOnPlane);
    }

    @Override
    public Consumer<Geometry> reflect() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble()
                .forEach(p -> p.reflect(projectOnPlane(p.copy())));
    }

    @Override
    public Function<Geometry, List<Boolean>> isPartOf() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        double jitter = factory.getJitter();

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())) < jitter)
                .collect(Collectors.toList());
    }

    @Override
    public Function<Geometry, List<Double>> getDistance() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public Function<Geometry, List<Double>> getDistanceP2() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistanceP2(projectOnPlane(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public Consumer<Geometry> setDistance(double distance) {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble()
                .forEach(p -> p.setDistance(projectOnPlane(p.copy()), distance));
    }

    @Override
    public Function<Geometry, List<Boolean>> isInHalfSpace() {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return (e) -> e.disassemble().stream()
                .map(p -> isInHalfSpace(projectOnLine(p.copy())))
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isCut(Geometry assembly) {

        if (getOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        List<Boolean> isInHalfSpace = assembly.disassemble().stream()
                .map(p -> isInHalfSpace(projectOnLine(p.copy())))
                .collect(Collectors.toList());

        boolean conditionTrue = isInHalfSpace.stream().anyMatch(e -> e);
        boolean conditionFalse = isInHalfSpace.stream().anyMatch(e -> !e);

        return conditionTrue && conditionFalse;
    }

    @Override
    public Optional<FPoint> getCommonFPoint(FLine ref) {

        if (getOrigin().isOrthogonal(ref.getOrigin())) {
            return Optional.empty();
        }

        FPoint vPlane = getOrigin().copy().moveBase().normalize().getRefHead();
        FPoint vLine = ref.getOrigin().copy().moveBase().normalize().getRefHead();

        double dividend = vPlane.getDotProduct(getOrigin().getRefBase().copy().sub(ref.getOrigin().getRefBase()));
        double divisor = vPlane.getDotProduct(vLine);
        double distance = dividend / divisor;

        FVector extension = ref.getOrigin().copy().setLength(distance);

        return Optional.of(extension.getRefHead());
    }

    @Override
    public Optional<FLine> getCommonFLine(FPlane ref) {

        if (getOrigin().isParallel(ref.getOrigin()) || getOrigin().isAntiParallel(ref.getOrigin())) {
            return Optional.empty();
        }

        FPoint vPlane1 = getOrigin().copy().moveBase().getRefHead();
        double d1 = -vPlane1.getDotProduct(getBase());

        FPoint vPlane2 = ref.getOrigin().copy().moveBase().getRefHead();
        double d2 = -vPlane2.getDotProduct(ref.getBase());

        FPoint vPlanePar = vPlane1.copy().setCrossProduct(vPlane2);
        double vPlaneParDot = vPlanePar.getDotProduct(vPlanePar);

        FPoint pos = vPlane1.mul(d2).sub(vPlane2.mul(d1)).setCrossProduct(vPlanePar).div(vPlaneParDot);

        return Optional.of(factory.getFLine(factory.getFVector(vPlanePar).moveBase(pos)));
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint projectOnPlane(FPoint fPoint) {
        FPoint opA = factory.getFPoint(getOrigin().getRefHead())
                .sub(getOrigin().getRefBase())
                .div(getOrigin().getLength());

        FPoint opB = factory.getFPoint(fPoint)
                .sub(getOrigin().getRefBase());

        FPoint opC = factory.getFPoint()
                .set(getOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        FVector translation = factory.getFVector(opC, fPoint.copy())
                .moveBase(getOrigin().getRefBase());

        return fPoint.set(translation.getRefHead());
    }

    private FPoint projectOnLine(FPoint fPoint) {
        FPoint opA = factory.getFPoint(getOrigin().getRefHead())
                .sub(getOrigin().getRefBase())
                .div(getOrigin().getLength());

        FPoint opB = factory.getFPoint(fPoint)
                .sub(getOrigin().getRefBase());

        return fPoint.set(getOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));
    }

    private boolean isInHalfSpace(FPoint projection) {
        double magnitude = getOrigin().getLength();

        double distanceBase = getOrigin().getRefBase().getDistance(projection);
        double distanceHead = getOrigin().getRefHead().getDistance(projection);

        double jitter = factory.getJitter();

        if ((distanceBase < magnitude + jitter) && (distanceHead < magnitude + jitter)) {
            return true;
        }

        return distanceHead < distanceBase + jitter;
    }

}

// http://geomalgorithms.com/a05-_intersect-1.html
// https://opentextbc.ca/calculusv3openstax/chapter/equations-of-lines-and-planes-in-space/