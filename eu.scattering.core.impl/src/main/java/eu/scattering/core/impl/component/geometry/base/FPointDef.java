package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointFactory;
import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import eu.scattering.core.design.transfer.primitive.FMatrix3x3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FPointDef implements FPoint {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "point";
    private static final String JSON_VAL = "position";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final FPointFactory factorySelf;

    private double oX, oY, oZ;

    private FPointDef(FPointFactory factorySelf) {

        this.factorySelf = factorySelf;
    }

    public static FPoint create(FPointFactory factorySelf) {

        return new FPointDef(factorySelf);
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
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
    public FPoint applyStateFrom(FPos3D position) {

        return set(position.getD0(), position.getD1(), position.getD2());
    }

    @Override
    public FPoint applyStateFrom(FPoint arg) {

        return set(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint set(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        var structure = json.getJSONArray(JSON_VAL);
        var x = structure.getDouble(0);
        var y = structure.getDouble(1);
        var z = structure.getDouble(2);

        return set(x, y, z);
    }

    @Override
    public FPoint applyStateTo(FPoint arg) {

        arg.applyStateFrom(this);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(double x, double y, double z) {

        return getX() == x && getY() == y && getZ() == z;
    }

    @Override
    public boolean isExact(FPoint arg) {

        if (this == arg) {
            return true;
        }

        return isExact(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public boolean isExact(FPos3D arg) {

        return isExact(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public boolean isExact(Geometry arg) {

        if (arg instanceof FPoint) {
            return isExact((FPoint) arg);
        }

        return false;
    }

    @Override
    public boolean isSimilar(double x, double y, double z) {
        double distanceX = Math.abs(getX() - x);
        double distanceY = Math.abs(getY() - y);
        double distanceZ = Math.abs(getZ() - z);

        return distanceX < EPSILON && distanceY < EPSILON && distanceZ < EPSILON;
    }

    @Override
    public boolean isSimilar(FPoint arg) {

        if (this == arg) {
            return true;
        }

        return isSimilar(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public boolean isSimilar(FPos3D arg) {

        return isSimilar(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public boolean isSimilar(Geometry arg) {

        if (arg instanceof FPoint) {
            return isSimilar((FPoint) arg);
        }

        return false;
    }

    @Override
    public FPoint self() {

        return this;
    }

    @Override
    public FPoint copy() {

        return supplyFPoint().applyStateFrom(this);
    }

    @Override
    public Geometry copyGeometry() {

        return copy();
    }

    @Override
    public FPos3D toFPos3D() {

        return factoryExt.getFPos3D(getX(), getY(), getZ());
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
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------


    @Override
    public FPoint add(double x, double y, double z) {

        return addXYZ(x, y, z);
    }

    @Override
    public FPoint add(FPoint arg) {

        return addXYZ(arg);
    }

    @Override
    public FPoint add(FPos3D arg) {

        return addXYZ(arg);
    }

    @Override
    public FPoint sub(double x, double y, double z) {

        return subXYZ(x, y, z);
    }

    @Override
    public FPoint sub(FPoint arg) {

        return subXYZ(arg);
    }

    @Override
    public FPoint sub(FPos3D arg) {

        return subXYZ(arg);
    }

    @Override
    public FPoint addXYZ(double x, double y, double z) {

        return addX(x).addY(y).addZ(z);
    }

    @Override
    public FPoint addXYZ(FPoint arg) {

        return this.addXYZ(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint addXYZ(FPos3D arg) {

        return this.addXYZ(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPoint addFactor(double factor) {

        return this.addXYZ(factor, factor, factor);
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
    public FPoint subXYZ(double x, double y, double z) {

        return subX(x).subY(y).subZ(z);
    }

    @Override
    public FPoint subXYZ(FPoint arg) {

        return this.subXYZ(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint subXYZ(FPos3D arg) {

        return this.subXYZ(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPoint subFactor(double factor) {

        return this.subXYZ(factor, factor, factor);
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
    public FPoint mulXYZ(double x, double y, double z) {

        return mulX(x).mulY(y).mulZ(z);
    }

    @Override
    public FPoint mulXYZ(FPoint arg) {

        return this.mulXYZ(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint mulXYZ(FPos3D arg) {

        return this.mulXYZ(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPoint mulFactor(double factor) {

        return this.mulXYZ(factor, factor, factor);
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
    public FPoint divXYZ(double x, double y, double z) {

        return divX(x).divY(y).divZ(z);
    }

    @Override
    public FPoint divXYZ(FPoint arg) {

        return this.divXYZ(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint divXYZ(FPos3D arg) {

        return this.divXYZ(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPoint divFactor(double factor) {

        return this.divXYZ(factor, factor, factor);
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

    @Override
    public FPoint mul(FMatrix3x3D arg) {
        double opX = (arg.get0x0() * getX()) + (arg.get0x1() * getY()) + (arg.get0x2() * getZ());
        double opY = (arg.get1x0() * getX()) + (arg.get1x1() * getY()) + (arg.get1x2() * getZ());
        double opZ = (arg.get2x0() * getX()) + (arg.get2x1() * getY()) + (arg.get2x2() * getZ());

        return set(opX, opY, opZ);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public List<FPoint> toFPoints() {
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
    public double getDistanceP2(FPoint arg) {

        return getDistanceP2(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public double getDistanceP2(FPos3D arg) {

        return getDistanceP2(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public double getDistance(double x, double y, double z) {

        return Math.sqrt(getDistanceP2(x, y, z));
    }

    @Override
    public double getDistance(FPoint arg) {

        return getDistance(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public double getDistance(FPos3D arg) {

        return getDistance(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPoint setDistance(double x, double y, double z, double distance) {

        if (isExact(x, y, z)) {
            throw new IllegalStateException("FPoints must not be on the same position");
        }

        return this.subXYZ(x, y, z).setMagnitude(distance).addXYZ(x, y, z);
    }

    @Override
    public FPoint setDistance(FPoint arg, double distance) {

        return setDistance(arg.getX(), arg.getY(), arg.getZ(), distance);
    }

    @Override
    public FPoint setDistance(FPos3D arg, double distance) {

        return setDistance(arg.getD0(), arg.getD1(), arg.getD2(), distance);
    }

    @Override
    public boolean isCollinear(double x, double y, double z) {

        return isParallel(x, y, z) || isAntiParallel(x, y, z);
    }

    @Override
    public boolean isCollinear(FPoint arg) {

        return isCollinear(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public boolean isCollinear(FPos3D arg) {

        return isCollinear(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPoint setCollinear(double x, double y, double z) {

        if (getAngle(x, y, z) < Math.PI / 2) {
            return setParallel(x, y, z);
        }

        return setAntiParallel(x, y, z);
    }

    @Override
    public FPoint setCollinear(FPoint arg) {

        return setCollinear(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint setCollinear(FPos3D arg) {

        return setCollinear(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public boolean isParallel(double x, double y, double z) {

        if (isNearZero()) {
            throw new IllegalStateException("The direction of the FPoint is not defined");
        }

        if (isNearZero(x, y, z)) {
            throw new IllegalArgumentException("The direction of the argument FPoint is not defined");
        }

        double ref = x / getX();

        if (Math.abs(ref - (y / getY())) > EPSILON || Math.abs(ref - (z / getZ())) > EPSILON) {
            return false;
        }

        return ref > 0;
    }

    @Override
    public boolean isParallel(FPoint arg) {

        return isParallel(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public boolean isParallel(FPos3D arg) {

        return isParallel(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPoint setParallel(double x, double y, double z) {

        if (isNearZero()) {
            throw new IllegalStateException("The direction of the FPoint is not defined");
        }

        if (isNearZero(x, y, z)) {
            throw new IllegalArgumentException("The direction of the argument FPoint is not defined");
        }

        double memoMagnitude = getMagnitude();

        set(x, y, z);
        setMagnitude(memoMagnitude);

        return this;
    }

    @Override
    public FPoint setParallel(FPoint arg) {

        return setParallel(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint setParallel(FPos3D arg) {

        return setParallel(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public boolean isAntiParallel(double x, double y, double z) {

        if (isNearZero()) {
            throw new IllegalStateException("The direction of the FPoint is not defined");
        }

        if (isNearZero(x, y, z)) {
            throw new IllegalArgumentException("The direction of the argument FPoint is not defined");
        }

        double ref = x / getX();

        if (Math.abs(ref - (y / getY())) > EPSILON || Math.abs(ref - (z / getZ())) > EPSILON) {
            return false;
        }

        return ref < 0;
    }

    @Override
    public boolean isAntiParallel(FPoint arg) {

        return isAntiParallel(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public boolean isAntiParallel(FPos3D arg) {

        return isAntiParallel(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPoint setAntiParallel(double x, double y, double z) {

        if (isNearZero()) {
            throw new IllegalStateException("The direction of the FPoint is not defined");
        }

        if (isNearZero(x, y, z)) {
            throw new IllegalArgumentException("The direction of the argument FPoint is not defined");
        }

        double memoMagnitude = getMagnitude();

        set(-x, -y, -z);
        setMagnitude(memoMagnitude);

        return this;
    }

    @Override
    public FPoint setAntiParallel(FPoint arg) {

        return setAntiParallel(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint setAntiParallel(FPos3D arg) {

        return setAntiParallel(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public boolean isOrthogonal(double x, double y, double z) {

        if (isNearZero()) {
            throw new IllegalStateException("The direction of the FPoint is not defined");
        }

        if (isNearZero(x, y, z)) {
            throw new IllegalArgumentException("The direction of the argument FPoint is not defined");
        }

        boolean dotProduct = Math.abs(getDotProduct(x, y, z)) < EPSILON;
        boolean angle = Math.abs((Math.PI * 0.5) - getAngle(x, y, z)) < EPSILON;

        return  dotProduct || angle;
    }

    @Override
    public boolean isOrthogonal(FPoint arg) {

        return isOrthogonal(
                arg.getX(), arg.getY(), arg.getZ()
        );
    }

    @Override
    public boolean isOrthogonal(FPos3D arg) {

        return isOrthogonal(
                arg.getD0(), arg.getD1(), arg.getD2()
        );
    }

    @Override
    public FPoint setOrthogonal(double x, double y, double z) {

        if (isParallel(x, y, z)) {
            throw new IllegalStateException("FPoints are parallel");
        }

        if (isAntiParallel(x, y, z)) {
            throw new IllegalStateException("FPoints are anti-parallel");
        }

        double memoMag = getMagnitude();

        setCrossProduct(x, y, z);
        setCrossProduct(-x, -y, -z);
        setMagnitude(memoMag);

        return this;
    }

    @Override
    public FPoint setOrthogonal(FPoint arg) {

        return setOrthogonal(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint setOrthogonal(FPos3D arg) {

        return setOrthogonal(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public double getMagnitudeP2() {

        return (getX() * getX()) + (getY() * getY()) + (getZ() * getZ());
    }

    @Override
    public double getMagnitude() {

        return Math.sqrt(getMagnitudeP2());
    }

    @Override
    public FPoint setMagnitude(double magnitude) {

        if (isNearZero()) {
            throw new IllegalStateException("The vector is non-directional (the position is too close to zero)");
        }

        return this.mulFactor(magnitude / getMagnitude());
    }

    @Override
    public FPoint normalize() {

        return setMagnitude(1);
    }

    @Override
    public FPoint reflectThroughCenter() {

        return set(-getX(), -getY(), -getZ());
    }

    @Override
    public FPoint reflect(double x, double y, double z) {

        return this.subXYZ(x, y, z).reflectThroughCenter().addXYZ(x, y, z);
    }

    @Override
    public FPoint reflect(FPoint arg) {

        return reflect(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint reflect(FPos3D arg) {

        return reflect(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public double getAngle(double x, double y, double z) {

        if (isNearZero()) {
            throw new IllegalStateException("The input vector is non-directional");
        }

        if (Math.abs(x) < EPSILON && Math.abs(y) < EPSILON && Math.abs(z) < EPSILON) {
            throw new IllegalArgumentException("The reference vector is non-directional");
        }

        if (isSimilar(x, y, z)) {
            throw new IllegalStateException("The vectors are similar");
        }

        double dProd = getDotProduct(x, y, z);
        double magAB = getMagnitude() * Math.sqrt((x * x) + (y * y) + (z * z));
        double angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public double getAngle(FPoint arg) {

        return getAngle(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public double getAngle(FPos3D arg) {

        return getAngle(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public double getDotProduct(double x, double y, double z) {
        double dimX = getX() * x;
        double dimY = getY() * y;
        double dimZ = getZ() * z;

        return dimX + dimY + dimZ;
    }

    @Override
    public double getDotProduct(FPoint arg) {

        return getDotProduct(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public double getDotProduct(FPos3D arg) {

        return getDotProduct(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPoint setCrossProduct(double x, double y, double z) {
        double dimX = (getY() * z) - (getZ() * y);
        double dimY = (getZ() * x) - (getX() * z);
        double dimZ = (getX() * y) - (getY() * x);

        return set(dimX, dimY, dimZ);
    }

    @Override
    public FPoint setCrossProduct(FPoint arg) {

        return setCrossProduct(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FPoint setCrossProduct(FPos3D arg) {

        return setCrossProduct(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FPoint setSphericalCoordinates(double inclination, double azimuth) {
        double memoMag = getMagnitude();

        setX(Math.cos(azimuth) * Math.sin(inclination));
        setY(Math.cos(inclination));
        setZ(Math.sin(azimuth) * Math.sin(inclination));

        setMagnitude(memoMag);

        return this;
    }

    @Override
    public double getInclination() {

        return Math.acos(getY() / getMagnitude());
    }

    @Override
    public FPoint setInclination(double polar) {
        double memoMag = getMagnitude();

        setSphericalCoordinates(polar, getAzimuth());
        setMagnitude(memoMag);

        return this;
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
        double memoMag = getMagnitude();

        setSphericalCoordinates(getInclination(), azimuthal);
        setMagnitude(memoMag);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPoint apply(Consumer<FPoint> action) {

        action.accept(this);

        return this;
    }

    @Override
    public double toDouble(Function<FPoint, Double> action) {

        return action.apply(this);
    }

    @Override
    public boolean toBoolean(Function<FPoint, Boolean> action) {

        return action.apply(this);
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isNearZero(double x, double y, double z) {
        boolean posX = Math.abs(x) < EPSILON;
        boolean posY = Math.abs(y) < EPSILON;
        boolean posZ = Math.abs(z) < EPSILON;

        return posX && posY && posZ;
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint supplyFPoint() {

        return factorySelf.getFPoint();
    }
}