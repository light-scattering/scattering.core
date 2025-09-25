package eu.scattering.core.design.engine;

import eu.scattering.core.design.engine.export.FExportEngineFactory;
import eu.scattering.core.design.engine.prototype.FProtoEngineFactory;
import eu.scattering.core.design.engine.randomize.FRandEngineFactory;
import eu.scattering.core.design.engine.randomize.generator.FRandGeneratorFactory;
import eu.scattering.core.design.engine.rotate.FRotEngineFactory;
import eu.scattering.core.design.engine.rotate.generator.FRotGeneratorFactory;

public interface EngineFactory extends FRandGeneratorFactory, FRandEngineFactory,
        FRotGeneratorFactory, FRotEngineFactory,
        FProtoEngineFactory,
        FExportEngineFactory {
}
