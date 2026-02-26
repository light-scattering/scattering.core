package eu.scattering.core.design.storage.transfer.position.p2.variant;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface FPairPos3D extends Storage {

    FPos3D getPosA();
    FPos3D getPosB();
}
