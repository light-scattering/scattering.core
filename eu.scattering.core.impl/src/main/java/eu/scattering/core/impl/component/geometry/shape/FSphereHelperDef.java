package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPointHelper;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.transfer.primitive.FPos3D;

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
}

// https://mathworld.wolfram.com/Sphere-SphereIntersection.html