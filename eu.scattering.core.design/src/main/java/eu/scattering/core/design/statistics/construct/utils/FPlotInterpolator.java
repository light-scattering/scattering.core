package eu.scattering.core.design.statistics.construct.utils;

import eu.scattering.core.design.statistics.construct.FPlot;

import java.util.function.BiFunction;

public interface FPlotInterpolator {

    double hermite(double x);
    double hermite(double x, double bias, double tension);
    double cosine(double x);
    double catmullRom(double x);
    double linear(double x);
    double cubic(double x);

    // -------------------------------------------------------------------------------------------------

    FPlot sampleStep(BiFunction<FPlotInterpolator, Double, Double> function, double step);
    FPlot sampleStep(BiFunction<FPlotInterpolator, Double, Double> function, double min, double max, double step);

    FPlot sampleDivisions(BiFunction<FPlotInterpolator, Double, Double> function, int divisions);
    FPlot sampleDivisions(BiFunction<FPlotInterpolator, Double, Double> function, double min, double max, int divisions);
}
