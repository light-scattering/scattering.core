package eu.scattering.core.transfer.container.position.FPairPos2D;

import eu.scattering.core.transfer.container.position.FPos2D.FPos2D;
import org.json.JSONObject;

public interface FPairPos2DFactory {

    default FPairPos2D getFPairPos2D(double AD0, double AD1, double BD0, double BD1) {

        return FPairPos2D.create(AD0, AD1, BD0, BD1);
    }

    default FPairPos2D getFPairPos2D(FPos2D posA, FPos2D posB) {

        return FPairPos2D.create(posA, posB);
    }

    default FPairPos2D getFPairPos2D(JSONObject json) {

        return FPairPos2D.create(json);
    }
}
