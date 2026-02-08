package eu.scattering.core.impl.statistics;

import eu.scattering.core.design.statistics.StatisticsHelper;

public class FStatHelperDef implements StatisticsHelper {

    private FStatHelperDef() { }

    public static StatisticsHelper create() {

        return new FStatHelperDef();
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
