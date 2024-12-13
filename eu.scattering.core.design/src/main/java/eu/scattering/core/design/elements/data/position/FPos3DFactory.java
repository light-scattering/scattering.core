package eu.scattering.core.design.elements.data.position;

import org.json.JSONObject;

public interface FPos3DFactory {

    default FPos3D getFPos3D(double d0, double d1, double d2) {
        return FPos3D.create(d0, d1, d2);
    }

    default FPos3D getFPos3D(FPos3D pos) {
        return getFPos3D(pos.getD0(), pos.getD1(), pos.getD2());
    }

    default FPos3D getFPos3D(FPos2D pos, double d2) {
        return getFPos3D(pos.getD0(), pos.getD1(), d2);
    }

    default FPos3D getFPos3D(double d0, FPos2D pos) {
        return getFPos3D(d0, pos.getD0(), pos.getD1());
    }

    default FPos3D getFPos3D(FPos3DI pos) {
        return getFPos3D(pos.getD0(), pos.getD1(), pos.getD2());
    }

    default FPos3D getFPos3D(FPos2DI pos, double d2) {
        return getFPos3D(pos.getD0(), pos.getD1(), d2);
    }

    default FPos3D getFPos3D(double d0, FPos2DI pos) {
        return getFPos3D(d0, pos.getD0(), pos.getD1());
    }

    //--------------------------------------------------

    default FPos3D getFPos3D(String text) {
        return FPos3D.create(text);
    }

    default FPos3D getFPos3D(JSONObject json) {
        return FPos3D.create(json);
    }
}
