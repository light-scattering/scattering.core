package eu.scattering.core.impl.production.support.helper;

import eu.scattering.core.test.design.support.helper.AngleHelper;

public class AngleHelperDefault implements AngleHelper {

    private AngleHelperDefault() { }

    public static AngleHelper create() {

        return new AngleHelperDefault();
    }

    @Override
    public double radToDeg(double radian) {

        return radian * 180 / Math.PI;
    }

    @Override
    public double degToRad(double degree) {

        return degree * Math.PI / 180;
    }
}
