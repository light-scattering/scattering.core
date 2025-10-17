package eu.scattering.core.design.transfer.primitive;

import org.json.JSONObject;

public interface FPairPos2DIFactory {

    default FPairPos2DI getFPairPos2DI(int AD0, int AD1, int BD0, int BD1) {

        return FPairPos2DI.create(AD0, AD1, BD0, BD1);
    }

    default FPairPos2DI getFPairPos2DI(FPos2DI posA, FPos2DI posB) {

        return FPairPos2DI.create(posA, posB);
    }

    default FPairPos2DI getFPairPos2DI(JSONObject json) {

        return FPairPos2DI.create(json);
    }
}
