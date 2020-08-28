package eu.scattering.core.implementation.support;

import eu.scattering.core.design.support.AngleHelper;

public class AngleHelperDefault implements AngleHelper {

    @Override
    public double radToDeg(double radian) {

        return radian * 180 / Math.PI;
    }

    @Override
    public double degToRad(double degree) {

        return degree * Math.PI / 180;
    }
}
