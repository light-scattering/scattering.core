package eu.scattering.core.geometry.base.point.impl;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.CoreObject;
import eu.scattering.core.geometry.base.point.IFPoint;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static eu.scattering.core.Configuration.*;

public class FPoint extends CoreObject implements IFPoint {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double[] origin = { 0.0, 0.0, 0.0 };

    private FPoint() { }

    public static FPoint create() {
        return new FPoint();
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
    public FPoint set(IFPoint fPoint) {
        return set(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint set(double x, double y, double z) {
        return setX(x).setY(y).setZ(z);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(IFPoint fPoint) {

        if (this == fPoint) {
            return true;
        }

        return getX() == fPoint.getX() && getY() == fPoint.getY() && getZ() == fPoint.getZ();
    }

    @Override
    public boolean isSimilar(IFPoint fPoint) {
        double distanceX = Math.abs(getX() - fPoint.getX());
        double distanceY = Math.abs(getY() - fPoint.getY());
        double distanceZ = Math.abs(getZ() - fPoint.getZ());

        return distanceX < jitter && distanceY < jitter && distanceZ < jitter;
    }

    @Override
    public int getHashCode() {
        int hashCode = 7;

        hashCode = 31 * hashCode + (int) (getX() * 100);
        hashCode = 31 * hashCode + (int) (getY() * 100);
        hashCode = 31 * hashCode + (int) (getZ() * 100);

        return hashCode;
    }

    @Override
    public String exportToJSON() {
        JSONObject json = new JSONObject();
        json.put("origin", new double[]{getX(), getY(), getZ()});

        return json.toString();
    }

    @Override
    public FPoint importFromJSON(String json) {
        JSONArray structure = new JSONObject(json).getJSONArray("origin");

        setX(structure.getDouble(0));
        setY(structure.getDouble(1));
        setZ(structure.getDouble(2));

        return this;
    }

    @Override
    public FPoint copy() {
        FPoint point = new FPoint();
        point.set(this);

        return point;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof IFPoint)) {
            return false;
        }

        return isExact((IFPoint) object);
    }

    @Override
    public int hashCode() {
        return getHashCode();
    }

    @Override
    public String toString() {
        return "FPoint [" + getX() + "," + getY() +"," + getZ() + "]";
    }

    @Override
    public FPoint clone() {
        return copy();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public List<IFPoint> getIFPoints() {
        List<IFPoint> fPointList = new ArrayList<>();
        fPointList.add(this);

        return fPointList;
    }

    @Override
    public FPoint add(IFPoint fPoint) {
        return add(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint add(double x, double y, double z) {
        return addX(x).addY(y).addZ(z);
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
    public FPoint sub(IFPoint fPoint) {
        return sub(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint sub(double x, double y, double z) {
        return subX(x).subY(y).subZ(z);
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
    public FPoint mul(IFPoint fPoint) {
        return mul(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint mul(double x, double y, double z) {
        return mulX(x).mulY(y).mulZ(z);
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
    public FPoint div(IFPoint fPoint) {
        return div(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint div(double x, double y, double z) {
        return divX(x).divY(y).divZ(z);
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

    @Override
    public FPoint scale(double scaleFactor) {
        return mul(scaleFactor, scaleFactor, scaleFactor);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPoint setSphericalCoordinates(double inclination, double azimuth) {
        double radius = getRadius();

        setX(Math.cos(azimuth) * Math.sin(inclination));
        setY(Math.cos(inclination));
        setZ(Math.sin(azimuth) * Math.sin(inclination));

        return setRadius(radius);
    }

    @Override
    public FPoint setRandom(IFPoint... exclude) {
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
    public FPoint normalize() {
        return setRadius(1);
    }

    @Override
    public FPoint reflect() {
        return set(-getX(), -getY(), -getZ());
    }

    @Override
    public double getInclination() {
        return Math.acos(getY() / Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ())));
    }

    @Override
    public FPoint setInclination(double polar) {
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
    public FPoint setAzimuth(double azimuthal) {
        double radius = getRadius();

        return setSphericalCoordinates(getInclination(), azimuthal).setRadius(radius);
    }

    @Override
    public double getRadius() {
        return Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));
    }

    @Override
    public FPoint setRadius(double radius) throws SamePositionException {

        if (radius <= 0) {
            throw new IllegalArgumentException("The requested radius must be positive");
        }

        if (getX() == 0 && getY() == 0 && getZ() == 0) {
            throw new SamePositionException("The origin is at the same position as the given point");
        }

        return scale(radius / getRadius());
    }

    @Override
    public boolean isZero() {
        return getX() == 0 && getY() == 0 && getZ() == 0;
    }

}
