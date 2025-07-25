package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPointHelper;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FPointHelperDef implements FPointHelper {

    private FPointHelperDef() {}

    public static FPointHelper get() {

        return new FPointHelperDef();
    }

    @Override
    public double getMagnitude(double x, double y, double z) {

        return Math.sqrt(getMagnitudeP2(x, y, z));
    }

    @Override
    public boolean isExact(double aX, double aY, double aZ, double bX, double bY, double bZ) {

        return aX == bX && aY == bY && aZ == bZ;
    }

    @Override
    public boolean isSimilar(double aX, double aY, double aZ, double bX, double bY, double bZ) {
        double distanceX = Math.abs(aX - bX);
        double distanceY = Math.abs(aY - bY);
        double distanceZ = Math.abs(aZ - bZ);

        return distanceX < EPSILON && distanceY < EPSILON && distanceZ < EPSILON;
    }

    @Override
    public double getDistance(double aX, double aY, double aZ, double bX, double bY, double bZ) {

        return Math.sqrt(getDistanceP2(aX, aY, aZ, bX, bY, bZ));
    }

    //--------------------------------------------------

    @Override
    public double getMagnitudeP2(double x, double y, double z) {

        return (x * x) + (y * y) + (z * z);
    }

    @Override
    public double getDistanceP2(double aX, double aY, double aZ, double bX, double bY, double bZ) {
        double dimX = aX - bX;
        double dimY = aY - bY;
        double dimZ = aZ - bZ;

        return (dimX * dimX) + (dimY * dimY) + (dimZ * dimZ);
    }

}
