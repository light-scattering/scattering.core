package eu.scattering.core.design.core.data.pos4DI;

import eu.scattering.core.design.core.data.pos2DI.FPos2DI;
import eu.scattering.core.design.core.data.pos3DI.FPos3DI;
import org.json.JSONObject;

public interface FPos4DIFactory {

    default FPos4DI getFPos4DI(int d0, int d1, int d2, int d3) {
        return FPos4DI.create(d0, d1, d2, d3);
    }

    default FPos4DI getFPos4DI(FPos4DI pos) {
        return getFPos4DI(pos.getD0(), pos.getD1(), pos.getD2(), pos.getD3());
    }

    default FPos4DI getFPos4DI(FPos3DI pos, int d3) {
        return getFPos4DI(pos.getD0(), pos.getD1(), pos.getD2(), d3);
    }

    default FPos4DI getFPos4DI(int d0, FPos3DI pos) {
        return getFPos4DI(d0, pos.getD0(), pos.getD1(), pos.getD2());
    }

    default FPos4DI getFPos4DI(FPos2DI posA, FPos2DI posB) {
        return getFPos4DI(posA.getD0(), posA.getD1(), posB.getD0(), posB.getD1());
    }

    default FPos4DI getFPos4DI(FPos2DI pos, int d2, int d3) {
        return getFPos4DI(pos.getD0(), pos.getD1(), d2, d3);
    }

    default FPos4DI getFPos4DI(int d0, FPos2DI pos, int d3) {
        return getFPos4DI(d0, pos.getD0(), pos.getD1(), d3);
    }

    default FPos4DI getFPos4DI(int d0, int d1, FPos2DI pos) {
        return getFPos4DI(d0, d1, pos.getD0(), pos.getD1());
    }

    //--------------------------------------------------

    default FPos4DI getFPos4DI(String text) {
        return FPos4DI.create(text);
    }

    default FPos4DI getFPos4DI(JSONObject json) {
        return FPos4DI.create(json);
    }
}
