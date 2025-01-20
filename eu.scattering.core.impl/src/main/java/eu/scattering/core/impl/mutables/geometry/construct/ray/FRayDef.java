package eu.scattering.core.impl.mutables.geometry.construct.ray;

import eu.scattering.core.design.mutables.geometry.Geometry;
import eu.scattering.core.design.mutables.geometry.construct.ray.FRay;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.impl.mutables.geometry.construct.ConstructPresetDef;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import org.json.JSONObject;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static eu.scattering.core.impl.configurations.NameConfigDef.JSON_TYPE;

public class FRayDef extends ConstructPresetDef<FRay> implements FRay {
    private static final String JSON_MAIN = "ray";
    private static final String JSON_VAL = "val";

    private final Supplier<FVector> fVectorSupplier;
    private final Supplier<FPoint> fPointSupplier;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double epsilon;
    private FVector origin;

    private FRayDef(double epsilon, FVector origin) {

        this.fVectorSupplier = origin::copyZero;
        this.fPointSupplier = () -> getRefOrigin().getRefBase().copyZero();

        this.epsilon = epsilon;
        this.origin = origin;
    }

    public static FRay create(double epsilon, FVector origin) {

        return new FRayDef(epsilon, origin);
    }

    @Override
    public FVector getRefOrigin() {

        return origin;
    }

    @Override
    public FRay setRefOrigin(FVector refOrigin) {

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
    public FRay set(FPairPos3D position) {

        getRefOrigin().set(position);

        return this;
    }

    @Override
    public FRay applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        var origin = fVectorSupplier.get().applyStateFrom(json.getJSONObject(JSON_VAL));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FRay self() {

        return this;
    }

    @Override
    public FRay copy() {

        return copyZero().setRefOrigin(getRefOrigin().copy());
    }

    @Override
    public FRay copyZero() {

        return create(epsilon, fVectorSupplier.get());
    }

    @Override
    public FPairPos3D toFPairPos3D() {

        return getRefOrigin().toFPairPos3D();
    }

    @Override
    public JSONObject toJSON() {
        var json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_VAL, getRefOrigin().toJSON());

        return json;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {

        return getRefOrigin().hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FRay) {
            var ref = (FRay) object;

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

            var isValid = projectUnit(p);

            var pX = p.getX();
            var pY = p.getY();
            var pZ = p.getZ();

            p.set(oX, oY, oZ);

            if (!isValid) {
                return;
            }

            p.reflect(pX, pY, pZ);
        });
    }

    @Override
    public boolean isPartOf(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream().allMatch(this::isPartOfUnit);
    }

    @Override
    public List<OptionalDouble> getAtomicDistanceP2(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> {
                    var oX = p.getX();
                    var oY = p.getY();
                    var oZ = p.getZ();

                    var isValid = projectUnit(p);

                    var pX = p.getX();
                    var pY = p.getY();
                    var pZ = p.getZ();

                    p.set(oX, oY, oZ);

                    if (!isValid) {
                        return OptionalDouble.empty();
                    }

                    return OptionalDouble.of(p.getDistanceP2(pX, pY, pZ));
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<OptionalDouble> getAtomicDistance(Geometry geometry) {

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        return geometry.disassemble().stream()
                .map(p -> {
                    var oX = p.getX();
                    var oY = p.getY();
                    var oZ = p.getZ();

                    var isValid = projectUnit(p);

                    var pX = p.getX();
                    var pY = p.getY();
                    var pZ = p.getZ();

                    p.set(oX, oY, oZ);

                    if (!isValid) {
                        return OptionalDouble.empty();
                    }

                    return OptionalDouble.of(p.getDistance(pX, pY, pZ));
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

            var isValid = projectUnit(p);

            var pX = p.getX();
            var pY = p.getY();
            var pZ = p.getZ();

            p.set(oX, oY, oZ);

            if (!isValid) {
                return;
            }

            p.setDistance(pX, pY, pZ, distance);
        });
    }

    @Override
    public void shiftForward(Geometry geometry, double distance) {

        geometry.disassemble().forEach(p -> shiftUnitForward(p, distance));
    }

    @Override
    public void shiftBackward(Geometry geometry, double distance) {

        geometry.disassemble().forEach(p -> shiftUnitBackward(p, distance));
    }

    @Override
    public FPoint getFPointAtDistance(double length) {

        if (length < 0) {
            throw new IllegalArgumentException("The distance must be a positive value");
        }

        if (getRefOrigin().isNearZeroLength()) {
            throw new IllegalStateException("The origin is a non-directional FVector");
        }

        var fPoint = fPointSupplier.get();

        fPoint.applyStateFrom(getRefOrigin().getRefHead());
        fPoint.sub(getRefOrigin().getRefBase());

        var tmp = length / getRefOrigin().getMagnitude();

        fPoint.setX(getRefOrigin().getRefBase().getX() + (fPoint.getX() * tmp));
        fPoint.setY(getRefOrigin().getRefBase().getY() + (fPoint.getY() * tmp));
        fPoint.setZ(getRefOrigin().getRefBase().getZ() + (fPoint.getZ() * tmp));

        return fPoint;
    }

    // -------------------------------------------------------------------------------------------------

    private boolean projectUnit(FPoint in) {
        double memoX = in.getX();
        double memoY = in.getY();
        double memoZ = in.getZ();

        getRefOrigin().applyWithFixedState(o -> {
            FPoint oBase = o.getRefBase();
            FPoint oHead = o.getRefHead();

            double oMagnitude = o.getMagnitude();

            in.sub(oBase);

            oHead.sub(oBase);
            oHead.div(oMagnitude);

            oHead.mul(in.getDotProduct(oHead));
            oBase.add(oHead);

            in.applyStateFrom(oBase);
        });

        boolean isValid = projectUnitValidate(in);

        if (isValid) {
            return true;
        }

        in.set(memoX, memoY, memoZ);

        return false;
    }

    private boolean projectUnitValidate(FPoint arg) {
        FPoint oBase = getRefOrigin().getRefBase();
        FPoint oHead = getRefOrigin().getRefHead();

        double oMagnitude = getRefOrigin().getMagnitude();

        double distBase = oBase.getDistance(arg);
        double distHead = oHead.getDistance(arg);

        if (Math.abs(distBase + distHead - oMagnitude) < epsilon) {
            return true;
        }

        return distBase > distHead;
    }

    private boolean isPartOfUnit(FPoint arg) {
        double memoX = arg.getX();
        double memoY = arg.getY();
        double memoZ = arg.getZ();

        return arg.toBooleanWithFixedState(p -> {
            boolean isValid = projectUnit(p);

            if (!isValid) {
                return false;
            }

            return p.getDistance(memoX, memoY, memoZ) < epsilon;
        });
    }

    private void shiftUnitForward(FPoint in, double dist) {

        getRefOrigin().applyWithFixedState(o -> {
            in.applyStateFrom(o.moveBase(in).shiftForward(dist).getRefBase());
        });
    }

    private void shiftUnitBackward(FPoint in, double dist) {

        getRefOrigin().applyWithFixedState(o -> {
            in.applyStateFrom(o.moveBase(in).shiftBackward(dist).getRefBase());
        });
    }
}