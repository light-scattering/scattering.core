package eu.scattering.core.design.statistics.base;

import eu.scattering.core.design.annotation.Modificator;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface FStatFactory {

    FStat getFStat();

    @Modificator
    FStat getRefFStat(List<Double> refData);

    //--------------------------------------------------

    FStat getFStat(JSONObject json);

    //--------------------------------------------------

    default FStat getFStat(String text) {

        try {
            return getFStat(new JSONObject(text));
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid json format");
        }
    }
}
