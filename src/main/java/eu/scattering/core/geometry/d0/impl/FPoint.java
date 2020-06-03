package eu.scattering.core.geometry.d0.impl;

import eu.scattering.core.Configuration;
import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.CoreObject;
import eu.scattering.core.geometry.d0.IFPoint;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FPoint extends CoreObject implements IFPoint {

    private final double[] origin = { 0.0, 0.0, 0.0 };

    private FPoint() { }

    public static FPoint create() {
        return new FPoint();
    }

//--------------------------------------------------

    @Override
    public boolean isExact(IFPoint fPoint) {

        if (this == fPoint) {
            return true;
        }

        return origin[0] == fPoint.getX() && origin[1] == fPoint.getY() && origin[2] == fPoint.getZ();
    }

    @Override
    public boolean isSimilar(IFPoint fPoint) {
        double distanceX = Math.abs(origin[0] - fPoint.getX());
        double distanceY = Math.abs(origin[1] - fPoint.getY());
        double distanceZ = Math.abs(origin[2] - fPoint.getZ());

        return distanceX < Configuration.jitter && distanceY < Configuration.jitter && distanceZ < Configuration.jitter;
    }

    @Override
    public int getHashCode() {
        int hashCode = 7;

        hashCode = 31 * hashCode + (int) (origin[0] * 100);
        hashCode = 31 * hashCode + (int) (origin[1] * 100);
        hashCode = 31 * hashCode + (int) (origin[2] * 100);

        return hashCode;
    }

    @Override
    public String exportToJSON() {
        JSONObject json = new JSONObject();
        json.put("origin", origin);

        return json.toString();
    }

    @Override
    public FPoint importFromJSON(String json) {
        JSONArray array = new JSONObject(json).getJSONArray("origin");

        origin[0] = array.getDouble(0);
        origin[1] = array.getDouble(1);
        origin[2] = array.getDouble(2);

        return this;
    }

    @Override
    public FPoint copy() {
        FPoint point = new FPoint();
        point.set(this);

        return point;
    }

//--------------------------------------------------

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
        return "FPoint [" + origin[0] + "," + origin[1] +"," + origin[2] + "]";
    }

    @Override
    public FPoint clone() {
        return copy();
    }

//--------------------------------------------------

    @Override
    public List<IFPoint> getIFPoints() {
        List<IFPoint> fPointList = new ArrayList<>();
        fPointList.add(this);

        return fPointList;
    }

    @Override
    public FPoint add(IFPoint fPoint) {
        origin[0] += fPoint.getX();
        origin[1] += fPoint.getY();
        origin[2] += fPoint.getZ();

        return this;
    }

    @Override
    public FPoint add(double x, double y, double z) {
        origin[0] += x;
        origin[1] += y;
        origin[2] += z;

        return this;
    }

    @Override
    public FPoint addX(double x) {
        origin[0] += x;

        return this;
    }

    @Override
    public FPoint addY(double y) {
        origin[1] += y;

        return this;
    }

    @Override
    public FPoint addZ(double z) {
        origin[2] += z;

        return this;
    }

    @Override
    public FPoint sub(IFPoint fPoint) {
        return sub(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint sub(double x, double y, double z) {
        origin[0] -= x;
        origin[1] -= y;
        origin[2] -= z;

        return this;
    }

    @Override
    public FPoint subX(double x) {
        origin[0] -= x;

        return this;
    }

    @Override
    public FPoint subY(double y) {
        origin[1] -= y;

        return this;
    }

    @Override
    public FPoint subZ(double z) {
        origin[2] -= z;

        return this;
    }

    @Override
    public FPoint mul(IFPoint fPoint) {
        return mul(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint mul(double x, double y, double z) {
        origin[0] *= x;
        origin[1] *= y;
        origin[2] *= z;

        return this;
    }

    @Override
    public FPoint mulX(double x) {
        origin[0] *= x;

        return this;
    }

    @Override
    public FPoint mulY(double y) {
        origin[1] *= y;

        return this;
    }

    @Override
    public FPoint mulZ(double z) {
        origin[2] *= z;

        return this;
    }

    @Override
    public FPoint div(IFPoint fPoint) {
        return div(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint div(double x, double y, double z) {

        if (x == 0 || y == 0 || z == 0) {
            throw new ArithmeticException("Division by zero");
        }

        origin[0] /= x;
        origin[1] /= y;
        origin[2] /= z;

        return this;
    }

    @Override
    public FPoint divX(double x) {

        if (x == 0) {
            throw new ArithmeticException("Division by zero");
        }

        origin[0] /= x;

        return this;
    }

    @Override
    public FPoint divY(double y) {

        if (y == 0) {
            throw new ArithmeticException("Division by zero");
        }
        origin[1] /= y;

        return this;
    }

    @Override
    public FPoint divZ(double z) {

        if (z == 0) {
            throw new ArithmeticException("Division by zero");
        }

        origin[2] /= z;

        return this;
    }

    @Override
    public FPoint scale(double scaleFactor) {
        return mul(scaleFactor, scaleFactor, scaleFactor);
    }

//--------------------------------------------------

    @Override
    public FPoint set(IFPoint fPoint) {
        return set(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint set(double x, double y, double z) {
        origin[0] = x;
        origin[1] = y;
        origin[2] = z;

        return this;
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

//--------------------------------------------------

    @Override
    public IFPoint setSphericalCoordinates(double inclination, double azimuth) {
        origin[2] = Math.sin(azimuth) * Math.sin(inclination);
        origin[1] = Math.cos(inclination);
        origin[0] = Math.cos(azimuth) * Math.sin(inclination);

        return this;
    }

    @Override
    public IFPoint setRandom(IFPoint... exclude) {

        mainLoop:
        while (true) {
            double x1 = 0, x2 = 0, f = 10;

            while (f >= 1) {
                x1 = 2 * ThreadLocalRandom.current().nextDouble() - 1;
                x2 = 2 * ThreadLocalRandom.current().nextDouble() - 1;
                f = x1 * x1 + x2 * x2;
            }

            double candidateX = 2 * x1 * Math.sqrt(1 - f);
            double candidateY = 2 * x2 * Math.sqrt(1 - f);
            double candidateZ = 1 - 2 * f;

            for (IFPoint singularity : exclude) {
                if (isSimilar(singularity)) {
                    continue mainLoop;
                }
            }

            origin[0] = candidateX;
            origin[1] = candidateY;
            origin[2] = candidateZ;

            return this;
        }

    }

    @Override
    public IFPoint normalize() {
        return setRadius(1);
    }

    @Override
    public double getInclination() {
        return Math.acos(origin[1] / Math.sqrt((origin[0] * origin[0]) + (origin[1] * origin[1]) + (origin[2] * origin[2])));
    }

    @Override
    public IFPoint setInclination(double polar) {
        double radius = getRadius();

        return setSphericalCoordinates(polar, getAzimuth()).setRadius(radius);
    }

    @Override
    public double getAzimuth() {
        double r2 = Math.sqrt((origin[0] * origin[0]) + (origin[2] * origin[2]));
        double azimuthal;

        if (origin[2] >= 0) {
            azimuthal = Math.acos(origin[0] / r2);
        } else {
            azimuthal = -Math.acos(origin[0] / r2);
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
    public double getRadius() {
        return Math.sqrt((origin[0] * origin[0]) + (origin[1] * origin[1]) + (origin[2] * origin[2]));
    }

    @Override
    public FPoint setRadius(double radius) throws SamePositionException {

        if (radius <= 0) {
            throw new IllegalArgumentException("The requested radius must be positive");
        }

        if (origin[0] == 0 && origin[1] == 0 && origin[2] == 0) {
            throw new SamePositionException("The origin is at the same position as the given point");
        }

        return scale(radius / getRadius());
    }

    @Override
    public FPoint reflect() {
        return set(-origin[0], -origin[1], -origin[2]);
    }
}
