package eu.scattering.core.geometry.d0.impl;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.CoreObject;
import eu.scattering.core.geometry.d0.IFPoint;

import java.util.ArrayList;
import java.util.List;

public class FPoint extends CoreObject implements IFPoint {

    private final double[] core = { 0.0, 0.0, 0.0 };

    private FPoint() { }

    public static FPoint create() {
        return new FPoint();
    }

    @Override
    public IFPoint setSphericalCoordinates(double polar, double azimuthal, double radius) {
        core[0] = Math.cos(azimuthal) * Math.sin(polar);
        core[1] = Math.sin(azimuthal) * Math.sin(polar);
        core[2] = Math.cos(polar);

        return scale(radius);
    }

    @Override
    public IFPoint randomize(double radius) {
        double x1 = 0, x2 = 0, f = 10;

        while (f >= 1) {
            x1 = 2 * Math.random() - 1;
            x2 = 2 * Math.random() - 1;
            f = x1 * x1 + x2 * x2;
        }

        core[0]= 2 * x1 * Math.sqrt(1 - f);
        core[1] = 2 * x2 * Math.sqrt(1 - f);
        core[2] = 1 - 2 * f;

        return scale(radius);
    }

    @Override
    public IFPoint normalize() {
        return null;
    }

    @Override
    public double getX() {
        return core[0];
    }

    @Override
    public FPoint setX(double x) {
        core[0] = x;

        return this;
    }

    @Override
    public double getY() {
        return core[1];
    }

    @Override
    public FPoint setY(double y) {
        core[1] = y;

        return this;
    }

    @Override
    public double getZ() {
        return core[2];
    }

    @Override
    public FPoint setZ(double z) {
        core[2] = z;

        return this;
    }

    @Override
    public FPoint set(IFPoint fPoint) {
        return set(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint set(double x, double y, double z) {
        core[0] = x;
        core[1] = y;
        core[2] = z;

        return this;
    }

    @Override
    public FPoint add(IFPoint fPoint) {
        core[0] += fPoint.getX();
        core[1] += fPoint.getY();
        core[2] += fPoint.getZ();

        return this;
    }

    @Override
    public FPoint add(double x, double y, double z) {
        core[0] += x;
        core[1] += y;
        core[2] += z;

        return this;
    }

    @Override
    public FPoint addX(double x) {
        core[0] += x;

        return this;
    }

    @Override
    public FPoint addY(double y) {
        core[1] += y;

        return this;
    }

    @Override
    public FPoint addZ(double z) {
        core[2] += z;

        return this;
    }

    @Override
    public FPoint sub(IFPoint fPoint) {
        return sub(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint sub(double x, double y, double z) {
        core[0] -= x;
        core[1] -= y;
        core[2] -= z;

        return this;
    }

    @Override
    public FPoint subX(double x) {
        core[0] -= x;

        return this;
    }

    @Override
    public FPoint subY(double y) {
        core[1] -= y;

        return this;
    }

    @Override
    public FPoint subZ(double z) {
        core[2] -= z;

        return this;
    }

    @Override
    public FPoint mul(IFPoint fPoint) {
        return mul(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public FPoint mul(double x, double y, double z) {
        core[0] *= x;
        core[1] *= y;
        core[2] *= z;

        return this;
    }

    @Override
    public FPoint mulX(double x) {
        core[0] *= x;

        return this;
    }

    @Override
    public FPoint mulY(double y) {
        core[1] *= y;

        return this;
    }

    @Override
    public FPoint mulZ(double z) {
        core[2] *= z;

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

        core[0] /= x;
        core[1] /= y;
        core[2] /= z;

        return this;
    }

    @Override
    public FPoint divX(double x) {

        if (x == 0) {
            throw new ArithmeticException("Division by zero");
        }

        core[0] /= x;

        return this;
    }

    @Override
    public FPoint divY(double y) {

        if (y == 0) {
            throw new ArithmeticException("Division by zero");
        }
        core[1] /= y;

        return this;
    }

    @Override
    public FPoint divZ(double z) {

        if (z == 0) {
            throw new ArithmeticException("Division by zero");
        }

        core[2] /= z;

        return this;
    }

    @Override
    public FPoint scale(double scaleFactor) {
        return mul(scaleFactor, scaleFactor, scaleFactor);
    }

    @Override
    public List<IFPoint> getIFPoints() {
        List<IFPoint> fPointList = new ArrayList<>();
        fPointList.add(this);

        return fPointList;
    }

    @Override
    public boolean isExact(IFPoint element) {
        return false;
    }

    @Override
    public boolean isSimilar(IFPoint element) {
        return false;
    }

    @Override
    public int getHashCode() {
        return 0;
    }

    @Override
    public String exportToJSON() {
        return null;
    }

    @Override
    public IFPoint importFromJSON() {
        return null;
    }

    @Override
    public FPoint copy() {
        return clone();
    }

    @Override
    public double getPolarAngle() {
        return Math.acos(core[2] / Math.sqrt((core[0] * core[0]) + (core[1] * core[1]) + (core[2] * core[2])));
    }

    @Override
    public IFPoint setPolarAngle(double polar) {
        return setSphericalCoordinates(polar, getAzimuthalAngle(), getRadius());
    }

    @Override
    public double getAzimuthalAngle() {
        double r2 = Math.sqrt((core[0] * core[0]) + (core[1] * core[1]));
        double azimuthal;

        if (core[1] >= 0) {
            azimuthal = Math.acos(core[0] / r2);
        } else {
            azimuthal = -Math.acos(core[0] / r2);
        }

        if (Double.isNaN(azimuthal)) {
            return 0;
        } else {
            return azimuthal;
        }
    }

    @Override
    public IFPoint setAzimuthalAngle(double azimuthal) {
        return setSphericalCoordinates(getPolarAngle(), azimuthal, getRadius());
    }

    @Override
    public double getRadius() {
        return Math.sqrt((core[0] * core[0]) + (core[1] * core[1]) + (core[2] * core[2]));
    }

    @Override
    public FPoint setRadius(double distance) throws SamePositionException {

        if (core[0] == 0 && core[1] == 0 && core[2] == 0) {
            throw new SamePositionException("The origin is at the same position as the given point");
        }

        return scale(distance / getRadius());
    }

    @Override
    public FPoint reflect() {
        return set(-core[0], -core[1], -core[2]);
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public FPoint clone() {
        FPoint point = new FPoint();
        point.set(this);

        return point;
    }

}
