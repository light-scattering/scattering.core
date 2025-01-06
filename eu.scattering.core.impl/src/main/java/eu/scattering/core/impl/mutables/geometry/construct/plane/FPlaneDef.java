package eu.scattering.core.impl.mutables.geometry.construct.plane;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlane;
import eu.scattering.core.impl.mutables.geometry.construct.AdvancedPresetDef;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
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

        return new FPlaneDef(factory, epsilon).setRefOrigin(factory.getFVector());
    }

    @Override
    public FVector getRefOrigin() {

        return origin;
    }

    @Override
    public FPlane setRefOrigin(FVector refOrigin) {

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
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.append("plane", getRefOrigin().toJSON());

        return json;
    }

    @Override
    public FPlane applyStateFrom(JSONObject json) {
        JSONArray structure = json.getJSONArray("plane");

        getRefOrigin().applyStateFrom(factory.getFVector().applyStateFrom(structure.getJSONObject(0)));

        return this;
    }

    @Override
    public FPlane copy() {

        return factory.getFPlane(getRefOrigin().copy());
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

        return (getRefOrigin().isParallel(ref.getRefOrigin()) || getRefOrigin().isAntiParallel(ref.getRefOrigin()))
                && isPartOf(ref.getRefOrigin()).get(0);
    }

    // -------------------------------------------------------------------------------------------------


    @Override
    public void project(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(this::projectOnPlane);
    }

    @Override
    public void reflect(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(p -> p.reflect(projectOnPlane(p.copy())));
    }

    @Override
    public List<Boolean> isPartOf(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())) < epsilon)
                .collect(Collectors.toList());
    }

    @Override
    public List<Double> getDistance(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Double> getDistanceP2(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> p.getDistanceP2(projectOnPlane(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public void setDistance(Geometry geometry, double distance) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        geometry.disassemble().forEach(p -> p.setDistance(projectOnPlane(p.copy()), distance));
    }

    @Override
    public List<Boolean> isInHalfSpace(Geometry geometry) {

        if (getRefOrigin().isNonDirectional()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> isInHalfSpace(projectOnLine(p.copy())))
                .collect(Collectors.toList());
    }


    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isCut(Geometry assembly) {

        if (getRefOrigin().isNonDirectional()) {
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

        if (getRefOrigin().isOrthogonal(ref.getRefOrigin())) {
            return Optional.empty();
        }

        FPoint vPlane = getRefOrigin().copy().moveBaseToCenter().normalize().getRefHead();
        FPoint vLine = ref.getRefOrigin().copy().moveBaseToCenter().normalize().getRefHead();

        double dividend = vPlane.getDotProduct(getRefOrigin().getRefBase().copy().sub(ref.getRefOrigin().getRefBase()));
        double divisor = vPlane.getDotProduct(vLine);
        double distance = dividend / divisor;

        FVector extension = ref.getRefOrigin().copy().setLength(distance);

        return Optional.of(extension.getRefHead());
    }

    @Override
    public Optional<FLine> getCommonFLine(FPlane ref) {

        if (getRefOrigin().isParallel(ref.getRefOrigin()) || getRefOrigin().isAntiParallel(ref.getRefOrigin())) {
            return Optional.empty();
        }

        FPoint vPlane1 = getRefOrigin().copy().moveBaseToCenter().getRefHead();
        double d1 = -vPlane1.getDotProduct(getRefOrigin().getRefBase());

        FPoint vPlane2 = ref.getRefOrigin().copy().moveBaseToCenter().getRefHead();
        double d2 = -vPlane2.getDotProduct(ref.getRefOrigin().getRefBase());

        FPoint vPlanePar = vPlane1.copy().setCrossProduct(vPlane2);
        double vPlaneParDot = vPlanePar.getDotProduct(vPlanePar);

        FPoint pos = vPlane1.mul(d2).sub(vPlane2.mul(d1)).setCrossProduct(vPlanePar).div(vPlaneParDot);

        return Optional.of(factory.getRefFLine(factory.getFVector(vPlanePar).moveBase(pos)));
    }



    // -------------------------------------------------------------------------------------------------

    private FPoint projectOnPlane(FPoint fPoint) {
        FPoint opA = getRefOrigin().getRefHead().copy()
                .sub(getRefOrigin().getRefBase())
                .div(getRefOrigin().getLength());

        FPoint opB = fPoint.copy()
                .sub(getRefOrigin().getRefBase());

        FPoint opC = factory.getFPoint()
                .applyStateFrom(getRefOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));

        FVector translation = factory.getFVector(opC, fPoint.copy())
                .moveBase(getRefOrigin().getRefBase());

        return fPoint.applyStateFrom(translation.getRefHead());
    }

    private FPoint projectOnLine(FPoint fPoint) {
        FPoint opA = getRefOrigin().getRefHead().copy()
                .sub(getRefOrigin().getRefBase())
                .div(getRefOrigin().getLength());

        FPoint opB = fPoint.copy()
                .sub(getRefOrigin().getRefBase());

        return fPoint.applyStateFrom(getRefOrigin().getRefBase().copy().add(opA.mul(opB.getDotProduct(opA))));
    }

    private boolean isInHalfSpace(FPoint projection) {
        double magnitude = getRefOrigin().getLength();

        double distanceBase = getRefOrigin().getRefBase().getDistance(projection);
        double distanceHead = getRefOrigin().getRefHead().getDistance(projection);

        if ((distanceBase < magnitude + epsilon) && (distanceHead < magnitude + epsilon)) {
            return true;
        }

        return distanceHead < distanceBase + epsilon;
    }
}

// http://geomalgorithms.com/a05-_intersect-1.html
// https://opentextbc.ca/calculusv3openstax/chapter/equations-of-lines-and-planes-in-space/