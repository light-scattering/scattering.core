package eu.scattering.core.transfer.containers.engine.FRot;

import eu.scattering.core.transfer.containers.grid.FMatrix3x3D.FMatrix3x3D;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import org.json.JSONObject;

public interface FRotFactory {

    default FRot getFRot(FPairPos3D rotAxis, double rotAngle, FPos4D rotCoreCode, FMatrix3x3D rotCoreMatrix) {

        return FRot.create(rotAxis, rotAngle, rotCoreCode, rotCoreMatrix);
    }

    default FRot getFRot(JSONObject json) {

        return FRot.create(json);
    }
}
