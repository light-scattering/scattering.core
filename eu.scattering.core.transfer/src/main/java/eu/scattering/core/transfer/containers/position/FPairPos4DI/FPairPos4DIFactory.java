package eu.scattering.core.transfer.containers.position.FPairPos4DI;

import eu.scattering.core.transfer.containers.position.FPos4DI.FPos4DI;
import org.json.JSONObject;

public interface FPairPos4DIFactory {

    default FPairPos4DI getFPairPos4DI(FPos4DI posA, FPos4DI posB) {

        return FPairPos4DI.create(posA, posB);
    }

    default FPairPos4DI getFPairPos4DI(JSONObject json) {

        return FPairPos4DI.create(json);
    }
}
