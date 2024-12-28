package eu.scattering.core.transfer.containers.position.FPos2DI;

import org.json.JSONObject;

public interface FPos2DIFactory {

    default FPos2DI getFPos2DI(int d0, int d1) {

        return FPos2DI.create(d0, d1);
    }

    default FPos2DI getFPos2DI(JSONObject json) {

        return FPos2DI.create(json);
    }
}
