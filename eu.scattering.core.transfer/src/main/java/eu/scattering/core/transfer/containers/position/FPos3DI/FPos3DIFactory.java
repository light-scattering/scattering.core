package eu.scattering.core.transfer.containers.position.FPos3DI;

import eu.scattering.core.transfer.containers.position.FPos2DI.FPos2DI;
import org.json.JSONObject;

public interface FPos3DIFactory {

    default FPos3DI getFPos3DI(int d0, int d1, int d2) {

        return FPos3DI.create(d0, d1, d2);
    }

    default FPos3DI getFPos3DI(FPos3DI pos) {

        return getFPos3DI(pos.getD0(), pos.getD1(), pos.getD2());
    }

    default FPos3DI getFPos3DI(FPos2DI pos, int d2) {

        return getFPos3DI(pos.getD0(), pos.getD1(), d2);
    }

    default FPos3DI getFPos3DI(int d0, FPos2DI pos) {

        return getFPos3DI(d0, pos.getD0(), pos.getD1());
    }

    default FPos3DI getFPos3DI(JSONObject json) {

        return FPos3DI.create(json);
    }
}
