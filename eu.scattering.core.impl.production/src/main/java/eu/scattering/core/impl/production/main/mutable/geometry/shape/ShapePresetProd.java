package eu.scattering.core.impl.production.main.mutable.geometry.shape;

import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.mutable.geometry.base.vector.FVector;
import eu.scattering.core.design.main.mutable.geometry.shape.Shape;
import eu.scattering.core.impl.production.main.mutable.MutablePresetProd;

import java.util.ArrayList;
import java.util.List;

public abstract class ShapePresetProd<T> extends MutablePresetProd<T> implements Shape<T> {

    private FVector axisOX;//OrientationBox
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

    public FPoint getCenter() { // must make a copy (a single point cannot be extracted)

        return getAxisOX().getBase();
    }

    protected abstract double getAlgebraicVolume();
    protected abstract double getAlgebraicSurface();

    @Override
    public boolean intersectsStronglyWith(Shape shape) {
        return false;
    }

    @Override
    public Shape[] getStronglyIntersectingShapes(Shape[] shapes) {
        return new Shape[0];
    }

    @Override
    public Iterable<FPoint> getDoubleVolumeMesh(double distance) {
        return null;
    }

    @Override
    public Iterable<FPoint> getDoubleSurfaceMesh(double distance) {
        return null;
    }

    @Override
    public double getExactVolume(Shape[] exclusion) {
        return 0;
    }

    @Override
    public double getExactSurface(Shape[] exclusion) {
        return 0;
    }

    @Override
    public double getOuterRadius() {
        return 0;
    }

    @Override
    public double getInnerRadius() {
        return 0;
    }

    @Override
    public T setCenter(FPoint position) {

        return self();
    }

    @Override
    public T setOuterRadius(double radius) {
        double factor = radius / getOuterRadius();

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
