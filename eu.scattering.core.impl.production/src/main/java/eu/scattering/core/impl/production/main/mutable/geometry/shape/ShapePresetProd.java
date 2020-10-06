package eu.scattering.core.impl.production.main.mutable.geometry.shape;

import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.mutable.geometry.base.vector.FVector;
import eu.scattering.core.design.main.mutable.geometry.shape.Shape;
import eu.scattering.core.design.main.mutable.geometry.shape.sphere.FSphere;
import eu.scattering.core.impl.production.main.mutable.MutablePresetProd;

import java.util.ArrayList;
import java.util.List;

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

    protected FPoint getCenter() {

        return getAxisOX().getBase();
    }

    protected abstract double getAlgebraicVolume();
    protected abstract double getAlgebraicSurface();

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

    @Override
    public T setRadius(double radius) {
        double factor = radius / getRadius();

        getAxisOX().setLength(getAxisOX().getLength() * factor);
        getAxisOY().setLength(getAxisOY().getLength() * factor);
        getAxisOZ().setLength(getAxisOZ().getLength() * factor);

        return self();
    }

    @Override
    public T setInnerRadius(double innerRadius) {
        double factor = innerRadius / getInnerRadius();

        getAxisOX().setLength(getAxisOX().getLength() * factor);
        getAxisOY().setLength(getAxisOY().getLength() * factor);
        getAxisOZ().setLength(getAxisOZ().getLength() * factor);

        return self();
    }

    @Override
    public List<FPoint> disassemble() {

        List<FPoint> fPointList = new ArrayList<>();

        fPointList.add(getCenter());

        fPointList.add(getAxisOX().getHead());
        fPointList.add(getAxisOY().getHead());
        fPointList.add(getAxisOZ().getHead());

        return fPointList;
    }

}
