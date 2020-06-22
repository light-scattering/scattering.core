package eu.scattering.core.geometry.base.point.impl;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.PresetGeometry;
import eu.scattering.core.geometry.base.point.IFPoint;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static eu.scattering.core.Configuration.*;

public class FPoint extends PresetGeometry<IFPoint> implements IFPoint {

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

        json.append("origin", getX());
        json.append("origin", getY());
        json.append("origin", getZ());

        return json;
    }

    @Override
    public IFPoint importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("origin");

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
        double radius = getRadius();

        setX(Math.cos(azimuth) * Math.sin(inclination));
        setY(Math.cos(inclination));
        setZ(Math.sin(azimuth) * Math.sin(inclination));

        return setRadius(radius);
    }

    @Override
    public IFPoint setRandom(IFPoint... exclude) {
        double radius = getRadius();

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

            return setRadius(radius);
        }

    }

    @Override
    public IFPoint normalize() {
        return setRadius(1);
    }

    @Override
    public IFPoint reflect() {
        return set(-getX(), -getY(), -getZ());
    }

    @Override
    public double getInclination() {
        return Math.acos(getY() / Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ())));
    }

    @Override
    public IFPoint setInclination(double polar) {
        double radius = getRadius();

        return setSphericalCoordinates(polar, getAzimuth()).setRadius(radius);
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
        double radius = getRadius();

        return setSphericalCoordinates(getInclination(), azimuthal).setRadius(radius);
    }

    @Override
    public double getAngle(IFPoint fPoint) {
        double angle, dProd, magAB;

        dProd = dProd(fPoint);
        magAB = getRadius() * fPoint.getRadius();
        angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public double getDistance(IFPoint fPoint) {
        double dimX = fPoint.getX() - getX();
        double dimY = fPoint.getY() - getY();
        double dimZ = fPoint.getZ() - getZ();

        return Math.sqrt((dimX * dimX) + (dimY * dimY) + (dimZ * dimZ));
    }

    @Override
    public IFPoint setDistance(IFPoint fPoint, double distance) throws SamePositionException, IllegalArgumentException {

        if (distance < 0) {
            throw new IllegalArgumentException("The distance between IFPoints cannot be lower than zero");
        }

        if (this.equals(fPoint)) {
            throw new SamePositionException("IFPoints must not be on the same position");
        }

        return this.sub(fPoint).setRadius(distance).add(fPoint);
    }

    @Override
    public double dProd(IFPoint fPoint) {
        double dProd, dimX, dimY, dimZ;

        dimX = getX() * fPoint.getX();
        dimY = getY() * fPoint.getY();
        dimZ = getZ() * fPoint.getZ();
        dProd = dimX + dimY + dimZ;

        return dProd;
    }

    @Override
    public IFPoint cProd(IFPoint fPoint) {
        double dimX, dimY, dimZ;

        dimX = (getY() * fPoint.getZ()) - (getZ() * fPoint.getY());
        dimY = (getZ() * fPoint.getX()) - (getX() * fPoint.getZ());
        dimZ = (getX() * fPoint.getY()) - (getY() * fPoint.getX());
        set(dimX, dimY, dimZ);

        return this;
    }

    @Override
    public double getRadius() {
        return Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));
    }

    @Override
    public IFPoint setRadius(double radius) throws SamePositionException {

        if (radius <= 0) {
            throw new IllegalArgumentException("The requested radius must be positive");
        }

        if (getX() == 0 && getY() == 0 && getZ() == 0) {
            throw new SamePositionException("The origin is at the same position as the given point");
        }

        return mul(radius / getRadius());
    }

    @Override
    public boolean isZero() {
        return getX() == 0 && getY() == 0 && getZ() == 0;
    }

}
