package eu.scattering.core.design.aspect.rotate.state;

import eu.scattering.core.design.storage.transfer.Transfer;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;

public interface FRotState extends Transfer {

    FPos3D getOffset();

    FPos4D getQuaternion();

    FMatrix3x3D getMatrix();

    double getAngle();

    FPairPos3D getAxis();
}
