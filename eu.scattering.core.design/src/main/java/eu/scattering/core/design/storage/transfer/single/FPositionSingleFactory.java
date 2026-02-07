package eu.scattering.core.design.storage.transfer.single;

import eu.scattering.core.design.storage.transfer.single.variants.*;
import org.json.JSONObject;

public interface FPositionSingleFactory {

    FPos2D getFPos2D(double d0, double d1);
    FPos2D getFPos2D(JSONObject json);

    FPos2DI getFPos2DI(int d0, int d1);
    FPos2DI getFPos2DI(JSONObject json);

    FPos3D getFPos3D(double d0, double d1, double d2);
    FPos3D getFPos3D(JSONObject json);

    FPos3DI getFPos3DI(int d0, int d1, int d2);
    FPos3DI getFPos3DI(JSONObject json);

    FPos4D getFPos4D(double d0, double d1, double d2, double d3);
    FPos4D getFPos4D(JSONObject json);

    FPos4DI getFPos4DI(int d0, int d1, int d2, int d3);
    FPos4DI getFPos4DI(JSONObject json);

    //--------------------------------------------------

    default FPos3D getFPos3D(FPos2D pos, double d2) {

        return getFPos3D(pos.getD0(), pos.getD1(), d2);
    }

    default FPos3D getFPos3D(double d0, FPos2D pos) {

        return getFPos3D(d0, pos.getD0(), pos.getD1());
    }

    default FPos3DI getFPos3DI(FPos2DI pos, int d2) {

        return getFPos3DI(pos.getD0(), pos.getD1(), d2);
    }

    default FPos3DI getFPos3DI(int d0, FPos2DI pos) {

        return getFPos3DI(d0, pos.getD0(), pos.getD1());
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
}
