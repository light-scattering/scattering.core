package eu.scattering.core.design.aspect.rotate.transfer.variant;

import eu.scattering.core.design.storage.transfer.Transfer;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;

public interface FRotQt extends Transfer {

    FPos3D getOffset();

    FPos4D getQuaternion();

    FMatrix3x3D getMatrix();
}
