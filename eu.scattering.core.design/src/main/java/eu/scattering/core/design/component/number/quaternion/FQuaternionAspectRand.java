package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos4D;

public interface FQuaternionAspectRand {

    FQuaternion inRange(FQuaternion in, FPairPos4D range);
}
