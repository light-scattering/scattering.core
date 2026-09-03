package eu.scattering.core.design.aspect;

import eu.scattering.core.design.aspect.save.FSaveAspectFactory;
import eu.scattering.core.design.aspect.load.FLoadAspectFactory;
import eu.scattering.core.design.aspect.prototype.FProtoAspectFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspectFactory;
import eu.scattering.core.design.aspect.rotate.FRotAspectFactory;
import eu.scattering.core.design.aspect.rotate.generator.FRotGeneratorFactory;
import eu.scattering.core.design.aspect.rotate.transfer.FRotTransferFactory;

public interface AspectFactory extends
        FRandAspectFactory,
        FRotGeneratorFactory, FRotTransferFactory, FRotAspectFactory,
        FProtoAspectFactory,
        FSaveAspectFactory, FLoadAspectFactory {
}
