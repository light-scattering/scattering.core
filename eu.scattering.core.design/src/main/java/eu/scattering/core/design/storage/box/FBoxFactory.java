package eu.scattering.core.design.storage.box;

import eu.scattering.core.design.storage.box.variants.FBoxDouble;
import eu.scattering.core.design.storage.box.variants.FBoxString;

public interface FBoxFactory {

    FBoxDouble getFBoxDouble();
    FBoxString getFBoxString();
}
