package eu.scattering.core.design.storage.transfer.box;

import eu.scattering.core.design.storage.transfer.box.variants.FBoxDouble;
import eu.scattering.core.design.storage.transfer.box.variants.FBoxString;

public interface FBoxFactory {

    FBoxDouble getFBoxDouble();
    FBoxString getFBoxString();
}
