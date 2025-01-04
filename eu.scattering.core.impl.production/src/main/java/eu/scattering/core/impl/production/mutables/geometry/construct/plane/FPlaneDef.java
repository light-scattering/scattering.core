package eu.scattering.core.impl.production.mutables.algebra.geometry.construct.plane;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlane;
import eu.scattering.core.impl.production.mutables.algebra.geometry.construct.AdvancedPresetDef;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FPlaneDef extends AdvancedPresetDef<FPlane> implements FPlane {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FVector origin;
    private final FactoryDesignConcrete factory;
    private final double epsilon;

    private FPlaneDef(FactoryDesignConcrete factory, double epsilon) {

        this.factory = factory;
        this.epsilon = epsilon;
    }

    public static FPlane create(FactoryDesignConcrete factory, double epsilon) {

        return new FPlaneDef(factory, epsilon).setOriginRef(factory.getFVector());
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
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.append("plane", getOrigin().toJSON());

        return json;
    }

    @Override
    public FPlane applyStateFrom(JSONObject json) {
        JSONArray structure = json.getJSONArray("plane");

        getOrigin().applyStateFrom(factory.getFVector().applyStateFrom(structure.getJSONObject(0)));

        return this;
    }

    @Override
    public FPlane copy() {

        return factory.getFPlane(getOrigin().copy());
    }

    @Override
    public FPlane copyZero() {

        return factory.getFPlane();
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

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())) < epsilon)
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

        FPoint vPlane = getOrigin().copy().moveBaseToCenter().normalize().getRefHead();
        FPoint vLine = ref.getOrigin().copy().moveBaseToCenter().normalize().getRefHead();

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

        FPoint vPlane1 = getOrigin().copy().moveBaseToCenter().getRefHead();
        double d1 = -vPlane1.getDotProduct(getBase());

        FPoint vPlane2 = ref.getOrigin().copy().moveBaseToCenter().getRefHead();
        double d2 = -vPlane2.getDotProduct(ref.getBase());

        FPoint vPlanePar = vPlane1.copy().setCrossProduct(vPlane2);
        double vPlaneParDot = vPlanePar.getDotProduct(vPlanePar);

        FPoint pos = vPlane1.mul(d2).sub(vPlane2.mul(d1)).setCrossProduct(vPlanePar).div(vPlaneParDot);

        return Optional.of(factory.getFLine(factory.getFVector(vPlanePar).moveBase(pos)));
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint projectOnPlane(FPoint fPoint) {
        FPoint opA = getOrigin().getRefHead().copy()
                .sub(getOrigin().getRefBase())
                .div(getOrigin().getLength());

        FPoint opB = fPoint.copy()
                .sub(getOrigin().getRefBase());

        FPoint opC = factory.getFPoint()
                .applyStateFrom(getOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        FVector translation = factory.getFVector(opC, fPoint.copy())
                .moveBase(getOrigin().getRefBase());

        return fPoint.applyStateFrom(translation.getRefHead());
    }

    private FPoint projectOnLine(FPoint fPoint) {
        FPoint opA = getOrigin().getRefHead().copy()
                .sub(getOrigin().getRefBase())
                .div(getOrigin().getLength());

        FPoint opB = fPoint.copy()
                .sub(getOrigin().getRefBase());

        return fPoint.applyStateFrom(getOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));
    }

    private boolean isInHalfSpace(FPoint projection) {
        double magnitude = getOrigin().getLength();

        double distanceBase = getOrigin().getRefBase().getDistance(projection);
        double distanceHead = getOrigin().getRefHead().getDistance(projection);

        if ((distanceBase < magnitude + epsilon) && (distanceHead < magnitude + epsilon)) {
            return true;
        }

        return distanceHead < distanceBase + epsilon;
    }
}

// http://geomalgorithms.com/a05-_intersect-1.html
// https://opentextbc.ca/calculusv3openstax/chapter/equations-of-lines-and-planes-in-space/