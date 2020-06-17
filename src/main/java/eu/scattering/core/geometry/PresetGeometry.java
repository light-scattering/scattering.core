package eu.scattering.core.geometry;

import eu.scattering.core.geometry.base.point.IFPoint;

public abstract class PresetGeometry<T> implements IGeometryAlgebra<T> {

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object object);

    @Override
    public abstract Object clone();

    @Override
    public abstract String toString();

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

    // -------------------------------------------------------------------------------------------------

    protected abstract T self();

}
