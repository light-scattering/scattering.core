package eu.scattering.core.design.transfer.complex;

import eu.scattering.core.design.transfer.primitive.FMatrix3x3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.design.transfer.primitive.FPos4D;
import org.json.JSONObject;

public interface FRotQtFactory {

    default FRotQt getFRotQt(FPos4D qt, FPos3D offset, FMatrix3x3D matrix) {

        return FRotQt.create(qt, offset, matrix);
    }

    default FRotQt getFRotQt(JSONObject json) {

        return FRotQt.create(json);
    }
}
