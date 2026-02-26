package eu.scattering.core.design.storage.transfer.position.p2.variant;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;

public interface FPairPos4D extends Storage {

    FPos4D getPosA();
    FPos4D getPosB();
}
