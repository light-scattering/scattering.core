package eu.scattering.core.design.mutable.number.quaternion;

import eu.scattering.core.transfer.container.position.FPairPos4D.FPairPos4D;

public interface FQuaternionEngineRand {

    FQuaternion rndPosition(FQuaternion origin, FPairPos4D range, FQuaternion... exclusion);
    FQuaternion rndPosition(FQuaternion origin, double radius, FQuaternion... exclusion);
}
