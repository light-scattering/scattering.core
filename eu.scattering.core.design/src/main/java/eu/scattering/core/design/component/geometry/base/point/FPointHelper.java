package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.util.annotation.Fragment;

public interface FPointHelper {

    double getMagnitude(double x, double y, double z);

    boolean isExact(double aX, double aY, double aZ, double bX, double bY, double bZ);
    boolean isSimilar(double aX, double aY, double aZ, double bX, double bY, double bZ);

    double getDistance(double aX, double aY, double aZ, double bX, double bY, double bZ);

    //--------------------------------------------------

    @Fragment
    double getMagnitudeP2(double x, double y, double z);
    @Fragment
    double getDistanceP2(double aX, double aY, double aZ, double bX, double bY, double bZ);
}
