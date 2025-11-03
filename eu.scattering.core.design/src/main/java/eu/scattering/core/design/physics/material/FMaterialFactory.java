package eu.scattering.core.design.physics.material;

import org.json.JSONException;
import org.json.JSONObject;

public interface FMaterialFactory {

    FMaterial getFMaterial();

    //--------------------------------------------------

    FMaterial getFMaterial(JSONObject json);

    //--------------------------------------------------

    default FMaterial getFMaterial(String text) {

        try {
            return getFMaterial(new JSONObject(text));
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid json format");
        }
    }
}
