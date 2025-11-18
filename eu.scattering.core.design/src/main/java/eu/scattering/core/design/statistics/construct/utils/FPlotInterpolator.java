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

    double apx(FPlot data, double x);

    double apxLinear(FPlot data, double x);
    double apxCosine(FPlot data, double x);
    double apxCubic(FPlot data, double x);
    double apxCatmullRom(FPlot data, double x);
    double apxHermite(FPlot data, double x);

    // -------------------------------------------------------------------------------------------------

    boolean isEqual(FPlotInterpolator interpolator);

    FPlotInterpolator copy();

    JSONObject toJSON();

    // -------------------------------------------------------------------------------------------------

    enum Method { HERMITE, CATMULL_ROM, LINEAR, COSINE, CUBIC }
}
