package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.design.transfer.primitive.FPairPos4D;

public interface FQuaternionEngineRand {

    FQuaternion inRange(FQuaternion in, FPairPos4D range);
}
