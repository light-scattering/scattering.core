package eu.scattering.core.transfer.containers.position.FPairPos2DI;

import eu.scattering.core.transfer.containers.position.FPos2DI.FPos2DI;
import org.json.JSONObject;

public interface FPairPos2DIFactory {

    default FPairPos2DI getFPairPos2DI(FPos2DI posA, FPos2DI posB) {

        return FPairPos2DI.create(posA, posB);
    }

    default FPairPos2DI getFPairPos2DI(JSONObject json) {

        return FPairPos2DI.create(json);
    }
}
