package eu.scattering.core.transfer.container.position.FPairPos4D;

import eu.scattering.core.transfer.container.position.FPos4D.FPos4D;
import org.json.JSONObject;

public interface FPairPos4DFactory {

    default FPairPos4D getFPairPos4D(double AD0, double AD1, double AD2, double AD3, double BD0, double BD1, double BD2, double BD3) {

        return FPairPos4D.create(AD0, AD1, AD2, AD3, BD0, BD1, BD2, BD3);
    }

    default FPairPos4D getFPairPos4D(FPos4D posA, FPos4D posB) {

        return FPairPos4D.create(posA, posB);
    }

    default FPairPos4D getFPairPos4D(JSONObject json) {

        return FPairPos4D.create(json);
    }
}
