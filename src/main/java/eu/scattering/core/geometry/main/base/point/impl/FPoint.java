package eu.scattering.core.geometry.main.base.point.impl;

import eu.scattering.core.exception.DirectionException;
import eu.scattering.core.exception.PositionException;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.PresetBase;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static eu.scattering.core.Configuration.*;

public class FPoint extends PresetBase<IFPoint> implements IFPoint {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double[] origin = { 0.0, 0.0, 0.0 };

    private FPoint() { }

    public static IFPoint create() {

        return new FPoint();
    }

    @Override
    public double getX() {

        return origin[0];
    }

    @Override
    public IFPoint setX(double x) {
        origin[0] = x;

        return this;
    }

    @Override
    public double getY() {
        return origin[1];
    }

    @Override
    public IFPoint setY(double y) {
        origin[1] = y;

        return this;
    }

    @Override
    public double getZ() {
        return origin[2];
    }

    @Override
    public IFPoint setZ(double z) {
        origin[2] = z;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public IFPoint set(IFPoint fPoint) {

        return set(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public IFPoint set(double x, double y, double z) {

        return setX(x).setY(y).setZ(z);
    }

    @Override
    public IFPoint swap(IFPoint element) {
        IFPoint store = copy();

        set(element);
        element.set(store);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(IFPoint fPoint) {

        if (fPoint == null) {
            throw new NullPointerException("The reference IFPoint cannot be null");
        }

        if (this == fPoint) {
            return true;
        }

        return getX() == fPoint.getX() && getY() == fPoint.getY() && getZ() == fPoint.getZ();
    }

    @Override
    public boolean isSimilar(IFPoint fPoint) {

        if (fPoint == null) {
            throw new NullPointerException("The reference IFPoint cannot be null");
        }

        if (this == fPoint) {
            return true;
        }

        double distanceX = Math.abs(getX() - fPoint.getX());
        double distanceY = Math.abs(getY() - fPoint.getY());
        double distanceZ = Math.abs(getZ() - fPoint.getZ());

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
    public IFPoint importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("point");

        setX(structure.getDouble(0));
        setY(structure.getDouble(1));
        setZ(structure.getDouble(2));

        return this;
    }

    @Override
    public IFPoint copy() {

        return new FPoint().set(this);
    }

    @Override
    public IFPoint self() {

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

    @Override
    public boolean equals(Object object) {

        if (object instanceof IFPoint) {
            return isExact((IFPoint) object);
        }

        return false;
    }

    @Override
    public Object clone() {
        return copy();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public List<IFPoint> disassemble() {
        List<IFPoint> fPointList = new ArrayList<>();
        fPointList.add(this);

        return fPointList;
    }

    @Override
    public IFPoint add(IFPoint fPoint) {

        return add(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public IFPoint add(double x, double y, double z) {

        return addX(x).addY(y).addZ(z);
    }

    @Override
    public IFPoint add(double factor) {

        return add(factor, factor, factor);
    }

    @Override
    public IFPoint addX(double x) {

        return setX(getX() + x);
    }

    @Override
    public IFPoint addY(double y) {

        return setY(getY() + y);
    }

    @Override
    public IFPoint addZ(double z) {

        return setZ(getZ() + z);
    }

    @Override
    public IFPoint sub(IFPoint fPoint) {

        return sub(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public IFPoint sub(double x, double y, double z) {

        return subX(x).subY(y).subZ(z);
    }

    @Override
    public IFPoint sub(double factor) {

        return sub(factor, factor, factor);
    }

    @Override
    public IFPoint subX(double x) {

        return setX(getX() - x);
    }

    @Override
    public IFPoint subY(double y) {

        return setY(getY() - y);
    }

    @Override
    public IFPoint subZ(double z) {

        return setZ(getZ() - z);
    }

    @Override
    public IFPoint mul(IFPoint fPoint) {

        return mul(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public IFPoint mul(double x, double y, double z) {

        return mulX(x).mulY(y).mulZ(z);
    }

    @Override
    public IFPoint mul(double factor) {

        return mul(factor, factor, factor);
    }

    @Override
    public IFPoint mulX(double x) {

        return setX(getX() * x);
    }

    @Override
    public IFPoint mulY(double y) {

        return setY(getY() * y);
    }

    @Override
    public IFPoint mulZ(double z) {

        return setZ(getZ() * z);
    }

    @Override
    public IFPoint div(IFPoint fPoint) {

        return div(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public IFPoint div(double x, double y, double z) {

        return divX(x).divY(y).divZ(z);
    }

    @Override
    public IFPoint div(double factor) {

        return div(factor, factor, factor);
    }

    @Override
    public IFPoint divX(double x) {

        if (x == 0) {
            throw new ArithmeticException("Division by zero");
        }

        return setX(getX() / x);
    }

    @Override
    public IFPoint divY(double y) {

        if (y == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return setY(getY() / y);
    }

    @Override
    public IFPoint divZ(double z) {

        if (z == 0) {
            throw new ArithmeticException("Division by zero");
        }

        return setZ(getZ() / z);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public IFPoint setSphericalCoordinates(double inclination, double azimuth) {
        double radius = getLength();

        setX(Math.cos(azimuth) * Math.sin(inclination));
        setY(Math.cos(inclination));
        setZ(Math.sin(azimuth) * Math.sin(inclination));

        return setLength(radius);
    }

    @Override
    public IFPoint setRandomAngle(IFPoint... exclude) {
        double radius = getLength();

        IFPoint[] excludeList = new IFPoint[exclude.length];
        for (int i = 0 ; i < exclude.length ; i++ ) {
            excludeList[i] = exclude[i].copy();
        }

        mainLoop:
        while (true) {
            double x1 = 0, x2 = 0, f = 10;

            while (f >= 1) {
                x1 = 2 * ThreadLocalRandom.current().nextDouble() - 1;
                x2 = 2 * ThreadLocalRandom.current().nextDouble() - 1;
                f = x1 * x1 + x2 * x2;
            }

            setX(2 * x1 * Math.sqrt(1 - f));
            setY(2 * x2 * Math.sqrt(1 - f));
            setZ(1 - 2 * f);

            for (IFPoint singularity : excludeList) {
                if (isSimilar(singularity)) {
                    continue mainLoop;
                }
            }

            return setLength(radius);
        }

    }

    @Override
    public boolean isExact(double x, double y, double z) {

        return isExact(FactoryGeometry.getIFPoint(x, y, z));
    }

    @Override
    public boolean isSimilar(double x, double y, double z) {

        return isSimilar(FactoryGeometry.getIFPoint(x, y, z));
    }

    @Override
    public IFPoint normalize() {

        return setLength(1);
    }

    @Override
    public IFPoint reflect() {

        return set(-getX(), -getY(), -getZ());
    }

    @Override
    public IFPoint reflect(IFPoint ref) {

        return sub(ref).reflect().add(ref);
    }

    @Override
    public double getInclination() {

        return Math.acos(getY() / Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ())));
    }

    @Override
    public IFPoint setInclination(double polar) {
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
    public IFPoint setAzimuth(double azimuthal) {
        double radius = getLength();

        return setSphericalCoordinates(getInclination(), azimuthal).setLength(radius);
    }

    @Override
    public double getAngle(IFPoint ref) throws DirectionException {

        if (isZero()) {
            throw new DirectionException("The input IFPoint is zero");
        }

        if (ref.isZero()) {
            throw new DirectionException("The reference IFPoint is zero");
        }

        double angle, dProd, magAB;

        dProd = getDotProduct(ref);
        magAB = getLength() * ref.getLength();
        angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public double getDistance(IFPoint ref) {
        double dimX = ref.getX() - getX();
        double dimY = ref.getY() - getY();
        double dimZ = ref.getZ() - getZ();

        return Math.sqrt((dimX * dimX) + (dimY * dimY) + (dimZ * dimZ));
    }

    @Override
    public IFPoint setDistance(IFPoint ref, double distance) throws DirectionException {

        if (equals(ref)) {
            throw new DirectionException("IFPoints must not be on the same position");
        }

        return sub(ref).setLength(distance).add(ref);
    }

    @Override
    public double getDotProduct(IFPoint ref) {
        double dProd, dimX, dimY, dimZ;

        dimX = getX() * ref.getX();
        dimY = getY() * ref.getY();
        dimZ = getZ() * ref.getZ();
        dProd = dimX + dimY + dimZ;

        return dProd;
    }

    @Override
    public IFPoint setCrossProduct(IFPoint ref) {
        double dimX, dimY, dimZ;

        dimX = (getY() * ref.getZ()) - (getZ() * ref.getY());
        dimY = (getZ() * ref.getX()) - (getX() * ref.getZ());
        dimZ = (getX() * ref.getY()) - (getY() * ref.getX());
        set(dimX, dimY, dimZ);

        return this;
    }

    @Override
    public double getLength() {

        return Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));
    }

    @Override
    public IFPoint setLength(double length) throws DirectionException {

        if (getX() == 0 && getY() == 0 && getZ() == 0) {
            throw new DirectionException("The origin is at the same position as the given point");
        }

        return mul(length / getLength());
    }

    @Override
    public boolean isZero() {

        return getX() == 0 && getY() == 0 && getZ() == 0;
    }

}
