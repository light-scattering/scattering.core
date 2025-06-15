package eu.scattering.core.design;

import eu.scattering.core.design.engine.EngineFactory;
import eu.scattering.core.design.component.ComponentFactory;
import eu.scattering.core.design.helper.HelperFactory;
import eu.scattering.core.transfer.TransferFactory;

public interface ScatFactory extends TransferFactory, ComponentFactory, EngineFactory, HelperFactory {
}
