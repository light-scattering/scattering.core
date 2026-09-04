package eu.scattering.core.design.aspect;

import eu.scattering.core.design.aspect.save.FSaveAspectFactory;
import eu.scattering.core.design.aspect.load.FLoadAspectFactory;
import eu.scattering.core.design.aspect.prototype.FProtoAspectFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspectFactory;
import eu.scattering.core.design.aspect.rotate.FRotAspectFactory;

public interface AspectFactory extends
        FRandAspectFactory,
        FRotAspectFactory,
        FProtoAspectFactory,
        FSaveAspectFactory, FLoadAspectFactory {
}
