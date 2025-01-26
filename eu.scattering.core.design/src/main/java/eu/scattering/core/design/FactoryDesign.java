package eu.scattering.core.design;

import eu.scattering.core.design.engines.EngineFactory;
import eu.scattering.core.design.mutables.MutableFactory;
import eu.scattering.core.design.helpers.HelperFactory;
import eu.scattering.core.transfer.TransferFactory;

public interface FactoryDesign extends TransferFactory, MutableFactory, EngineFactory, HelperFactory {

    void initialize();
}
