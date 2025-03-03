package eu.scattering.core.transfer.container.storage.FPairPos3D;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONObject;

public interface FPairPos3DFactory {

    default FPairPos3D getFPairPos3D(double AD0, double AD1, double AD2, double BD0, double BD1, double BD2) {

        return FPairPos3D.create(AD0, AD1, AD2, BD0, BD1, BD2);
    }

    default FPairPos3D getFPairPos3D(FPos3D posA, FPos3D posB) {

        return FPairPos3D.create(posA, posB);
    }

    default FPairPos3D getFPairPos3D(JSONObject json) {

        return FPairPos3D.create(json);
    }
}
