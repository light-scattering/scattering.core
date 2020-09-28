package eu.scattering.core.impl.production.support.helper;

import eu.scattering.core.design.support.helper.AngleHelper;

public class AngleHelperProd implements AngleHelper {

    private AngleHelperProd() { }

    public static AngleHelper create() {

        return new AngleHelperProd();
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
