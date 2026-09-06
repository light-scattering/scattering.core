package eu.scattering.core.design.storage.transfer.position.p2;

import eu.scattering.core.design.storage.transfer.position.p1.variant.integer.FPos2DI;
import eu.scattering.core.design.storage.transfer.position.p1.variant.integer.FPos3DI;
import eu.scattering.core.design.storage.transfer.position.p1.variant.integer.FPos4DI;
import eu.scattering.core.design.storage.transfer.position.p2.variant.*;
import eu.scattering.core.design.storage.transfer.position.p1.variant.*;
import eu.scattering.core.design.storage.transfer.position.p2.variant.integer.FPairPos2DI;
import eu.scattering.core.design.storage.transfer.position.p2.variant.integer.FPairPos3DI;
import eu.scattering.core.design.storage.transfer.position.p2.variant.integer.FPairPos4DI;
import org.json.JSONObject;

public interface FPos2Factory {

    FPairPos2D getFPairPos2D(double aD0, double aD1, double bD0, double bD1);
    FPairPos2D getFPairPos2D(FPos2D posA, FPos2D posB);
    FPairPos2D getFPairPos2D(JSONObject json);

    FPairPos2DI getFPairPos2DI(int aD0, int aD1, int bD0, int bD1);
    FPairPos2DI getFPairPos2DI(FPos2DI posA, FPos2DI posB);
    FPairPos2DI getFPairPos2DI(JSONObject json);

    FPairPos3D getFPairPos3D(double aD0, double aD1, double aD2, double bD0, double bD1, double bD2);
    FPairPos3D getFPairPos3D(FPos3D posA, FPos3D posB);
    FPairPos3D getFPairPos3D(JSONObject json);

    FPairPos3DI getFPairPos3DI(int aD0, int aD1, int aD2, int bD0, int bD1, int bD2);
    FPairPos3DI getFPairPos3DI(FPos3DI posA, FPos3DI posB);
    FPairPos3DI getFPairPos3DI(JSONObject json);

    FPairPos4D getFPairPos4D(double aD0, double aD1, double aD2, double aD3, double bD0, double bD1, double bD2, double bD3);
    FPairPos4D getFPairPos4D(FPos4D posA, FPos4D posB);
    FPairPos4D getFPairPos4D(JSONObject json);

    FPairPos4DI getFPairPos4DI(int aD0, int aD1, int aD2, int aD3, int bD0, int bD1, int bD2, int bD3);
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
