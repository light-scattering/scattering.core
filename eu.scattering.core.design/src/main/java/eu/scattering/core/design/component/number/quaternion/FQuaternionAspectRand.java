package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.design.storage.transfer.position.p2.variants.FPairPos4D;

public interface FQuaternionAspectRand {

    FQuaternion inRange(FQuaternion in, FPairPos4D range);
}
