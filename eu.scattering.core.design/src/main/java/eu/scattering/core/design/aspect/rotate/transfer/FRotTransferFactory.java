package eu.scattering.core.design.aspect.rotate.transfer;

import eu.scattering.core.design.aspect.rotate.transfer.variant.FRotQt;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;
import org.json.JSONObject;

public interface FRotTransferFactory {

    FRotQt getFRotQt(FPos4D qt, FPos3D offset, FMatrix3x3D matrix);

    FRotQt getFRotQt(JSONObject json);
}
