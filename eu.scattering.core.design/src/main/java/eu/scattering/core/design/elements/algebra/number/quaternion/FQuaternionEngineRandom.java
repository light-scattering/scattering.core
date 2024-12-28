package eu.scattering.core.design.elements.algebra.number.quaternion;

import eu.scattering.core.transfer.containers.position.FPairPos4D.FPairPos4D;

public interface FQuaternionEngineRandom {

    void rndPosition(FQuaternion origin, FPairPos4D range, FQuaternion... exclusion);
    void rndPosition(FQuaternion origin, double radius, FQuaternion... exclusion);
}
