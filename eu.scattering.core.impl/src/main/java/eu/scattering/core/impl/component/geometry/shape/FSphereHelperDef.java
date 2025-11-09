package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPointHelper;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.function.BiConsumer;

public class FSphereHelperDef implements FSphereHelper {
    private final FPointHelper fPointHelper;

    private FSphereHelperDef(FPointHelper fPointHelper) {

        this.fPointHelper = fPointHelper;
    }

    public static FSphereHelper get(FPointHelper fPointHelper) {

        return new FSphereHelperDef(fPointHelper);
    }

    @Override
    public double getVolume(double radius) {

        return 4 * Math.PI * radius * radius * radius / 3;
    }

    @Override
    public double getSurface(double radius) {

        return 4 * Math.PI * radius * radius;
    }

    @Override
    public double getVolumeRadius(double volume) {

        return Math.pow(0.75 * volume / Math.PI, 1.0 / 3);
    }

    @Override
    public double getSurfaceRadius(double surface) {

        return Math.pow(0.25 * surface / Math.PI, 0.5);
    }

    @Override
    public double getVolume(FPos3D posA, FPos3D posB, double rA, double rB) {

        return getVolume(rA) + getVolume(rB) - getVolumeCommon(posA, posB, rA, rB);
    }

    @Override
    public double getSurface(FPos3D posA, FPos3D posB, double rA, double rB) {

        return getSurface(rA) + getSurface(rB) - getSurfaceCommon(posA, posB, rA, rB);
    }

    @Override
    public double getVolumeCommon(FPos3D posA, FPos3D posB, double rA, double rB) {

        double d = fPointHelper.getDistance(
                posA.getD0(), posA.getD1(), posA.getD2(),
                posB.getD0(), posB.getD1(), posB.getD2()
        );

        double hA = (rB - rA + d) * (rB + rA - d) / (2 * d);
        double hB = (rA - rB + d) * (rA + rB - d) / (2 * d);

        double vA = Math.PI * (hA * hA) * ((3 * rA) - hA) / 3;
        double vB = Math.PI * (hB * hB) * ((3 * rB) - hB) / 3;

        return vA + vB;
    }

    @Override
    public double getSurfaceCommon(FPos3D posA, FPos3D posB, double rA, double rB) {

        double d = fPointHelper.getDistance(
                posA.getD0(), posA.getD1(), posA.getD2(),
                posB.getD0(), posB.getD1(), posB.getD2()
        );

        double hA = (rB - rA + d) * (rB + rA - d) / (2 * d);
        double hB = (rA - rB + d) * (rA + rB - d) / (2 * d);

        double sA = 2 * Math.PI * rA * hA;
        double sB = 2 * Math.PI * rB * hB;

        return sA + sB;
    }

    @Override
    public double getVolumeRing(double r1, double r2) {

        return getVolume(r2) - getVolume(r1);
    }

    @Override
    public double getRadiusOfGyration(double r) {

        return Math.sqrt(0.6) * r;
    }

    @Override
    public boolean intersectsCube(Shape shape, double cSqX, double cSqY, double cSqZ, double size) {

        if (size <= 0) {
            throw new IllegalArgumentException("The box size must be greater than zero");
        }

        double sHalf = size * 0.5;

        double x = shape.getCenterX();
        double y = shape.getCenterY();
        double z = shape.getCenterZ();

        double xMin = cSqX - sHalf;
        double xMax = cSqX + sHalf;
        double yMin = cSqY - sHalf;
        double yMax = cSqY + sHalf;
        double zMin = cSqZ - sHalf;
        double zMax = cSqZ + sHalf;

        double pX = Math.max(xMin, Math.min(x, xMax));
        double pY = Math.max(yMin, Math.min(y, yMax));
        double pZ = Math.max(zMin, Math.min(z, zMax));

        double dX = pX - x;
        double dY = pY - y;
        double dZ = pZ - z;

        return (dX * dX) + (dY * dY) + (dZ * dZ) <= Math.pow(shape.getRadius(), 2);
    }

    @Override
    public void getSpherePoints(double radius, int count, TriConsumer<Double, Double, Double> consumer) {

        if (radius <= 0) {
            throw new IllegalArgumentException("The sphere radius must be greater then zero");
        }

        if (count <= 0) {
            throw new IllegalArgumentException("The number of points must be greater then zero");
        }

        double offset = 2.0 / count;
        double increment = Math.PI * (3.0 - Math.sqrt(5));

        for (int i = 0; i < count; i++) {
            double y = 1 - (i + 0.5) * offset;
            double r = Math.sqrt(1 - y * y);
            double phi = i * increment;

            double x = Math.cos(phi) * r;
            double z = Math.sin(phi) * r;

            consumer.accept(x * radius, y * radius, z * radius);
        }
    }

    @Override
    public void getCirclePoints(double radius, int count, BiConsumer<Double, Double> consumer) {

        if (radius <= 0) {
            throw new IllegalArgumentException("The sphere radius must be greater then zero");
        }

        if (count <= 0) {
            throw new IllegalArgumentException("The number of points must be greater then zero");
        }

        double spacing = 2 * Math.PI / count;

        for (int i = 0; i < count; i++) {
            double angle = i * spacing;

            consumer.accept(radius * Math.cos(angle), radius * Math.sin(angle));
        }
    }
}

// https://mathworld.wolfram.com/Sphere-SphereIntersection.html