package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;
import eu.scattering.core.design.utility.annotation.Fragment;

public interface FPointHelper {

    double getMagnitude(double x, double y, double z);
    double getMagnitude(FPos3D center);
    FPos3D setMagnitude(double refX, double refY, double refZ, double magnitude);
    FPos3D setMagnitude(FPos3D ref, double magnitude);

    boolean isNearZero(double x, double y, double z);

    boolean isExact(double aX, double aY, double aZ, double bX, double bY, double bZ);
    boolean isSimilar(double aX, double aY, double aZ, double bX, double bY, double bZ);

    double getDistance(double aX, double aY, double aZ, double bX, double bY, double bZ);
    FPos3D setDistance(double x, double y, double z, double refX, double refY, double refZ, double distance);
    FPos3D setDistance(double x, double y, double z, FPos3D ref, double distance);
    FPos3D setDistance(FPos3D center, double refX, double refY, double refZ, double distance);
    FPos3D setDistance(FPos3D center, FPos3D ref, double distance);

    //--------------------------------------------------

    @Fragment
    double getMagnitudeP2(double x, double y, double z);
    @Fragment
    double getDistanceP2(double aX, double aY, double aZ, double bX, double bY, double bZ);
}
