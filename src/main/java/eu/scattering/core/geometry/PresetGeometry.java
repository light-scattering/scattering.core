package eu.scattering.core.geometry;

import eu.scattering.core.geometry.base.point.IFPoint;

import java.util.Arrays;

import static eu.scattering.core.Configuration.debugPrintStream;

public abstract class PresetGeometry<T extends IGeometryAlgebra<T>>
        implements IGeometryBase<T>, IGeometryDebug<T>, IGeometryAlgebra<T> {

    @Override
    public abstract Object clone();

    @Override
    public abstract boolean equals(Object object);

    // -------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return exportToJSON();
    }

    @Override
    public int hashCode() {
        int hashCode = 7;

        for (IFPoint fPoint : getIFPoints()) {
            hashCode = 31 * hashCode + (int) (fPoint.getX() * 100);
            hashCode = 31 * hashCode + (int) (fPoint.getY() * 100);
            hashCode = 31 * hashCode + (int) (fPoint.getZ() * 100);
        }

        return hashCode;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public T add(IFPoint fPoint) {
        getIFPoints().forEach(e -> e.add(fPoint.getX(), fPoint.getY(), fPoint.getZ()));
        return self();
    }

    @Override
    public T add(double x, double y, double z) {
        getIFPoints().forEach(e -> e.addX(x).addY(y).addZ(z));
        return self();
    }

    @Override
    public T addX(double x) {
        getIFPoints().forEach(e -> e.setX(e.getX() + x));
        return self();
    }

    @Override
    public T addY(double y) {
        getIFPoints().forEach(e -> e.setY(e.getY() + y));
        return self();
    }

    @Override
    public T addZ(double z) {
        getIFPoints().forEach(e -> e.setZ(e.getZ() + z));
        return self();
    }

    @Override
    public T sub(IFPoint fPoint) {
        getIFPoints().forEach(e -> e.sub(fPoint.getX(), fPoint.getY(), fPoint.getZ()));
        return self();
    }

    @Override
    public T sub(double x, double y, double z) {
        getIFPoints().forEach(e -> e.subX(x).subY(y).subZ(z));
        return self();
    }

    @Override
    public T subX(double x) {
        getIFPoints().forEach(e -> e.setX(e.getX() - x));
        return self();
    }

    @Override
    public T subY(double y) {
        getIFPoints().forEach(e -> e.setY(e.getY() - y));
        return self();
    }

    @Override
    public T subZ(double z) {
        getIFPoints().forEach(e -> e.setZ(e.getZ() - z));
        return self();
    }

    @Override
    public T mul(IFPoint fPoint) {
        getIFPoints().forEach(e -> e.mul(fPoint.getX(), fPoint.getY(), fPoint.getZ()));
        return self();
    }

    @Override
    public T mul(double x, double y, double z) {
        getIFPoints().forEach(e -> e.mulX(x).mulY(y).mulZ(z));
        return self();
    }

    @Override
    public T mulX(double x) {
        getIFPoints().forEach(e -> e.setX(e.getX() * x));
        return self();
    }

    @Override
    public T mulY(double y) {
        getIFPoints().forEach(e -> e.setY(e.getY() * y));
        return self();
    }

    @Override
    public T mulZ(double z) {
        getIFPoints().forEach(e -> e.setZ(e.getZ() * z));
        return self();
    }

    @Override
    public T div(IFPoint fPoint) {
        getIFPoints().forEach(e -> e.div(fPoint.getX(), fPoint.getY(), fPoint.getZ()));
        return self();
    }

    @Override
    public T div(double x, double y, double z) {
        getIFPoints().forEach(e -> e.divX(x).divY(y).divZ(z));
        return self();
    }

    @Override
    public T divX(double x) {
        getIFPoints().forEach(e -> {

            if (x == 0) {
                throw new ArithmeticException("Division by zero");
            }

            e.setX(e.getX() / x);
        });

        return self();
    }

    @Override
    public T divY(double y) {
        getIFPoints().forEach(e -> {

            if (y == 0) {
                throw new ArithmeticException("Division by zero");
            }

            e.setY(e.getY() / y);
        });

        return self();
    }

    @Override
    public T divZ(double z) {
        getIFPoints().forEach(e -> {

            if (z == 0) {
                throw new ArithmeticException("Division by zero");
            }

            e.setZ(e.getZ() / z);
        });

        return self();
    }

    @Override
    public T scale(double scaleFactor) {
        getIFPoints().forEach(e -> e.mul(scaleFactor, scaleFactor, scaleFactor));
        return self();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public T devDescribe() {
        debugPrintStream.println(toString());
        return self();
    }

    @Override
    public T devDescribe(String message) {
        debugPrintStream.println(message + " - " + toString());
        return self();
    }

    @Override
    public T devStore(T element) {
        element.set(self());
        return self();
    }

}
