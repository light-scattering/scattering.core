package eu.scattering.core.impl.mutables.geometry.primitive;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.impl.mutables.geometry.primitive.support.PrimitivePresetDef;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import org.json.JSONArray;
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

    private final double[] origin = { 0.0, 0.0, 0.0 };
    private final double epsilon;

    private FPointDef(double epsilon) {

        this.epsilon = epsilon;
    }

    public static FPoint create(double epsilon) {

        return new FPointDef(epsilon);
    }

    @Override
    public double getX() {

        return origin[0];
    }

    @Override
    public FPoint setX(double x) {

        origin[0] = x;

        return this;
    }

    @Override
    public double getY() {

        return origin[1];
    }

    @Override
    public FPoint setY(double y) {

        origin[1] = y;

        return this;
    }

    @Override
    public double getZ() {

        return origin[2];
    }

    @Override
    public FPoint setZ(double z) {

        origin[2] = z;

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
    public FPoint applyStateFrom(FPoint ref) {

        return set(ref.getX(), ref.getY(), ref.getZ());
    }

    @Override
    public FPoint applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
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
    public boolean isExact(FPoint ref) {

        if (ref == null) {
            throw new NullPointerException("The reference FPoint is null");
        }

        return isExact(ref.getX(), ref.getY(), ref.getZ());
    }

    @Override
    public boolean isSimilar(double x, double y, double z) {

        double distanceX = Math.abs(getX() - x);
        double distanceY = Math.abs(getY() - y);
        double distanceZ = Math.abs(getZ() - z);

        return distanceX < epsilon && distanceY < epsilon && distanceZ < epsilon;
    }

    @Override
    public boolean isSimilar(FPoint ref) {

        if (ref == null) {
            throw new NullPointerException("The reference FPoint is null");
        }

        return isSimilar(ref.getX(), ref.getY(), ref.getZ());
    }

    @Override
    public FPoint self() {

        return this;
    }

    @Override
    public FPoint copy() {

        return FPointDef.create(epsilon).applyStateFrom(this);
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
        JSONObject json = new JSONObject();

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
            FPoint ref = (FPoint) object;

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
    public FPoint add(FPoint fPoint) {

        return add(fPoint.getX(), fPoint.getY(), fPoint.getZ());
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
    public FPoint sub(FPoint fPoint) {

        return sub(fPoint.getX(), fPoint.getY(), fPoint.getZ());
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
    public FPoint mul(FPoint fPoint) {

        return mul(fPoint.getX(), fPoint.getY(), fPoint.getZ());
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
    public FPoint div(FPoint fPoint) {

        return div(fPoint.getX(), fPoint.getY(), fPoint.getZ());
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
    public boolean isNonDirectional() {

        return isSimilar(0, 0, 0);
    }

    @Override
    public double getDistanceP2(FPoint ref) {
        double dimX = ref.getX() - getX();
        double dimY = ref.getY() - getY();
        double dimZ = ref.getZ() - getZ();

        return (dimX * dimX) + (dimY * dimY) + (dimZ * dimZ);
    }

    @Override
    public double getDistance(FPoint ref) {

        return Math.sqrt(getDistanceP2(ref));
    }

    @Override
    public FPoint setDistance(FPoint ref, double distance) throws IllegalStateException {

        if (equals(ref)) {
            throw new IllegalStateException("FPoints must not be on the same position");
        }

        return sub(ref).setLength(distance).add(ref);
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

        if (isNonDirectional()) {
            throw new IllegalStateException("The vector is non-directional (the position is too close to zero)");
        }

        return mul(length / getLength());
    }

    @Override
    public FPoint normalize() {

        return setLength(1);
    }

    @Override
    public FPoint reflect() {

        return set(-getX(), -getY(), -getZ());
    }

    @Override
    public FPoint reflect(FPoint ref) {

        return sub(ref).reflect().add(ref);
    }

    @Override
    public double getAngle(FPoint ref) {

        if (isNonDirectional()) {
            throw new IllegalStateException("The input vector is non-directional");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalStateException("The reference vector is non-directional");
        }

        double dProd = getDotProduct(ref);
        double magAB = getLength() * ref.getLength();
        double angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public FPoint setAngle(FPoint ref, double angle) {

        if (isNonDirectional()) {
            throw new IllegalArgumentException("The input vector is non-directional");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalArgumentException("The reference vector is non-directional");
        }

        double lenOpA = getLength();
        normalize();

        FPoint rotAxis = ref.copy().setCrossProduct(this).normalize();

        if (rotAxis.isNonDirectional()) {
            throw new IllegalStateException("The rotation axis is non-directional");
        }

        double aCurrent = Math.acos(getDotProduct(ref));
        double aDelta = angle - aCurrent;

        double aDeltaCos = Math.cos(aDelta);
        double aDeltaSin = Math.sin(aDelta);

        double tmpX = rotAxis.getX() * getX();
        double tmpY = rotAxis.getY() * getY();
        double tmpZ = rotAxis.getZ() * getZ();

        double tmpSuffix = (1 - aDeltaCos) * (tmpX + tmpY + tmpZ);

        double opX = aDeltaCos * getX() + aDeltaSin * (rotAxis.getY() * getZ() - rotAxis.getZ() * getY()) + rotAxis.getX() * tmpSuffix;
        double opY = aDeltaCos * getY() + aDeltaSin * (rotAxis.getZ() * getX() - rotAxis.getX() * getZ()) + rotAxis.getY() * tmpSuffix;
        double opZ = aDeltaCos * getZ() + aDeltaSin * (rotAxis.getX() * getY() - rotAxis.getY() * getX()) + rotAxis.getZ() * tmpSuffix;

        set(opX, opY, opZ).setLength(lenOpA);

        return this;
    }

    @Override
    public double getDotProduct(FPoint ref) {
        double dimX = getX() * ref.getX();
        double dimY = getY() * ref.getY();
        double dimZ = getZ() * ref.getZ();

        return dimX + dimY + dimZ;
    }

    @Override
    public FPoint setCrossProduct(FPoint ref) {
        double dimX = (getY() * ref.getZ()) - (getZ() * ref.getY());
        double dimY = (getZ() * ref.getX()) - (getX() * ref.getZ());
        double dimZ = (getX() * ref.getY()) - (getY() * ref.getX());

        set(dimX, dimY, dimZ);

        return this;
    }

    @Override
    public FPoint setSphericalCoordinates(double inclination, double azimuth) {
        double radius = getLength();

        setX(Math.cos(azimuth) * Math.sin(inclination));
        setY(Math.cos(inclination));
        setZ(Math.sin(azimuth) * Math.sin(inclination));

        return setLength(radius);
    }

    @Override
    public double getInclination() {

        return Math.acos(getY() / getLength());
    }

    @Override
    public FPoint setInclination(double polar) {
        double radius = getLength();

        return setSphericalCoordinates(polar, getAzimuth()).setLength(radius);
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
        double radius = getLength();

        return setSphericalCoordinates(getInclination(), azimuthal).setLength(radius);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPoint apply(Consumer<FPoint> action) {

        action.accept(this);

        return this;
    }

    @Override
    public double terminatorDouble(Function<FPoint, Double> action) {

        return action.apply(this);
    }

    @Override
    public boolean terminatorBoolean(Function<FPoint, Boolean> action) {

        return action.apply(this);
    }
}
