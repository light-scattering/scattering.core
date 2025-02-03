package eu.scattering.core.transfer.container.position.FPairPos3DI;

import eu.scattering.core.transfer.container.position.FPos3DI.FPos3DI;
import org.json.JSONObject;

public interface FPairPos3DIFactory {

    default FPairPos3DI getFPairPos3DI(int AD0, int AD1, int AD2, int BD0, int BD1, int BD2) {

        return FPairPos3DI.create(AD0, AD1, AD2, BD0, BD1, BD2);
    }

    default FPairPos3DI getFPairPos3DI(FPos3DI posA, FPos3DI posB) {

        return FPairPos3DI.create(posA, posB);
    }

    default FPairPos3DI getFPairPos3DI(JSONObject json) {

        return FPairPos3DI.create(json);
    }
}
