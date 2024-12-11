package eu.scattering.core.impl.production.support.helper;

import eu.scattering.core.design.helper.angle.FAngleHelper;

public class AngleHelperProd implements FAngleHelper {

    private AngleHelperProd() { }

    public static FAngleHelper create() {

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
