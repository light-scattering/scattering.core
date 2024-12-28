package eu.scattering.core.transfer.containers.position.FPairPos4D;

import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import org.json.JSONObject;

public interface FPairPos4DFactory {

    default FPairPos4D getFPairPos4D(FPos4D posA, FPos4D posB) {

        return FPairPos4D.create(posA, posB);
    }

    default FPairPos4D getFPairPos4D(JSONObject json) {

        return FPairPos4D.create(json);
    }
}
