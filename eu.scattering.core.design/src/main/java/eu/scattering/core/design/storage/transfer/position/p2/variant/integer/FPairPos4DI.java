package eu.scattering.core.design.storage.transfer.position.p2.variant.integer;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.transfer.position.p1.variant.integer.FPos4DI;

public interface FPairPos4DI extends Storage {

    FPos4DI getPosA();
    FPos4DI getPosB();
}
