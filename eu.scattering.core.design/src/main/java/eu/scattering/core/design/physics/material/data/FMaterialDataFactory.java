package eu.scattering.core.design.physics.material.data;

import org.json.JSONException;
import org.json.JSONObject;

public interface FMaterialDataFactory {

    FMaterialData getFMaterialData();

    //--------------------------------------------------

    FMaterialData getFMaterialData(JSONObject json);

    //--------------------------------------------------

    default FMaterialData getFMaterialData(String text) {

        try {
            return getFMaterialData(new JSONObject(text));
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid json format");
        }
    }
}
