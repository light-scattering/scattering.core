package eu.scattering.core.design.engine;

import eu.scattering.core.design.engine.prototype.FProtoEngineFactory;
import eu.scattering.core.design.engine.randomize.FRandEngineFactory;
import eu.scattering.core.design.engine.randomize.processor.FRandProcessorFactory;
import eu.scattering.core.design.engine.rotate.FRotEngineFactory;
import eu.scattering.core.design.engine.rotate.processor.FRotProcessorFactory;

public interface EngineFactory extends FRandProcessorFactory, FRandEngineFactory,
        FRotProcessorFactory, FRotEngineFactory,
        FProtoEngineFactory {
}
