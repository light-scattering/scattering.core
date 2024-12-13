package eu.scattering.core.design.elements.data.position;

import org.json.JSONObject;

public interface FPos2DFactory {

    default FPos2D getFPos2D(double d0, double d1) {
        return FPos2D.create(d0, d1);
    }

    default FPos2D getFPos2D(FPos2D pos) {
        return getFPos2D(pos.getD0(), pos.getD1());
    }

    default FPos2D getFPos2D(FPos2DI pos) {
        return getFPos2D(pos.getD0(), pos.getD1());
    }

    //--------------------------------------------------

    default FPos2D getFPos2D(String text) {
        return FPos2D.create(text);
    }

    default FPos2D getFPos2D(JSONObject json) {
        return FPos2D.create(json);
    }
}
