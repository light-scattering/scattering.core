package eu.scattering.core.design.storage.box;

import eu.scattering.core.design.storage.box.variant.FBoxDouble;
import eu.scattering.core.design.storage.box.variant.FBoxString;

public interface FBoxFactory {

    FBoxDouble getFBoxDouble();
    FBoxString getFBoxString();
}
