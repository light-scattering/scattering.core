package eu.scattering.core.impl.production.core.mutable.geometry.shape.sphere;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.core.data.pos3DI.FPos3DI;
import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.core.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.core.algebra.geometry.shape.Shape;
import eu.scattering.core.design.core.algebra.geometry.shape.sphere.FSphere;
import eu.scattering.core.impl.production.core.mutable.geometry.shape.ShapePresetProd;
import org.json.JSONObject;

import java.util.Iterator;

public class FSphereProd extends ShapePresetProd<FSphere> implements FSphere {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final Factory factory;

    private FSphereProd(Factory factory) {

        this.factory = factory;

        FPoint center = factory.getFPoint();

        FVector axisOX = factory.getFVector(center, 1, 0, 0);
        FVector axisOY = factory.getFVector(center, 0, 1, 0);
        FVector axisOZ = factory.getFVector(center, 0, 0, 1);

        setAxes(axisOX, axisOY, axisOZ);
    }

    public static FSphere create(Factory factory) {

        return new FSphereProd(factory);
    }

    @Override
    public boolean contains(FPoint fPoint) {
        return false;
    }

    @Override
    protected double getAlgebraicVolume() {

        return (4 * Math.PI * Math.pow(this.getOuterRadius(), 3)) / 3;
    }

    @Override
    protected double getAlgebraicSurface() {

        return 4 * Math.PI * getOuterRadiusP2();
    }

    @Override
    public boolean intersectsStronglyWith(Shape shape) {
        return false;
    }

    @Override
    public boolean intersectsLooselyWith(Shape shape) {
        return false;
    }

    @Override
    public Shape[] getStronglyIntersectingShapes(Shape... shapes) {
        return new Shape[0];
    }

    @Override
    public Shape[] getLooselyIntersectingShapes(Shape... shapes) {
        return new Shape[0];
    }

    @Override
    public Iterable<FPoint> getDoubleVolumeMesh(double distance) {
        return null;
    }

    @Override
    public Iterable<FPos3DI> getIntegerVolumeMesh(double distance) {
        return null;
    }

    @Override
    public Iterable<FPoint> getDoubleSurfaceMesh(double distance) {

        class SurfaceMeshIterator implements Iterator<FPoint> {

            final FPoint reference = factory.getFPoint();
            final FPoint sphereCenter;
            final double sphereRadius;
            final int numberOfPoints;

            final double tmp1;
            final double tmp2;

            int index;

            SurfaceMeshIterator(FPoint sphereCenter, double sphereRadius, int numberOfPoints) {
                this.sphereCenter = sphereCenter;
                this.sphereRadius = sphereRadius;
                this.numberOfPoints = numberOfPoints;
                this.tmp1 = Math.PI * (3 - Math.sqrt(5));
                this.tmp2 = 2 / (double) numberOfPoints;
            }

            @Override
            public boolean hasNext() {

                if (index < numberOfPoints) {
                    return true;
                } else {
                    index = 0;
                    return false;
                }
            }

            @Override
            public FPoint next() {

                double op1 = (index * tmp2) - 1 + (tmp2 / (double) 2);
                double op2 = Math.sqrt(1 - (op1 * op1));
                double op3 = index * tmp1;

                reference.set(Math.cos(op3) * op2, op1, Math.sin(op3) * op2);
                reference.setLength(sphereRadius);
                reference.add(sphereCenter);

                index++;

                return reference;
            }

        }

        class SurfaceMeshIterable implements Iterable<FPoint> {

            final FPoint sphereCenter = getAxisOX().getBase();
            final double sphereRadius = FSphereProd.this.getOuterRadius();
            final int numberOfPoints = (int) Math.round(getAlgebraicSurface() / (distance * distance));

            @Override
            public Iterator<FPoint> iterator() {

                return new SurfaceMeshIterator(sphereCenter.copy(), sphereRadius, numberOfPoints);
            }
        }

        return new SurfaceMeshIterable();
    }

    @Override
    public Iterable<FPos3DI> getIntegerSurfaceMesh(double distance) {
        return null;
    }

    @Override
    public double getVolume(Shape... exclusion) {
        return 0;
    }

    @Override
    public double getExactVolume(Shape... exclusion) {
        return 0;
    }

    @Override
    public double getApproximateVolume(Shape... exclusion) {
        return 0;
    }

    @Override
    public double getSurface(Shape... exclusion) {
        return 0;
    }

    @Override
    public double getExactSurface(Shape... exclusion) {
        return 0;
    }

    @Override
    public double getApproximateSurface(Shape... exclusion) {
        return 0;
    }

    @Override
    public double getOuterRadius() {

        return getAxisOX().getLength();
    }

    @Override
    public double getOuterRadiusP2() {

        return getAxisOX().getLengthP2();
    }

    @Override
    public double getInnerRadius() {

        return this.getOuterRadius();
    }

    @Override
    public double getInnerRadiusP2() {

        return getOuterRadiusP2();
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

    @Override
    public FSphere setOuterRadius(double radius) {

        getAxisOX().setLength(radius);

        return this;
    }

    @Override
    public FSphere setInnerRadius(double innerRadius) {

        return this.setOuterRadius(innerRadius);
    }

}
