package eu.scattering.core.design.transfer.primitive;

import org.json.JSONObject;

public interface FPolyFactory {

    default FPoly getFPoly(double... core) {

        return FPoly.create(core);
    }

    default FPoly getFPoly(JSONObject json) {

        return FPoly.create(json);
    }
}
