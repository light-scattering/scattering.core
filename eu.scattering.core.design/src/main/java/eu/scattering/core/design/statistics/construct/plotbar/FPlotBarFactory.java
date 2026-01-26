package eu.scattering.core.design.statistics.construct.plotbar;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.statistics.base.FStat;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface FPlotBarFactory {

    FPlotBar getFPlotBar();

    @Modificator
    FPlotBar getRefFPlotBar(FStat refDataX, List<FStat> refDataY);

    //--------------------------------------------------

    FPlotBar getFPlotBar(JSONObject json);

    //--------------------------------------------------

    FPlotBarMeta getFPlotBarMeta();

    //--------------------------------------------------

    default FPlotBar getFPlotBar(String text) {

        try {
            return getFPlotBar(new JSONObject(text));
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid json format");
        }
    }
}
