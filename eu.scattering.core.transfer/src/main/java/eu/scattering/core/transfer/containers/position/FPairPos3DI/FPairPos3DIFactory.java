package eu.scattering.core.transfer.containers.position.FPairPos3DI;

import eu.scattering.core.transfer.containers.position.FPos3DI.FPos3DI;
import org.json.JSONObject;

public interface FPairPos3DIFactory {

    default FPairPos3DI getFPairPos3DI(FPos3DI posA, FPos3DI posB) {

        return FPairPos3DI.create(posA, posB);
    }

    default FPairPos3DI getFPairPos3DI(JSONObject json) {

        return FPairPos3DI.create(json);
    }
}
