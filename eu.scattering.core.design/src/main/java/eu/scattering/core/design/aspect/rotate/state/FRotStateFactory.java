package eu.scattering.core.design.aspect.rotate.state;

import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import org.json.JSONObject;

public interface FRotStateFactory {

    FRotState fromComponents(FPos4D quaternion, FPos3D offset, FMatrix3x3D matrix);

    FRotState aroundAxis(double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FRotState aroundAxis(FPairPos3D axis, double angle);
    FRotState aroundAxis(double x, double y, double z, double angle);
    FRotState aroundAxis(FPos3D axis, double angle);

    //--------------------------------------------------

    FRotState fromJSON(JSONObject json);
}
