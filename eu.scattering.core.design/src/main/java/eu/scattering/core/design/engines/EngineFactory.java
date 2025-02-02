package eu.scattering.core.design.engines;

import eu.scattering.core.design.engines.proto.FProtoEngineFactory;
import eu.scattering.core.design.engines.rand.FRandEngineFactory;
import eu.scattering.core.design.engines.rand.processor.FRandProcessorFactory;
import eu.scattering.core.design.engines.rot.FRotEngineFactory;
import eu.scattering.core.design.engines.rot.processor.FRotProcessorFactory;

public interface EngineFactory extends FRandProcessorFactory, FRandEngineFactory,
        FRotProcessorFactory, FRotEngineFactory,
        FProtoEngineFactory {
}
