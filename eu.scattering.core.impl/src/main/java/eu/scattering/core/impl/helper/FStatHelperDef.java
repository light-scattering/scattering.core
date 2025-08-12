package eu.scattering.core.impl.helper;

import eu.scattering.core.design.helper.statistics.FStatHelper;

public class FStatHelperDef implements FStatHelper {
    private static FStatHelperDef self;

    private FStatHelperDef() { }

    public static FStatHelper get() {

        if (FStatHelperDef.self == null) {
            FStatHelperDef.self = new FStatHelperDef();
        }

        return FStatHelperDef.self;
    }

    @Override
    public double getAbsErr(double arg1, double arg2) {

        return Math.abs(arg1 - arg2);
    }

    @Override
    public boolean valAbsErr(double arg1, double arg2, double epsilon) {

        return getAbsErr(arg1, arg2) < epsilon;
    }

    @Override
    public double getRelErr(double base, double ref) {

        if (base == ref) {
            return 0;
        }

        return Math.abs((base - ref) / base);
    }

    @Override
    public boolean valRelErr(double base, double ref, double epsilon) {

        return getRelErr(base, ref) < epsilon;
    }
}
