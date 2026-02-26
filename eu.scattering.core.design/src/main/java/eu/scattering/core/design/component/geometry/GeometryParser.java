package eu.scattering.core.design.component.geometry;

import org.json.JSONObject;

public interface GeometryParser {

    Geometry parse(JSONObject json, String tag);

    //--------------------------------------------------

    default Geometry parse(JSONObject json) {

        return parse(json, null);
    }
}
