package eu.scattering.core.design.statistics.construct.utils;

import eu.scattering.core.design.transfer.primitive.FPoly;

public interface FPlotRegressor {

    FPoly poly(int degree);
    FPoly poly(int degree, int min, int max);

    FPoly fitConstant(int min, int max);
    FPoly fitLinear(int min, int max);

    // -------------------------------------------------------------------------------------------------

    FPoly fitSlope(int window);

    // -------------------------------------------------------------------------------------------------

    double mse(FPoly candidate);
    double mse(FPoly candidate, int min, int max);

    double rmse(FPoly candidate);
    double rmse(FPoly candidate, int min, int max);
}
