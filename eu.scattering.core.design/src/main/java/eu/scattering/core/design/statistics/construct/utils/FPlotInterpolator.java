package eu.scattering.core.design.statistics.construct.utils;

import eu.scattering.core.design.statistics.construct.FPlot;
import org.json.JSONObject;

public interface FPlotInterpolator {

    Method getMethod();
    void setMethod(Method method);

    double getHermiteBias();
    void setHermiteBias(double bias);

    double getHermiteTension();
    void setHermiteTension(double tension);

    // -------------------------------------------------------------------------------------------------

    double get(FPlot data, double x);

    double hermite(FPlot data, double x);
    double hermite(FPlot data, double x, double bias, double tension);

    double cubic(FPlot data, double x);
    double linear(FPlot data, double x);
    double cosine(FPlot data, double x);
    double catmullRom(FPlot data, double x);

    // -------------------------------------------------------------------------------------------------

    boolean isEqual(FPlotInterpolator interpolator);

    FPlotInterpolator copy();

    JSONObject toJSON();

    // -------------------------------------------------------------------------------------------------

    enum Method { HERMITE, CATMULL_ROM, LINEAR, COSINE, CUBIC }
}
