package eu.scattering.core.design.engines;

import eu.scattering.core.design.engines.random.FRandomEngineFactory;
import eu.scattering.core.design.engines.random.processor.FRandomProcessorFactory;
import eu.scattering.core.design.engines.rotation.FRotationEngineFactory;
import eu.scattering.core.design.engines.rotation.processor.FRotationProcessorFactory;

public interface EngineFactory extends FRandomProcessorFactory, FRandomEngineFactory, FRotationProcessorFactory, FRotationEngineFactory {
}
