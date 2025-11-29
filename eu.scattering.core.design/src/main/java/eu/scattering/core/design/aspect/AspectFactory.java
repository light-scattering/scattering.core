package eu.scattering.core.design.aspect;

import eu.scattering.core.design.aspect.export.FExportAspectFactory;
import eu.scattering.core.design.aspect.prototype.FProtoAspectFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspectFactory;
import eu.scattering.core.design.aspect.randomize.generator.FRandGeneratorFactory;
import eu.scattering.core.design.aspect.rotate.FRotAspectFactory;
import eu.scattering.core.design.aspect.rotate.generator.FRotGeneratorFactory;

public interface AspectFactory extends FRandGeneratorFactory, FRandAspectFactory,
        FRotGeneratorFactory, FRotAspectFactory,
        FProtoAspectFactory,
        FExportAspectFactory {
}
