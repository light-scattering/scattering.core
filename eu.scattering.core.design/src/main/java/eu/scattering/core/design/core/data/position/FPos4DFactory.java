package eu.scattering.core.design.core.data.position;

import org.json.JSONObject;

public interface FPos4DFactory {

    default FPos4D getFPos4D(double d0, double d1, double d2, double d3) {
        return FPos4D.create(d0, d1, d2, d3);
    }

    default FPos4D getFPos4D(FPos4D pos) {
        return getFPos4D(pos.getD0(), pos.getD1(), pos.getD2(), pos.getD3());
    }

    default FPos4D getFPos4D(FPos3D pos, double d3) {
        return getFPos4D(pos.getD0(), pos.getD1(), pos.getD2(), d3);
    }

    default FPos4D getFPos4D(double d0, FPos3D pos) {
        return getFPos4D(d0, pos.getD0(), pos.getD1(), pos.getD2());
    }

    default FPos4D getFPos4D(FPos2D posA, FPos2D posB) {
        return getFPos4D(posA.getD0(), posA.getD1(), posB.getD0(), posB.getD1());
    }

    default FPos4D getFPos4D(FPos2D pos, double d2, double d3) {
        return getFPos4D(pos.getD0(), pos.getD1(), d2, d3);
    }

    default FPos4D getFPos4D(double d0, FPos2D pos, double d3) {
        return getFPos4D(d0, pos.getD0(), pos.getD1(), d3);
    }

    default FPos4D getFPos4D(double d0, double d1, FPos2D pos) {
        return getFPos4D(d0, d1, pos.getD0(), pos.getD1());
    }

    default FPos4D getFPos4D(FPos4DI pos) {
        return getFPos4D(pos.getD0(), pos.getD1(), pos.getD2(), pos.getD3());
    }

    default FPos4D getFPos4D(FPos3DI pos, double d3) {
        return getFPos4D(pos.getD0(), pos.getD1(), pos.getD2(), d3);
    }

    default FPos4D getFPos4D(double d0, FPos3DI pos) {
        return getFPos4D(d0, pos.getD0(), pos.getD1(), pos.getD2());
    }

    default FPos4D getFPos4D(FPos2DI posA, FPos2DI posB) {
        return getFPos4D(posA.getD0(), posA.getD1(), posB.getD0(), posB.getD1());
    }

    default FPos4D getFPos4D(FPos2DI pos, double d2, double d3) {
        return getFPos4D(pos.getD0(), pos.getD1(), d2, d3);
    }

    default FPos4D getFPos4D(double d0, FPos2DI pos, double d3) {
        return getFPos4D(d0, pos.getD0(), pos.getD1(), d3);
    }

    default FPos4D getFPos4D(double d0, double d1, FPos2DI pos) {
        return getFPos4D(d0, d1, pos.getD0(), pos.getD1());
    }

    default FPos4D getFPos4D(FPos2D posA, FPos2DI posB) {
        return getFPos4D(posA.getD0(), posA.getD1(), posB.getD0(), posB.getD1());
    }

    default FPos4D getFPos4D(FPos2DI posA, FPos2D posB) {
        return getFPos4D(posA.getD0(), posA.getD1(), posB.getD0(), posB.getD1());
    }

    //--------------------------------------------------

    default FPos4D getFPos4D(String text) {
        return FPos4D.create(text);
    }

    default FPos4D getFPos4D(JSONObject json) {
        return FPos4D.create(json);
    }
}
