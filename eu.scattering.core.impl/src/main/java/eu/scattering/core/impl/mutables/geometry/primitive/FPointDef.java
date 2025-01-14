package eu.scattering.core.impl.mutables.geometry.primitive;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.impl.mutables.geometry.primitive.support.PrimitivePresetDef;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.impl.configurations.NameConfigDef.JSON_TYPE;

public class FPointDef extends PrimitivePresetDef<FPoint> implements FPoint {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private static final String JSON_MAIN = "point";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double epsilon;
    private double oX, oY, oZ;

    private FPointDef(double epsilon) {

        this.epsilon = epsilon;
    }

    public static FPoint create(double epsilon) {

        return new FPointDef(epsilon);
    }

    @Override
    public double getX() {

        return this.oX;
    }

    @Override
    public FPoint setX(double x) {

        this.oX = x;

        return this;
    }

    @Override
    public double getY() {

        return this.oY;
    }

    @Override
    public FPoint setY(double y) {

        this.oY = y;

        return this;
    }

    @Override
    public double getZ() {

        return this.oZ;
    }

    @Override
    public FPoint setZ(double z) {

        this.oZ = z;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FPoint set(double x, double y, double z) {

        return setX(x).setY(y).setZ(z);
    }

    @Override
    public FPoint set(FPos3D position) {

        return set(position.getD0(), position.getD1(), position.getD2());
    }

    @Override
    public FPoint applyStateFrom(FPoint op) {

        return set(op.getX(), op.getY(), op.getZ());
    }

    @Override
    public FPoint applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        var structure = json.getJSONArray(JSON_VAL);
        var x = structure.getDouble(0);
        var y = structure.getDouble(1);
        var z = structure.getDouble(2);

        return set(x, y, z);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(double x, double y, double z) {

        return getX() == x && getY() == y && getZ() == z;
    }

    @Override
    public boolean isExact(FPoint op) {

        if (this == op) {
            return true;
        }

        return isExact(op.getX(), op.getY(), op.getZ());
    }

    @Override
    public boolean isSimilar(double x, double y, double z) {

        double distanceX = Math.abs(getX() - x);
        double distanceY = Math.abs(getY() - y);
        double distanceZ = Math.abs(getZ() - z);

        return distanceX < epsilon && distanceY < epsilon && distanceZ < epsilon;
    }

    @Override
    public boolean isSimilar(FPoint op) {

        if (this == op) {
            return true;
        }

        return isSimilar(op.getX(), op.getY(), op.getZ());
    }

    @Override
    public FPoint self() {

        return this;
    }

    @Override
    public FPoint copy() {

        return copyZero().applyStateFrom(this);
    }

    @Override
    public FPoint copyZero() {

        return FPointDef.create(epsilon);
    }

    @Override
    public FPos3D toFPos3D() {

        return factory.getFPos3D(getX(), getY(), getZ());
    }

    @Override
    public JSONObject toJSON() {
        var json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getX());
        json.append(JSON_VAL, getY());
        json.append(JSON_VAL, getZ());

        return json;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(getX(), getY(), getZ());
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPoint) {
            var ref = (FPoint) object;

            return getX() == ref.getX() && getY() == ref.getY() && getZ() == ref.getZ();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPoint add(double x, double y, double z) {

        return addX(x).addY(y).addZ(z);
    }

    @Override
    public FPoint add(FPoint op) {

        return add(op.getX(), op.getY(), op.getZ());
    }

    @Override
    public FPoint add(double factor) {

        return add(factor, factor, factor);
    }

    @Override
    public FPoint addX(double x) {

        return setX(getX() + x);
    }

    @Override
    public FPoint addY(double y) {

        return setY(getY() + y);
    }

    @Override
    public FPoint addZ(double z) {

        return setZ(getZ() + z);
    }

    @Override
    public FPoint sub(double x, double y, double z) {

        return subX(x).subY(y).subZ(z);
    }

    @Override
    public FPoint sub(FPoint op) {

        return sub(op.getX(), op.getY(), op.getZ());
    }

    @Override
    public FPoint sub(double factor) {

        return sub(factor, factor, factor);
    }

    @Override
    public FPoint subX(double x) {

        return setX(getX() - x);
    }

    @Override
    public FPoint subY(double y) {

        return setY(getY() - y);
    }

    @Override
    public FPoint subZ(double z) {

        return setZ(getZ() - z);
    }

    @Override
    public FPoint mul(double x, double y, double z) {

        return mulX(x).mulY(y).mulZ(z);
    }

    @Override
    public FPoint mul(FPoint op) {

        return mul(op.getX(), op.getY(), op.getZ());
    }

    @Override
    public FPoint mul(double factor) {

        return mul(factor, factor, factor);
    }

    @Override
    public FPoint mulX(double x) {

        return setX(getX() * x);
    }

    @Override
    public FPoint mulY(double y) {

        return setY(getY() * y);
    }

    @Override
    public FPoint mulZ(double z) {

        return setZ(getZ() * z);
    }

    @Override
    public FPoint div(double x, double y, double z) {

        return divX(x).divY(y).divZ(z);
    }

    @Override
    public FPoint div(FPoint op) {

        return div(op.getX(), op.getY(), op.getZ());
    }

    @Override
    public FPoint div(double factor) {

        return div(factor, factor, factor);
    }

    @Override
    public FPoint divY(double y) {

        if (y == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return setY(getY() / y);
    }

    @Override
    public FPoint divX(double x) {

        if (x == 0) {
            throw new ArithmeticException("Division by zero");
        }

        return setX(getX() / x);
    }

    @Override
    public FPoint divZ(double z) {

        if (z == 0) {
            throw new ArithmeticException("Division by zero");
        }

        return setZ(getZ() / z);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public List<FPoint> disassemble() {
        List<FPoint> fPointList = new ArrayList<>();

        fPointList.add(this);

        return fPointList;
    }

    @Override
    public boolean isZero() {

        return getX() == 0 && getY() == 0 && getZ() == 0;
    }

    @Override
    public boolean isNearZero() {

        return isSimilar(0, 0, 0);
    }

    @Override
    public double getDistanceP2(double x, double y, double z) {
        double dimX = x - getX();
        double dimY = y - getY();
        double dimZ = z - getZ();

        return (dimX * dimX) + (dimY * dimY) + (dimZ * dimZ);
    }

    @Override
    public double getDistanceP2(FPoint op) {
        double dimX = op.getX() - getX();
        double dimY = op.getY() - getY();
        double dimZ = op.getZ() - getZ();

        return (dimX * dimX) + (dimY * dimY) + (dimZ * dimZ);
    }

    @Override
    public double getDistance(double x, double y, double z) {

        return Math.sqrt(getDistanceP2(x, y, z));
    }

    @Override
    public double getDistance(FPoint op) {

        return Math.sqrt(getDistanceP2(op));
    }

    @Override
    public FPoint setDistance(double x, double y, double z, double distance) {

        if (isExact(x, y, z)) {
            throw new IllegalStateException("FPoints must not be on the same position");
        }

        return sub(x, y, z).setLength(distance).add(x, y, z);
    }

    @Override
    public FPoint setDistance(FPoint op, double distance) {

        if (equals(op)) {
            throw new IllegalStateException("FPoints must not be on the same position");
        }

        return sub(op).setLength(distance).add(op);
    }

    @Override
    public double getLengthP2() {

        return (getX() * getX()) + (getY() * getY()) + (getZ() * getZ());
    }

    @Override
    public double getLength() {

        return Math.sqrt(getLengthP2());
    }

    @Override
    public FPoint setLength(double length) {

        if (isNearZero()) {
            throw new IllegalStateException("The vector is non-directional (the position is too close to zero)");
        }

        return mul(length / getLength());
    }

    @Override
    public FPoint normalize() {

        return setLength(1);
    }

    @Override
    public FPoint reflectThroughCenter() {

        return set(-getX(), -getY(), -getZ());
    }

    @Override
    public FPoint reflect(double x, double y, double z) {

        return sub(x, y, z).reflectThroughCenter().add(x, y, z);
    }

    @Override
    public FPoint reflect(FPoint op) {

        return sub(op).reflectThroughCenter().add(op);
    }

    @Override
    public double getAngle(double x, double y, double z) {

        if (isNearZero()) {
            throw new IllegalStateException("The input vector is non-directional");
        }

        if (Math.abs(x) < epsilon && Math.abs(y) < epsilon && Math.abs(z) < epsilon) {
            throw new IllegalStateException("The reference vector is non-directional");
        }

        if (isSimilar(x, y, z)) {
            throw new IllegalStateException("The vectors are similar");
        }

        double dProd = getDotProduct(x, y, z);
        double magAB = getLength() * Math.sqrt((x * x) + (y * y) + (z * z));
        double angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public double getAngle(FPoint op) {

        if (isNearZero()) {
            throw new IllegalStateException("The input vector is non-directional");
        }

        if (op.isNearZero()) {
            throw new IllegalStateException("The reference vector is non-directional");
        }

        if (isSimilar(op)) {
            throw new IllegalStateException("The vectors are similar");
        }

        double dProd = getDotProduct(op);
        double magAB = getLength() * op.getLength();
        double angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public FPoint setAngle(double x, double y, double z, double angle) {

        if (isNearZero()) {
            throw new IllegalStateException("The input vector is non-directional");
        }

        if (Math.abs(x) < epsilon && Math.abs(y) < epsilon && Math.abs(z) < epsilon) {
            throw new IllegalArgumentException("The reference vector is non-directional");
        }

        if (isSimilar(x, y, z)) {
            throw new IllegalArgumentException("The vectors are similar");
        }

        return applyWithFixedLength(p -> {
            p.normalize();

            double opX = (y * getZ()) - (z * getY());
            double opY = (z * getX()) - (x * getZ());
            double opZ = (x * getY()) - (y * getX());

            double factor = 1 / Math.sqrt((opX * opX) + (opY * opY) + (opZ * opZ));

            opX *= factor;
            opY *= factor;
            opZ *= factor;

            if (Math.abs(opX) < epsilon && Math.abs(opY) < epsilon && Math.abs(opZ) < epsilon) {
                throw new IllegalStateException("The rotation vector is non-directional");
            }

            var aDelta = angle - Math.acos(getDotProduct(x, y, z));

            var aCos = Math.cos(aDelta);
            var aSin = Math.sin(aDelta);

            var tmpSuffix = (1 - aCos) * (opX * p.getX() + opY * p.getY() + opZ * p.getZ());

            var resX = aCos * p.getX() + aSin * (opY * p.getZ() - opZ * p.getY()) + opX * tmpSuffix;
            var resY = aCos * p.getY() + aSin * (opZ * p.getX() - opX * p.getZ()) + opY * tmpSuffix;
            var resZ = aCos * p.getZ() + aSin * (opX * p.getY() - opY * p.getX()) + opZ * tmpSuffix;

            p.set(resX, resY, resZ);
        });
    }

    @Override
    public FPoint setAngle(FPoint op, double angle) {

        if (isNearZero()) {
            throw new IllegalArgumentException("The input vector is non-directional");
        }

        if (op.isNearZero()) {
            throw new IllegalArgumentException("The reference vector is non-directional");
        }
// TODO - test for similarity
// TODO - check exception types
        return applyWithFixedLength(p -> {
            p.normalize();

            op.applyWithFixedState(rotAxis -> {
                rotAxis.setCrossProduct(p).normalize();

                if (rotAxis.isNearZero()) {
                    throw new IllegalStateException("The rotation vector is non-directional");
                }

                var aDelta = angle - Math.acos(getDotProduct(op));

                var aCos = Math.cos(aDelta);
                var aSin = Math.sin(aDelta);

                var tmpSuffix = (1 - aCos) * (rotAxis.getX() * p.getX() + rotAxis.getY() * p.getY() + rotAxis.getZ() * p.getZ());

                var opX = aCos * p.getX() + aSin * (rotAxis.getY() * p.getZ() - rotAxis.getZ() * p.getY()) + rotAxis.getX() * tmpSuffix;
                var opY = aCos * p.getY() + aSin * (rotAxis.getZ() * p.getX() - rotAxis.getX() * p.getZ()) + rotAxis.getY() * tmpSuffix;
                var opZ = aCos * p.getZ() + aSin * (rotAxis.getX() * p.getY() - rotAxis.getY() * p.getX()) + rotAxis.getZ() * tmpSuffix;

                p.set(opX, opY, opZ);
            });
        });
    }

    @Override
    public double getDotProduct(double x, double y, double z) {
        double dimX = getX() * x;
        double dimY = getY() * y;
        double dimZ = getZ() * z;

        return dimX + dimY + dimZ;
    }

    @Override
    public double getDotProduct(FPoint op) {
        double dimX = getX() * op.getX();
        double dimY = getY() * op.getY();
        double dimZ = getZ() * op.getZ();

        return dimX + dimY + dimZ;
    }

    @Override
    public FPoint setCrossProduct(double x, double y, double z) {
        double dimX = (getY() * z) - (getZ() * y);
        double dimY = (getZ() * x) - (getX() * z);
        double dimZ = (getX() * y) - (getY() * x);

        return set(dimX, dimY, dimZ);
    }

    @Override
    public FPoint setCrossProduct(FPoint op) {
        double dimX = (getY() * op.getZ()) - (getZ() * op.getY());
        double dimY = (getZ() * op.getX()) - (getX() * op.getZ());
        double dimZ = (getX() * op.getY()) - (getY() * op.getX());

        return set(dimX, dimY, dimZ);
    }

    @Override
    public FPoint setSphericalCoordinates(double inclination, double azimuth) {

        return applyWithFixedLength(p -> {
            p.setX(Math.cos(azimuth) * Math.sin(inclination));
            p.setY(Math.cos(inclination));
            p.setZ(Math.sin(azimuth) * Math.sin(inclination));
        });
    }

    @Override
    public double getInclination() {

        return Math.acos(getY() / getLength());
    }

    @Override
    public FPoint setInclination(double polar) {

        return applyWithFixedLength(p -> p.setSphericalCoordinates(polar, p.getAzimuth()));
    }

    @Override
    public double getAzimuth() {
        double r2 = Math.sqrt((getX() * getX()) + (getZ() * getZ()));
        double azimuthal;

        if (getZ() >= 0) {
            azimuthal = Math.acos(getX() / r2);
        } else {
            azimuthal = -Math.acos(getX() / r2);
        }

        if (Double.isNaN(azimuthal)) {
            return 0;
        }

        return azimuthal;
    }

    @Override
    public FPoint setAzimuth(double azimuthal) {

        return applyWithFixedLength(p -> p.setSphericalCoordinates(p.getInclination(), azimuthal));
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPoint apply(Consumer<FPoint> action) {

        action.accept(this);

        return this;
    }

    @Override
    public FPoint applyWithFixedState(Consumer<FPoint> action) {
        double memoX = this.getX();
        double memoY = this.getY();
        double memoZ = this.getZ();

        action.accept(this);

        return set(memoX, memoY, memoZ);
    }

    @Override
    public FPoint applyWithFixedLength(Consumer<FPoint> action) {
        double length = getLength();

        action.accept(this);

        return setLength(length);
    }

    @Override
    public double toDouble(Function<FPoint, Double> action) {

        return action.apply(this);
    }

    @Override
    public boolean toBoolean(Function<FPoint, Boolean> action) {

        return action.apply(this);
    }

    @Override
    public double toDoubleWithFixedState(Function<FPoint, Double> action) {
        double memoX = this.getX();
        double memoY = this.getY();
        double memoZ = this.getZ();

        double results = action.apply(this);

        set(memoX, memoY, memoZ);

        return results;
    }

    @Override
    public boolean toBooleanWithFixedState(Function<FPoint, Boolean> action) {
        double memoX = this.getX();
        double memoY = this.getY();
        double memoZ = this.getZ();

        boolean results = action.apply(this);

        set(memoX, memoY, memoZ);

        return results;
    }
}
