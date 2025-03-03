package eu.scattering.core.transfer.container.storage.FPos3D;

import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;
import org.json.JSONObject;

public interface FPos3DFactory {

    default FPos3D getFPos3D(double d0, double d1, double d2) {

        return FPos3D.create(d0, d1, d2);
    }

    default FPos3D getFPos3D(FPos2D pos, double d2) {

        return getFPos3D(pos.getD0(), pos.getD1(), d2);
    }

    default FPos3D getFPos3D(double d0, FPos2D pos) {

        return getFPos3D(d0, pos.getD0(), pos.getD1());
    }

    default FPos3D getFPos3D(JSONObject json) {

        return FPos3D.create(json);
    }
}
