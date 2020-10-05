package eu.scattering.core.impl.production.main.mutable.geometry.shape.sphere;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.mutable.geometry.base.vector.FVector;
import eu.scattering.core.design.main.mutable.geometry.shape.Shape;
import eu.scattering.core.design.main.mutable.geometry.shape.sphere.FSphere;
import eu.scattering.core.impl.production.main.mutable.geometry.shape.ShapePresetProd;
import org.json.JSONObject;

public class FSphereProd extends ShapePresetProd<FSphere> implements FSphere {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final Factory factory;

    private FSphereProd(Factory factory, double radius) {

        this.factory = factory;

        FPoint center = factory.getFPoint();
        FVector axisOX = factory.getFVector(center, radius, 0, 0);
        FVector axisOY = factory.getFVector(center, 0, 1, 0);
        FVector axisOZ = factory.getFVector(center, 0, 0, 1);

        setAxes(axisOX, axisOY, axisOZ);
    }

    public static FSphere create(Factory factory, double radius) {

        return new FSphereProd(factory, radius);
    }

    @Override
    public boolean contains(FPoint fPoint) {
        return false;
    }

    @Override
    public boolean intersectsWith(Shape shape) {
        return false;
    }

    @Override
    public Shape[] getIntersectingShapes(Shape... shapes) {
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
    public double getVolume(Shape... exclusion) {
        return 0;
    }

    @Override
    public double getSurface(Shape... exclusion) {
        return 0;
    }

    @Override
    public double getRadius() {

        return getAxisOX().getLength();
    }

    @Override
    public double getRadiusP2() {

        return getAxisOX().getLengthP2();
    }

    @Override
    public double getInnerRadius() {

        return getRadius();
    }

    @Override
    public double getInnerRadiusP2() {

        return getRadiusP2();
    }

    @Override
    public FSphere importFromJSON(JSONObject json) {
        return null;
    }

    @Override
    public boolean isSimilar(FSphere element) {
        return false;
    }

    @Override
    public boolean isExact(FSphere element) {
        return false;
    }

    @Override
    public FSphere copy() {
        return null;
    }

    @Override
    public FSphere self() {

        return this;
    }

    @Override
    public JSONObject exportToJSON() {
        return null;
    }
}
