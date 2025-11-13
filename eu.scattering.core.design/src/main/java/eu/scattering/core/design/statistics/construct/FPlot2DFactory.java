package eu.scattering.core.design.statistics.construct;

import eu.scattering.core.design.storage.layer.FLayer;
import org.json.JSONObject;

public interface FPlot2DFactory {

    FPlot2D getFPlot2D();

    FPlot2D getFPlot2D(FLayer fLayer);

    FPlot2D getFPlot2D(JSONObject json);
}
