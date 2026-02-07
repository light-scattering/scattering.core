package eu.scattering.core.design.storage.transfer.position.p2.variant.integer;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.transfer.position.p1.variant.integer.FPos3DI;

public interface FPairPos3DI extends Storage {

    FPos3DI getPosA();
    FPos3DI getPosB();
}
