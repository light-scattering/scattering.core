package eu.scattering.core.geometry;

import eu.scattering.core.geometry.base.point.IFPoint;

public abstract class PresetGeometry<T> implements IGeometryBase<T>, IGeometryAlgebra<T> {

    @Override
    public boolean equals(Object object) {
        return isExact(object);
    }

    @Override
    public int hashCode() {
        return getHashCode();
    }

    @Override
    public String toString() {
        return exportToJSON();
    }

    @Override
    public T clone() {
        return copy();
    }

    public T add(IFPoint fPoint) {
        getIFPoints().forEach(e -> e.add(fPoint.getX(), fPoint.getY(), fPoint.getZ()));
        return self();
    }

    public T add(double x, double y, double z) {
        getIFPoints().forEach(e -> e.addX(x).addY(y).addZ(z));
        return self();
    }

    public T addX(double x) {
        getIFPoints().forEach(e -> e.setX(e.getX() + x));
        return self();
    }

    public T addY(double y) {
        getIFPoints().forEach(e -> e.setY(e.getY() + y));
        return self();
    }

    public T addZ(double z) {
        getIFPoints().forEach(e -> e.setZ(e.getZ() + z));
        return self();
    }


}
