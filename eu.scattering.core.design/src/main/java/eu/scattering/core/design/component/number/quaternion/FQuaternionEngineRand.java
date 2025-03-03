package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;

public interface FQuaternionEngineRand {

    FQuaternion rndPos(FQuaternion in, FPairPos4D range, FQuaternion... exclusion);
}
