package eu.scattering.core.transfer.statistics.FPlot2D;

import org.json.JSONObject;

public interface FPlot2DInterpolator {

    Method getMethod();
    void setMethod(Method method);

    double getHermiteBias();
    void setHermiteBias(double bias);

    double getHermiteTension();
    void setHermiteTension(double tension);

    // -------------------------------------------------------------------------------------------------

    double apx(FPlot2D data, double x);

    double apxLinear(FPlot2D data, double x);
    double apxCosine(FPlot2D data, double x);
    double apxCubic(FPlot2D data, double x);
    double apxCatmullRom(FPlot2D data, double x);
    double apxHermite(FPlot2D data, double x);

    // -------------------------------------------------------------------------------------------------

    boolean isEqual(FPlot2DInterpolator interpolator);

    FPlot2DInterpolator copy();

    JSONObject toJSON();

    // -------------------------------------------------------------------------------------------------

    enum Method { HERMITE, CATMULL_ROM, LINEAR, COSINE, CUBIC }
}
