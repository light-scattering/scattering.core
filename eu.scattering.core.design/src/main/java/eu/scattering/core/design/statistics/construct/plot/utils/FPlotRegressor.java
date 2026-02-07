package eu.scattering.core.design.statistics.construct.plot.utils;

import eu.scattering.core.design.storage.polynomial.variant.FPoly;

public interface FPlotRegressor {

    FPoly poly(int degree);
    FPoly poly(int degree, int min, int max);

    FPoly fitConstant();
    FPoly fitConstant(int min, int max);

    FPoly fitLinear();
    FPoly fitLinear(int min, int max);

    // -------------------------------------------------------------------------------------------------

    FPoly fitSlope(int window);

    // -------------------------------------------------------------------------------------------------

    double mse(FPoly candidate);
    double mse(FPoly candidate, int min, int max);

    double rmse(FPoly candidate);
    double rmse(FPoly candidate, int min, int max);
}
