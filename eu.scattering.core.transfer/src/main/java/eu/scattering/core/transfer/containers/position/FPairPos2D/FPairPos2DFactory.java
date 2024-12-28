package eu.scattering.core.transfer.containers.position.FPairPos2D;

import eu.scattering.core.transfer.containers.position.FPos2D.FPos2D;
import org.json.JSONObject;

public interface FPairPos2DFactory {

    default FPairPos2D getFPairPos2D(FPos2D posA, FPos2D posB) {

        return FPairPos2D.create(posA, posB);
    }

    default FPairPos2D getFPairPos2D(JSONObject json) {

        return FPairPos2D.create(json);
    }
}
