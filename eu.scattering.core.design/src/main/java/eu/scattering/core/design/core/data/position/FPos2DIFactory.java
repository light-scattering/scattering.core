package eu.scattering.core.design.core.data.position;

import org.json.JSONObject;

public interface FPos2DIFactory {

    default FPos2DI getFPos2DI(int d0, int d1) {
        return FPos2DI.create(d0, d1);
    }

    default FPos2DI getFPos2DI(FPos2DI pos) {
        return getFPos2DI(pos.getD0(), pos.getD1());
    }

    //--------------------------------------------------

    default FPos2DI getFPos2DI(String text) {
        return FPos2DI.create(text);
    }

    default FPos2DI getFPos2DI(JSONObject json) {
        return FPos2DI.create(json);
    }
}
