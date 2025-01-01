package eu.scattering.core.transfer.containers.engine.FRot;

import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import org.json.JSONObject;

public interface FRotFactory {

    default FRot getFRot(FPairPos3D rotAxis, double rotAngle, FPos4D rotCore) {

        return FRot.create(rotAxis, rotAngle, rotCore);
    }

    default FRot getFRot(JSONObject json) {

        return FRot.create(json);
    }
}
