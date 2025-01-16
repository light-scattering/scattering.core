package eu.scattering.core.impl.mutables.geometry.primitive;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.impl.mutables.geometry.primitive.support.PrimitivePresetDef;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

import static eu.scattering.core.impl.configurations.NameConfigDef.JSON_TYPE;

public class FVectorDef extends PrimitivePresetDef<FVector> implements FVector {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private static final String JSON_MAIN = "vector";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FPoint oBase;
    private FPoint oHead;
    private final double epsilon;

    private FVectorDef(double epsilon) {

        this.epsilon = epsilon;
    }

    public static FVector create(double epsilon, FPoint refBase, FPoint refHead) {

        if (refBase == null) {
            throw new NullPointerException("The base FPoint cannot be null");
        }

        if (refHead == null) {
            throw new NullPointerException("The head FPoint cannot be null");
        }

        var fVector = new FVectorDef(epsilon);

        fVector.setRefBase(refBase);
        fVector.setRefHead(refHead);

        return fVector;
    }

    @Override
    public FPoint getRefBase() {

        return oBase;
    }

    @Override
    public FVector setRefBase(FPoint refBase) {

        if (refBase == null) {
            throw new NullPointerException("The base FPoint cannot be null");
        }

        oBase = refBase;

        return this;
    }

    @Override
    public FPoint getRefHead() {

        return oHead;
    }

    @Override
    public FVector setRefHead(FPoint refHead) {

        if (refHead == null) {
            throw new NullPointerException("The head FPoint cannot be null");
        }

        oHead = refHead;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector setRef(FPoint refBase, FPoint refHead) {
        setRefBase(refBase);
        setRefHead(refHead);

        return this;
    }

    @Override
    public FVector set(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        setBase(bX, bY, bZ);
        setHead(hX, hY, hZ);

        return this;
    }

    @Override
    public FVector set(FPoint base, FPoint head) {
        setBase(base);
        setHead(head);

        return this;
    }

    @Override
    public FVector set(FPairPos3D position) {
        setBase(position.getPosA());
        setHead(position.getPosB());

        return this;
    }

    @Override
    public FVector setBase(double bX, double bY, double bZ) {
        getRefBase().set(bX, bY, bZ);

        return this;
    }

    @Override
    public FVector setBase(FPoint base) {
        getRefBase().applyStateFrom(base);

        return this;
    }

    @Override
    public FVector setBase(FPos3D base) {
        setBase(base.getD0(), base.getD1(), base.getD2());

        return this;
    }

    @Override
    public double getBaseX() {

        return getRefBase().getX();
    }

    @Override
    public double getBaseY() {

        return getRefBase().getY();
    }

    @Override
    public double getBaseZ() {

        return getRefBase().getZ();
    }

    @Override
    public FVector setBaseX(double bX) {
        getRefBase().setX(bX);

        return this;
    }

    @Override
    public FVector setBaseY(double bY) {
        getRefBase().setY(bY);

        return this;
    }

    @Override
    public FVector setBaseZ(double bZ) {
        getRefBase().setZ(bZ);

        return this;
    }

    @Override
    public FVector setHead(double hX, double hY, double hZ) {
        getRefHead().set(hX, hY, hZ);

        return this;
    }

    @Override
    public FVector setHead(FPoint head) {
        getRefHead().applyStateFrom(head);

        return this;
    }

    @Override
    public FVector setHead(FPos3D head) {
        setHead(head.getD0(), head.getD1(), head.getD2());

        return this;
    }

    @Override
    public double getHeadX() {

        return getRefHead().getX();
    }

    @Override
    public double getHeadY() {

        return getRefHead().getY();
    }

    @Override
    public double getHeadZ() {

        return getRefHead().getZ();
    }

    @Override
    public FVector setHeadX(double hX) {
        getRefHead().setX(hX);

        return this;
    }

    @Override
    public FVector setHeadY(double hY) {
        getRefHead().setY(hY);

        return this;
    }

    @Override
    public FVector setHeadZ(double hZ) {
        getRefHead().setZ(hZ);

        return this;
    }

    @Override
    public FVector applyStateFrom(FVector op) {
        setBase(op.getRefBase());
        setHead(op.getRefHead());

        return this;
    }

    @Override
    public FVector applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        var structure = json.getJSONArray(JSON_VAL);
        var base = getRefBase().applyStateFrom(structure.getJSONObject(0));
        var head = getRefHead().applyStateFrom(structure.getJSONObject(1));

        return set(base, head);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getRefBase().isExact(bX, bY, bZ) && getRefHead().isExact(hX, hY, hZ);
    }

    @Override
    public boolean isExact(FVector op) {

        if (this == op) {
            return true;
        }

        return getRefBase().isExact(op.getRefBase()) && getRefHead().isExact(op.getRefHead());
    }

    @Override
    public boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getRefBase().isSimilar(bX, bY, bZ) && getRefHead().isSimilar(hX, hY, hZ);
    }

    @Override
    public boolean isSimilar(FVector op) {

        if (this == op) {
            return true;
        }

        return getRefBase().isSimilar(op.getRefBase()) && getRefHead().isSimilar(op.getRefHead());
    }

    @Override
    public FVector self() {

        return this;
    }

    @Override
    public FVector copy() {

        return create(epsilon, getRefBase().copy(), getRefHead().copy());
    }

    @Override
    public FVector copyZero() {

        return create(epsilon, getRefBase().copyZero(), getRefHead().copyZero());
    }

    @Override
    public FPairPos3D toFPairPos3D() {

        var posA = getRefBase().toFPos3D();
        var posB = getRefHead().toFPos3D();

        return factory.getFPairPos3D(posA, posB);
    }

    @Override
    public JSONObject toJSON() {
        var json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getRefBase().toJSON());
        json.append(JSON_VAL, getRefHead().toJSON());

        return json;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(getBaseX(), getBaseY(), getBaseZ(), getHeadX(), getHeadY(), getHeadZ());
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FVector) {
            var ref = (FVector) object;

            return getRefBase().equals(ref.getRefBase()) && getRefHead().equals(ref.getRefHead());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector add(FVector op) {

        return applyWithCenteredPositionAndFixedState((a, b) ->
                a.getRefHead().add(b.moveBaseToCenter().getRefHead()), op);
    }

    @Override
    public FVector sub(FVector op) {

        return applyWithCenteredPositionAndFixedState((a, b) ->
                a.getRefHead().sub(b.moveBaseToCenter().getRefHead()), op);
    }

    //--------------------------------------------------

    @Override
    public List<FPoint> disassemble() {
        List<FPoint> fPointList = new ArrayList<>();

        fPointList.add(getRefBase());
        fPointList.add(getRefHead());

        return fPointList;
    }

    @Override
    public boolean isZeroLength() {

        return getRefBase().equals(getRefHead());
    }

    @Override
    public boolean isNearZeroLength() {

        return getRefBase().isSimilar(getRefHead());
    }

    @Override
    public double getLengthP2() {
        double distX = getRefHead().getX() - getRefBase().getX();
        double distY = getRefHead().getY() - getRefBase().getY();
        double distZ = getRefHead().getZ() - getRefBase().getZ();

        return (distX * distX) + (distY * distY) + (distZ * distZ);
    }

    @Override
    public double getLengthX() {

        return Math.abs(getRefHead().getX() - getRefBase().getX());
    }

    @Override
    public double getLengthY() {

        return Math.abs(getRefHead().getY() - getRefBase().getY());
    }

    @Override
    public double getLengthZ() {

        return Math.abs(getRefHead().getZ() - getRefBase().getZ());
    }

    @Override
    public double getLength() {

        return Math.sqrt(getLengthP2());
    }

    @Override
    public FVector setLength(double length) {

        return applyWithCenteredPosition(vZero -> getRefHead().setLength(length));
    }

    @Override
    public FVector normalize() {

        return applyWithCenteredPosition(vZero -> getRefHead().normalize());
    }

    @Override
    public FVector reflectBase() {

        getRefBase().reflect(getRefHead());

        return this;
    }

    @Override
    public FVector reflectHead() {

        getRefHead().reflect(getRefBase());

        return this;
    }

    @Override
    public FVector reflectThroughCenter() {

        getRefBase().reflect(0, 0, 0);
        getRefHead().reflect(0, 0, 0);

        return this;
    }

    @Override
    public FVector reflect(double x, double y, double z) {

        getRefBase().reflect(x, y, z);
        getRefHead().reflect(x, y, z);

        return this;
    }

    @Override
    public FVector reflect(FPoint op) {

        getRefBase().reflect(op);
        getRefHead().reflect(op);

        return this;
    }

    @Override
    public FVector swapBaseWithHead() {
        double memoX = getRefHead().getX();
        double memoY = getRefHead().getY();
        double memoZ = getRefHead().getZ();

        getRefHead().reflect(getRefBase());

        return moveBase(memoX, memoY, memoZ);
    }

    @Override
    public FVector moveBase(double bX, double bY, double bZ) {
        double distX = getRefBase().getX() - bX;
        double distY = getRefBase().getY() - bY;
        double distZ = getRefBase().getZ() - bZ;

        getRefBase().sub(distX, distY, distZ);
        getRefHead().sub(distX, distY, distZ);

        return this;
    }

    @Override
    public FVector moveBase(FPoint base) {
        double distX = getRefBase().getX() - base.getX();
        double distY = getRefBase().getY() - base.getY();
        double distZ = getRefBase().getZ() - base.getZ();

        getRefBase().sub(distX, distY, distZ);
        getRefHead().sub(distX, distY, distZ);

        return this;
    }

    @Override
    public FVector moveBaseToCenter() {

        return moveBase(0, 0, 0);
    }

    @Override
    public FVector moveHead(double hX, double hY, double hZ) {
        double distX = getRefHead().getX() - hX;
        double distY = getRefHead().getY() - hY;
        double distZ = getRefHead().getZ() - hZ;

        getRefBase().sub(distX, distY, distZ);
        getRefHead().sub(distX, distY, distZ);

        return this;
    }

    @Override
    public FVector moveHead(FPoint head) {
        double distX = getRefHead().getX() - head.getX();
        double distY = getRefHead().getY() - head.getY();
        double distZ = getRefHead().getZ() - head.getZ();

        getRefBase().sub(distX, distY, distZ);
        getRefHead().sub(distX, distY, distZ);

        return this;
    }

    @Override
    public FVector moveHeadToCenter() {

        return moveHead(0, 0, 0);
    }

    @Override
    public FVector shiftForward(double distance) {

        if (distance < 0) {
            return shiftBackward(-distance);
        }

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        return applyWithFixedLength(v -> v.setLength(distance).moveBase(getRefHead()));
    }

    @Override
    public FVector shiftBackward(double distance) {

        if (distance < 0) {
            return shiftForward(-distance);
        }

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        return applyWithFixedLength(v -> v.setLength(distance).reflectHead().moveBase(getRefHead()).reflectHead());
    }

    @Override
    public double getDotProduct(FVector op) {

        return toDoubleWithFixedState((a, b) ->
                a.moveBaseToCenter().getRefHead().getDotProduct(b.moveBaseToCenter().getRefHead()), op);
    }

    @Override
    public FVector setCrossProduct(FVector op) {

        return applyWithCenteredPositionAndFixedState((a, b) ->
                a.getRefHead().setCrossProduct(b.moveBaseToCenter().getRefHead()), op);
    }

    @Override
    public boolean isCollinear(FVector op) {

        return isParallel(op) || isAntiParallel(op);
    }

    @Override
    public FVector setCollinear(FVector op) {
        double lengthBase = getRefHead().getDistanceP2(op.getRefBase());
        double lengthHead = getRefHead().getDistanceP2(op.getRefHead());

        if (lengthHead < lengthBase) {
            return setParallel(op);
        }

        return setAntiParallel(op);
    }

    @Override
    public boolean isParallel(FVector op) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (op.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        return toBooleanWithFixedState((a, b) -> {
            a.moveBaseToCenter().normalize();
            b.moveBaseToCenter().normalize();

            return a.isSimilar(b);
        }, op);
    }

    @Override
    public FVector setParallel(FVector op) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (op.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        return applyWithCenteredPositionAndFixedState((a, b) ->
                a.getRefHead().applyStateFrom(b.moveBaseToCenter().getRefHead()), op);
    }

    @Override
    public boolean isAntiParallel(FVector op) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (op.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        return toBooleanWithFixedState((a, b) -> {
            a.moveBaseToCenter().normalize();
            b.moveBaseToCenter().normalize();

            return a.reflectHead().isSimilar(b);
        }, op);
    }

    @Override
    public FVector setAntiParallel(FVector op) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (op.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        return applyWithCenteredPositionAndFixedState((a, b) ->
                a.getRefHead().applyStateFrom(b.moveBaseToCenter().getRefHead()).reflectThroughCenter(), op);
    }

    @Override
    public boolean isOrthogonal(FVector op) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (op.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        return (Math.abs(getDotProduct(op)) < epsilon) || (Math.abs((Math.PI * 0.5) - getAngle(op)) < epsilon);
    }

    @Override
    public FVector setOrthogonal(FVector op) {

        if (isParallel(op)) {
            throw new IllegalStateException("FVectors are parallel");
        }

        if (isAntiParallel(op)) {
            throw new IllegalStateException("FVectors are anti-parallel");
        }

        return applyWithFixedLengthAndFixedState((a, b) ->
                a.applyStateFrom(b.setCrossProduct(a.setCrossProduct(b))), op);
    }

    @Override
    public FVector rotateAround(FVector op, double angle) {
        double baseX = op.getRefBase().getX();
        double baseY = op.getRefBase().getY();
        double baseZ = op.getRefBase().getZ();

        op.applyWithFixedState(v -> {
           sub(baseX, baseY, baseZ);
           op.getRefHead().sub(baseX, baseY, baseZ);
           getRefHead().rotateAround(op.getRefHead(), angle);
           getRefBase().rotateAround(op.getRefHead(), angle);
           add(baseX, baseY, baseZ);
        });

        return this;
    }

    @Override
    public FVector setSphericalCoordinates(double inclination, double azimuth) {

        return applyWithCenteredPosition(v -> v.getRefHead().setSphericalCoordinates(inclination, azimuth));
    }

    @Override
    public double getInclination() {

        return toDoubleWithFixedState(v -> v.moveBaseToCenter().getRefHead().getInclination());
    }

    @Override
    public FVector setInclination(double inclination) {

        return applyWithCenteredPosition(v -> v.getRefHead().setInclination(inclination));
    }

    @Override
    public double getAzimuth() {

        return toDoubleWithFixedState(v -> v.moveBaseToCenter().getRefHead().getAzimuth());
    }

    @Override
    public FVector setAzimuth(double azimuth) {

        return applyWithCenteredPosition(v -> v.getRefHead().setAzimuth(azimuth));
    }

    @Override
    public double getAngle(FVector op) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (op.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        return toDoubleWithFixedState((a, b) -> {
            a.moveBaseToCenter();
            b.moveBaseToCenter();
            return a.getRefHead().getAngle(b.getRefHead());
        }, op);
    }

    @Override
    public FVector setAngle(FVector op, double angle) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (op.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        return applyWithCenteredPositionAndFixedState((a, b) ->
                a.getRefHead().setAngle(b.moveBaseToCenter().getRefHead(), angle), op);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector apply(Consumer<FVector> action) {

        action.accept(this);

        return this;
    }

    @Override
    public FVector applyWithFixedState(Consumer<FVector> action) {
        double memoBX = this.getBaseX();
        double memoBY = this.getBaseY();
        double memoBZ = this.getBaseZ();
        double memoHX = this.getHeadX();
        double memoHY = this.getHeadY();
        double memoHZ = this.getHeadZ();

        action.accept(this);

        return this.set(memoBX, memoBY, memoBZ, memoHX, memoHY, memoHZ);
    }

    @Override
    public FVector applyWithFixedLength(Consumer<FVector> action) {
        double length = getLength();

        action.accept(this);

        return setLength(length);
    }

    @Override
    public FVector applyWithCenteredPosition(Consumer<FVector> action) {
        double memoX = this.getBaseX();
        double memoY = this.getBaseY();
        double memoZ = this.getBaseZ();

        moveBaseToCenter();

        action.accept(this);

        return moveBase(memoX, memoY, memoZ);
    }

    @Override
    public double toDouble(Function<FVector, Double> action) {

        return action.apply(this);
    }

    @Override
    public boolean toBoolean(Function<FVector, Boolean> action) {

        return action.apply(this);
    }

    @Override
    public double toDoubleWithFixedState(Function<FVector, Double> action) {
        double memoBX = this.getBaseX();
        double memoBY = this.getBaseY();
        double memoBZ = this.getBaseZ();
        double memoHX = this.getHeadX();
        double memoHY = this.getHeadY();
        double memoHZ = this.getHeadZ();

        double res = action.apply(this);

        set(memoBX, memoBY, memoBZ, memoHX, memoHY, memoHZ);

        return res;
    }

    @Override
    public boolean toBooleanWithFixedState(Function<FVector, Boolean> action) {
        double memoBX = this.getBaseX();
        double memoBY = this.getBaseY();
        double memoBZ = this.getBaseZ();
        double memoHX = this.getHeadX();
        double memoHY = this.getHeadY();
        double memoHZ = this.getHeadZ();

        boolean res = action.apply(this);

        set(memoBX, memoBY, memoBZ, memoHX, memoHY, memoHZ);

        return res;
    }

    // -------------------------------------------------------------------------------------------------

    private FVector applyWithFixedLengthAndFixedState(BiConsumer<FVector, FVector> action, FVector op) {

        return applyWithFixedLength(a -> op.applyWithFixedState(b -> action.accept(a, b)));
    }

    private FVector applyWithCenteredPositionAndFixedState(BiConsumer<FVector, FVector> action, FVector op) {

        return applyWithCenteredPosition(a -> op.applyWithFixedState(b -> action.accept(a, b)));
    }

    private double toDoubleWithFixedState(BiFunction<FVector, FVector, Double> action, FVector op) {

        return toDoubleWithFixedState(a -> op.toDoubleWithFixedState(b -> action.apply(a, b)));
    }

    private boolean toBooleanWithFixedState(BiFunction<FVector, FVector, Boolean> action, FVector op) {

        return toBooleanWithFixedState(a -> op.toBooleanWithFixedState(b -> action.apply(a, b)));
    }
}
