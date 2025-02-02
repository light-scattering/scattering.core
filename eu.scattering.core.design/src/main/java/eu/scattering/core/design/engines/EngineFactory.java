package eu.scattering.core.design.engines;

import eu.scattering.core.design.engines.random.FRandomEngineFactory;
import eu.scattering.core.design.engines.random.processor.FRandomProcessorFactory;
import eu.scattering.core.design.engines.rot.FRotEngineFactory;
import eu.scattering.core.design.engines.rot.processor.FRotProcessorFactory;

public interface EngineFactory extends FRandomProcessorFactory, FRandomEngineFactory, FRotProcessorFactory, FRotEngineFactory {
}
