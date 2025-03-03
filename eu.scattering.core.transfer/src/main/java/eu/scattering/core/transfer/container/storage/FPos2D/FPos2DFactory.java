package eu.scattering.core.transfer.container.storage.FPos2D;

import org.json.JSONObject;

public interface FPos2DFactory {

    default FPos2D getFPos2D(double d0, double d1) {

        return FPos2D.create(d0, d1);
    }

    default FPos2D getFPos2D(JSONObject json) {

        return FPos2D.create(json);
    }
}
