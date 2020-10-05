package eu.scattering.core.impl.production.main.mutable.geometry.shape;

import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.mutable.geometry.base.vector.FVector;
import eu.scattering.core.design.main.mutable.geometry.shape.Shape;
import eu.scattering.core.impl.production.main.mutable.MutablePresetProd;

public abstract class ShapePresetProd<T> extends MutablePresetProd<T> implements Shape<T> {

    private FVector axisOX;
    private FVector axisOY;
    private FVector axisOZ;

    protected void setAxes(FVector axisOX, FVector axisOY, FVector axisOZ) {

        this.axisOX = axisOX;
        this.axisOY = axisOY;
        this.axisOZ = axisOZ;
    }

    protected FVector getAxisOX() {

        return axisOX;
    }

    protected FVector getAxisOY() {

        return axisOY;
    }

    protected FVector getAxisOZ() {

        return axisOZ;
    }

    @Override
    public boolean intersectsWith(Shape shape) {
        return false;
    }

    @Override
    public Shape[] getIntersectingShapes(Shape[] shapes) {
        return new Shape[0];
    }

    @Override
    public Iterable<FPoint> getVolumeMesh(double distance) {
        return null;
    }

    @Override
    public Iterable<FPoint> getSurfaceMesh(double distance) {
        return null;
    }

    @Override
    public double getVolume(Shape[] exclusion) {
        return 0;
    }

    @Override
    public double getSurface(Shape[] exclusion) {
        return 0;
    }

    @Override
    public double getRadius() {
        return 0;
    }

    @Override
    public double getInnerRadius() {
        return 0;
    }

    @Override
    public T setPosition(FPoint position) {


        return self();
    }
}
