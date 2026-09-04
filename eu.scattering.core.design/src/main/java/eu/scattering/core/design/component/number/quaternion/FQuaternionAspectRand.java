package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos4D;

public interface FQuaternionAspectRand {

    FQuaternion withinRange(FQuaternion in, FPairPos4D range);
}
