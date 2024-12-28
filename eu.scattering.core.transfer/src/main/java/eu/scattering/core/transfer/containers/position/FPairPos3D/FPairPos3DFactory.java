package eu.scattering.core.transfer.containers.position.FPairPos3D;

import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import org.json.JSONObject;

public interface FPairPos3DFactory {

    default FPairPos3D getFPairPos3D(FPos3D posA, FPos3D posB) {

        return FPairPos3D.create(posA, posB);
    }

    default FPairPos3D getFPairPos3D(JSONObject json) {

        return FPairPos3D.create(json);
    }
}
