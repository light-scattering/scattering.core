package eu.scattering.core.design.statistics.construct.utils;

import eu.scattering.core.design.transfer.primitive.FPoly;

public interface FPlotRegressor {

    FPoly poly(int n);
    FPoly poly(int n, int min, int max);

    FPoly poly0(int min, int max);
    FPoly poly1(int min, int max);

    FPoly slope(int window);

    double mse(FPoly candidate);
    double mse(FPoly candidate, int min, int max);
}
