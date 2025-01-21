package eu.scattering.core.impl.mutables.geometry.construct.plane;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.construct.plane.FPlane;
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

public class FPlaneDef extends ConstructPresetDef<FPlane> implements FPlane {
    private static final String JSON_MAIN = "plane";
    private static final String JSON_VAL = "val";

    private final Supplier<FLine> fLineSupplier;
    private final Supplier<FVector> fVectorSupplier;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double epsilon;
    private FVector origin;

    private FPlaneDef(double epsilon, Supplier<FLine> fLineSupplier, Supplier<FVector> fOriginSupplier) {

        this.fLineSupplier = fLineSupplier;
        this.fVectorSupplier = fOriginSupplier;

        this.epsilon = epsilon;
        this.origin = fOriginSupplier.get();
    }

    private FPlaneDef(double epsilon, Supplier<FLine> fLineSupplier, Supplier<FVector> fOriginSupplier, FVector origin) {

        this.fLineSupplier = fLineSupplier;
        this.fVectorSupplier = fOriginSupplier;

        this.epsilon = epsilon;
        this.origin = origin;
    }

    public static FPlane create(double epsilon, Supplier<FLine> fLineSupplier, Supplier<FVector> fOriginSupplier) {

        return new FPlaneDef(epsilon, fLineSupplier, fOriginSupplier);
    }

    public static FPlane create(double epsilon, Supplier<FLine> fLineSupplier, Supplier<FVector> fOriginSupplier, FVector origin) {

        return new FPlaneDef(epsilon, fLineSupplier, fOriginSupplier, origin);
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
    public FPlane set(FPairPos3D position) {

        getRefOrigin().set(position);

        return this;
    }

    @Override
    public FPlane applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        var structure = json.getJSONArray(JSON_VAL);
        var origin = fVectorSupplier.get().applyStateFrom(structure.getJSONObject(0));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPlane self() {

        return this;
    }

    @Override
    public FPlane copy() {

        return copyZero().setRefOrigin(getRefOrigin().copy());
    }

    @Override
    public FPlane copyZero() {

        return create(epsilon, fLineSupplier, fVectorSupplier);
    }

    @Override
    public FPairPos3D toFPairPos3D() {

        return getRefOrigin().toFPairPos3D();
    }

    @Override
    public JSONObject toJSON() {
        var json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getRefOrigin().toJSON());

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
    public boolean isCoplanar(FPlane ref) {

        return getRefOrigin().isCollinear(ref.getRefOrigin()) && isPartOf(ref.getRefOrigin().getRefBase());
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
    public boolean isCut(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        List<Boolean> isInHalfSpace = geometry.disassemble().stream()
                .map(p -> p.toBooleanWithFixedState(e -> {
                    projectUnitOnLine(e);
                    return isUnitInHalfSpace(e);
                }))
                .collect(Collectors.toList());

        boolean conditionTrue = isInHalfSpace.stream().anyMatch(e -> e);
        boolean conditionFalse = isInHalfSpace.stream().anyMatch(e -> !e);

        return conditionTrue && conditionFalse;
    }

    @Override
    public boolean isOnSide(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .allMatch(p -> p.toBooleanWithFixedState(e -> {
                    projectUnitOnLine(e);
                    return isUnitInHalfSpace(e);
                }));
    }

    @Override
    public Optional<FLine> getFLineAtIntersection(FPlane ref) {

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

        return Optional.of(fLineSupplier.get().setRefOrigin(fVectorSupplier.get().setRefHead(vPlanePar).moveBase(pos)));
    }

    @Override
    public Optional<FPoint> getFPointAtIntersection(FLine ref) {

        if (getRefOrigin().isOrthogonal(ref.getRefOrigin())) {
            return Optional.empty();
        }

        FPoint results = fVectorSupplier.get().getRefBase().copyZero();

        getRefOrigin().applyWithFixedState(a -> {
            ref.getRefOrigin().applyWithFixedState(b -> {
                double aBX = a.getBaseX();
                double aBY = a.getBaseY();
                double aBZ = a.getBaseZ();
                double bBX = b.getBaseX();
                double bBY = b.getBaseY();
                double bBZ = b.getBaseZ();

                a.moveBaseToCenter().normalize();
                b.moveBaseToCenter().normalize();

                double dividend = a.getRefHead().getDotProduct(aBX - bBX, aBY - bBY, aBZ - bBZ);
                double divisor = a.getRefHead().getDotProduct(b.getRefHead());
                double distance = dividend / divisor;

                b.moveBase(bBX, bBY, bBZ).setMagnitude(distance);

                results.applyStateFrom(b.getRefHead());
            });
        });

        return Optional.of(results);
    }

    // -------------------------------------------------------------------------------------------------

    private void projectUnit(FPoint in) {

        getRefOrigin().applyWithFixedState(o -> {
            FPoint oBase = o.getRefBase();
            FPoint oHead = o.getRefHead();

            double memoX = oBase.getX();
            double memoY = oBase.getY();
            double memoZ = oBase.getZ();

            double oMagnitude = o.getMagnitude();

            oHead.sub(oBase);
            oHead.div(oMagnitude);
            oHead.mul(oHead.getDotProduct(in.getX() - memoX, in.getY() - memoY, in.getZ() - memoZ));

            oBase.add(oHead);

            o.setHead(in).moveBase(memoX, memoY, memoZ);

            in.applyStateFrom(o.getRefHead());
        });
    }

    private void projectUnitOnLine(FPoint in) {

        getRefOrigin().applyWithFixedState(o -> {
            FPoint oBase = o.getRefBase();
            FPoint oHead = o.getRefHead();

            double oMagnitude = o.getMagnitude();

            in.sub(oBase);

            oHead.sub(oBase);
            oHead.div(oMagnitude);
            oHead.mul(oHead.getDotProduct(in));

            oBase.add(oHead);

            in.applyStateFrom(oBase);
        });
    }

    private boolean isUnitInHalfSpace(FPoint arg) {
        double magnitude = getRefOrigin().getMagnitude();

        double distBase = getRefOrigin().getRefBase().getDistance(arg);
        double distHead = getRefOrigin().getRefHead().getDistance(arg);

        if ((distBase < magnitude + epsilon) && (distHead < magnitude + epsilon)) {
            return true;
        }

        return distHead < distBase + epsilon;
    }
}

// http://geomalgorithms.com/a05-_intersect-1.html
// https://opentextbc.ca/calculusv3openstax/chapter/equations-of-lines-and-planes-in-space/