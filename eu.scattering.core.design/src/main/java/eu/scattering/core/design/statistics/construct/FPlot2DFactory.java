package eu.scattering.core.design.statistics.construct;

import org.json.JSONObject;

public interface FPlot2DFactory {

    FPlot2D getFPlot2D();

    FPlot2D getFPlot2D(JSONObject json);
}
