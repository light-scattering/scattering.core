package eu.scattering.core.transfer.container.storage.FPairPos4DI;

import eu.scattering.core.transfer.container.storage.FPos4DI.FPos4DI;
import org.json.JSONObject;

public interface FPairPos4DIFactory {

    default FPairPos4DI getFPairPos4DI(int AD0, int AD1, int AD2, int AD3, int BD0, int BD1, int BD2, int BD3) {

        return FPairPos4DI.create(AD0, AD1, AD2, AD3, BD0, BD1, BD2, BD3);
    }

    default FPairPos4DI getFPairPos4DI(FPos4DI posA, FPos4DI posB) {

        return FPairPos4DI.create(posA, posB);
    }

    default FPairPos4DI getFPairPos4DI(JSONObject json) {

        return FPairPos4DI.create(json);
    }
}
