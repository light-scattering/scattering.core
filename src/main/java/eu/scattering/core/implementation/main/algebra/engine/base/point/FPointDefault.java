package eu.scattering.core.implementation.main.algebra.engine.base.point;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.box.rotation.FRotation;
import eu.scattering.core.implementation.main.algebra.engine.base.BasePresetDefault;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FPointDefault extends BasePresetDefault<FPoint> implements FPoint {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double[] origin = { 0.0, 0.0, 0.0 };
    private final Factory factory;

    private FPointDefault(Factory factory) {

        this.factory = factory;
    }

    public static FPoint create(Factory factory) {

        return new FPointDefault(factory);
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
    public FPoint set(FPoint fPoint) {

        return set(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint set(double x, double y, double z) {

        return setX(x).setY(y).setZ(z);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FPoint fPoint) {

        if (fPoint == null) {
            throw new NullPointerException("The reference FPoint cannot be null");
        }

        if (this == fPoint) {
            return true;
        }

        return getX() == fPoint.getX() && getY() == fPoint.getY() && getZ() == fPoint.getZ();
    }

    @Override
    public boolean isSimilar(FPoint fPoint) {

        if (fPoint == null) {
            throw new NullPointerException("The reference FPoint cannot be null");
        }

        if (this == fPoint) {
            return true;
        }

        double distanceX = Math.abs(getX() - fPoint.getX());
        double distanceY = Math.abs(getY() - fPoint.getY());
        double distanceZ = Math.abs(getZ() - fPoint.getZ());

        double jitter = factory.getJitter();

        return distanceX < jitter && distanceY < jitter && distanceZ < jitter;
    }

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.append("point", getX());
        json.append("point", getY());
        json.append("point", getZ());

        return json;
    }

    @Override
    public FPoint importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("point");

        setX(structure.getDouble(0));
        setY(structure.getDouble(1));
        setZ(structure.getDouble(2));

        return this;
    }

    @Override
    public FPoint copy() {

        return factory.getFPoint().set(this);
    }

    @Override
    public FPoint self() {

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        int hashCode = 7;

        hashCode = 31 * hashCode + (int) (getX() * 100);
        hashCode = 31 * hashCode + (int) (getY() * 100);
        hashCode = 31 * hashCode + (int) (getZ() * 100);

        return hashCode;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public List<FPoint> disassemble() {
        List<FPoint> fPointList = new ArrayList<>();
        fPointList.add(this);

        return fPointList;
    }

    @Override
    public FPoint add(FPoint fPoint) {

        return add(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint add(double x, double y, double z) {

        return addX(x).addY(y).addZ(z);
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
    public FPoint sub(FPoint fPoint) {

        return sub(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint sub(double x, double y, double z) {

        return subX(x).subY(y).subZ(z);
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
    public FPoint mul(FPoint fPoint) {

        return mul(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint mul(double x, double y, double z) {

        return mulX(x).mulY(y).mulZ(z);
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
    public FPoint div(FPoint fPoint) {

        return div(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint div(double x, double y, double z) {

        return divX(x).divY(y).divZ(z);
    }

    @Override
    public FPoint div(double factor) {

        return div(factor, factor, factor);
    }

    @Override
    public FPoint divX(double x) {

        if (x == 0) {
            throw new ArithmeticException("Division by zero");
        }

        return setX(getX() / x);
    }

    @Override
    public FPoint divY(double y) {

        if (y == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return setY(getY() / y);
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
    public FPoint setSphericalCoordinates(double inclination, double azimuth) {
        double radius = getLength();

        setX(Math.cos(azimuth) * Math.sin(inclination));
        setY(Math.cos(inclination));
        setZ(Math.sin(azimuth) * Math.sin(inclination));

        return setLength(radius);
    }

    @Override
    public FPoint setRandomAngle(FPoint... exclusion) {
        double radius = getLength();

        return set(factory.getHelperRandom().getFPoint(exclusion)).setLength(radius);
    }

    @Override
    public boolean isExact(double x, double y, double z) {

        return isExact(factory.getFPoint(x, y, z));
    }

    @Override
    public boolean isSimilar(double x, double y, double z) {

        return isSimilar(factory.getFPoint(x, y, z));
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
    public double getInclination() {

        return Math.acos(getY() / Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ())));
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
        } else {
            return azimuthal;
        }
    }

    @Override
    public FPoint setAzimuth(double azimuthal) {
        double radius = getLength();

        return setSphericalCoordinates(getInclination(), azimuthal).setLength(radius);
    }

    @Override
    public double getAngle(FPoint ref) throws IllegalStateException {

        if (isZero()) {
            throw new IllegalStateException("The input FPoint is zero");
        }

        if (ref.isZero()) {
            throw new IllegalStateException("The reference FPoint is zero");
        }

        double angle, dProd, magAB;

        dProd = getDotProduct(ref);
        magAB = getLength() * ref.getLength();
        angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public FPoint setAngle(FPoint ref, double angle) {

        FPoint axis = copy().setCrossProduct(ref);
        FRotation rotor = factory.getFRotation(axis, angle);

        ref.copy().ext(rotor.rotate()).imprint(this);

        return this;
    }

    @Override
    public FPoint rotate(FPoint ref, double angle) {

        FRotation rotor = factory.getFRotation(ref, angle);
        ext(rotor.rotate());

        return this;
    }

    @Override
    public double getDistance(FPoint ref) {

        return Math.sqrt(getDistanceP2(ref));
    }

    @Override
    public double getDistanceP2(FPoint ref) {
        double dimX = ref.getX() - getX();
        double dimY = ref.getY() - getY();
        double dimZ = ref.getZ() - getZ();

        return (dimX * dimX) + (dimY * dimY) + (dimZ * dimZ);
    }

    @Override
    public FPoint setDistance(FPoint ref, double distance) throws IllegalStateException {

        if (equals(ref)) {
            throw new IllegalStateException("FPoints must not be on the same position");
        }

        return sub(ref).setLength(distance).add(ref);
    }

    @Override
    public double getDotProduct(FPoint ref) {
        double dProd, dimX, dimY, dimZ;

        dimX = getX() * ref.getX();
        dimY = getY() * ref.getY();
        dimZ = getZ() * ref.getZ();
        dProd = dimX + dimY + dimZ;

        return dProd;
    }

    @Override
    public FPoint setCrossProduct(FPoint ref) {
        double dimX, dimY, dimZ;

        dimX = (getY() * ref.getZ()) - (getZ() * ref.getY());
        dimY = (getZ() * ref.getX()) - (getX() * ref.getZ());
        dimZ = (getX() * ref.getY()) - (getY() * ref.getX());
        set(dimX, dimY, dimZ);

        return this;
    }

    @Override
    public double getLength() {

        return Math.sqrt(getLengthP2());
    }

    @Override
    public double getLengthP2() {

        return (getX() * getX()) + (getY() * getY()) + (getZ() * getZ());
    }

    @Override
    public FPoint setLength(double length) throws IllegalStateException {

        if (getX() == 0 && getY() == 0 && getZ() == 0) {
            throw new IllegalStateException("The origin is at the same position as the given point");
        }

        return mul(length / getLength());
    }

    @Override
    public boolean isNonDirectional() {

        return getX() == 0 && getY() == 0 && getZ() == 0;
    }

    @Override
    public boolean isZero() {

        return getX() == 0 && getY() == 0 && getZ() == 0;
    }

}
