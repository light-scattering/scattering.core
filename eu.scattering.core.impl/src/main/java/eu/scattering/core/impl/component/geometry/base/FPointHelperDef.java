package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPointHelper;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FPointHelperDef implements FPointHelper {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private FPointHelperDef() {}

    public static FPointHelper get() {

        return new FPointHelperDef();
    }

    @Override
    public double getMagnitude(double x, double y, double z) {

        return Math.sqrt(getMagnitudeP2(x, y, z));
    }

    @Override
    public double getMagnitude(FPos3D center) {

        return getMagnitude(center.getD0(), center.getD1(), center.getD2());
    }

    @Override
    public FPos3D setMagnitude(double refX, double refY, double refZ, double magnitude) {

        if (isNearZero(refX, refY, refZ)) {
            throw new IllegalStateException("The vector is non-directional (the position is too close to zero)");
        }

        double factor = magnitude / getMagnitude(refX, refY, refZ);

        return factoryExt.getFPos3D(refX * factor, refY * factor, refZ * factor);
    }

    @Override
    public FPos3D setMagnitude(FPos3D ref, double magnitude) {

        return setMagnitude(ref.getD0(), ref.getD1(), ref.getD2(), magnitude);
    }

    @Override
    public boolean isNearZero(double x, double y, double z) {

        return isSimilar(x, y, z, 0, 0, 0);
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

    @Override
    public FPos3D setDistance(double x, double y, double z, double refX, double refY, double refZ, double distance) {

        if (isExact(x, y, z, refX, refY, refZ)) {
            throw new IllegalStateException("FPoints must not be on the same position");
        }

        double posX = refX - x;
        double posY = refY - y;
        double posZ = refZ - z;

        double factor = distance / getMagnitude(posX, posY, posZ);

        posX = (posX * factor) + x;
        posY = (posY * factor) + y;
        posZ = (posZ * factor) + z;

        return factoryExt.getFPos3D(posX, posY, posZ);
    }

    @Override
    public FPos3D setDistance(double x, double y, double z, FPos3D ref, double distance) {

        return setDistance(x, y, z, ref.getD0(), ref.getD1(), ref.getD2(), distance);
    }

    @Override
    public FPos3D setDistance(FPos3D center, double refX, double refY, double refZ, double distance) {

        return setDistance(center.getD0(), center.getD1(), center.getD2(), refX, refY, refZ, distance);
    }

    @Override
    public FPos3D setDistance(FPos3D center, FPos3D ref, double distance) {

        return setDistance(center.getD0(), center.getD1(), center.getD2(), ref, distance);
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
