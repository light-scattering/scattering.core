package eu.scattering.core.impl.production.support.helper;

import eu.scattering.core.design.helpers.auxiliary.FAngleHelper;

public class FAngleHelperDef implements FAngleHelper {

    private FAngleHelperDef() { }

    public static FAngleHelper create() {

        return new FAngleHelperDef();
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
