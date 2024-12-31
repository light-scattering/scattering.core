package eu.scattering.core.transfer.containers.engine.FRot;

import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import eu.scattering.core.transfer.enums.FRotationEngine;
import org.json.JSONObject;

public interface FRotFactory {

    default FRot getFRot(FRotationEngine engine, FPairPos3D rotAxis, double rotAngle, FPos4D rotCore) {

        return FRot.create(engine, rotAxis, rotAngle, rotCore);
    }

    default FRot getFRot(JSONObject json) {

        return FRot.create(json);
    }
}
