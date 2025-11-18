package eu.scattering.core.design.statistics.construct;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.layer.FLayer;
import org.json.JSONException;
import org.json.JSONObject;

public interface FPlotFactory {

    FPlot getFPlot();

    FPlot getFPlot(FLayer fLayer);

    @Modificator
    FPlot getRefFPlot(FStat refDataX, FStat refDataY);

    //--------------------------------------------------

    FPlot getFPlot(JSONObject json);

    //--------------------------------------------------

    default FPlot getFPlot(String text) {

        try {
            return getFPlot(new JSONObject(text));
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid json format");
        }
    }
}
