package eu.scattering.core.design.storage.transfer.pair;

import eu.scattering.core.design.storage.transfer.pair.variants.*;
import eu.scattering.core.design.storage.transfer.single.variants.*;
import org.json.JSONObject;

public interface FPositionPairFactory {

    FPairPos2D getFPairPos2D(double AD0, double AD1, double BD0, double BD1);
    FPairPos2D getFPairPos2D(FPos2D posA, FPos2D posB);
    FPairPos2D getFPairPos2D(JSONObject json);

    FPairPos2DI getFPairPos2DI(int AD0, int AD1, int BD0, int BD1);
    FPairPos2DI getFPairPos2DI(FPos2DI posA, FPos2DI posB);
    FPairPos2DI getFPairPos2DI(JSONObject json);

    FPairPos3D getFPairPos3D(double AD0, double AD1, double AD2, double BD0, double BD1, double BD2);
    FPairPos3D getFPairPos3D(FPos3D posA, FPos3D posB);
    FPairPos3D getFPairPos3D(JSONObject json);

    FPairPos3DI getFPairPos3DI(int AD0, int AD1, int AD2, int BD0, int BD1, int BD2);
    FPairPos3DI getFPairPos3DI(FPos3DI posA, FPos3DI posB);
    FPairPos3DI getFPairPos3DI(JSONObject json);

    FPairPos4D getFPairPos4D(double AD0, double AD1, double AD2, double AD3, double BD0, double BD1, double BD2, double BD3);
    FPairPos4D getFPairPos4D(FPos4D posA, FPos4D posB);
    FPairPos4D getFPairPos4D(JSONObject json);

    FPairPos4DI getFPairPos4DI(int AD0, int AD1, int AD2, int AD3, int BD0, int BD1, int BD2, int BD3);
    FPairPos4DI getFPairPos4DI(FPos4DI posA, FPos4DI posB);
    FPairPos4DI getFPairPos4DI(JSONObject json);

    //--------------------------------------------------

    default FPairPos3D getFPairPos3D(double min, double max) {

        return getFPairPos3D(min, min, min, max, max, max);
    }

    default FPairPos3D getFPairPos3D(double range) {

        return getFPairPos3D(-range, -range, -range, range, range, range);
    }
}
