package eu.scattering.core.design;

import eu.scattering.core.design.engine.EngineFactory;
import eu.scattering.core.design.mutable.MutableFactory;
import eu.scattering.core.design.helper.HelperFactory;
import eu.scattering.core.transfer.TransferFactory;

public interface FactoryDesign extends TransferFactory, MutableFactory, EngineFactory, HelperFactory {
}
