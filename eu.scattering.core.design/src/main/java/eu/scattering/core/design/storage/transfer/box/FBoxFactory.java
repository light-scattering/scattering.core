package eu.scattering.core.design.storage.transfer.box;

import eu.scattering.core.design.storage.transfer.box.variant.FBoxDouble;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxString;

public interface FBoxFactory {

    FBoxDouble getFBoxDouble();
    FBoxString getFBoxString();
}
