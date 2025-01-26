package eu.scattering.core.transfer.containers.engine.FRotQt;

import eu.scattering.core.transfer.containers.grid.FMatrix3x3D.FMatrix3x3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import org.json.JSONObject;

public interface FRotQtFactory {

    default FRotQt getFRotQt(FPos4D qt, FPos3D offset, FMatrix3x3D matrix) {

        return FRotQt.create(qt, offset, matrix);
    }

    default FRotQt getFRotQt(JSONObject json) {

        return FRotQt.create(json);
    }
}
