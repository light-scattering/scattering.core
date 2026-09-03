package eu.scattering.core.design.aspect.randomize;

import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.aspect.randomize.distribution.FRandDistFactory;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngineFactory;
import eu.scattering.core.design.component.ComponentAspectRand;

public interface FRandAspect extends ComponentAspectRand, Aspect, FRandDistFactory, FRandEngineFactory {
}
