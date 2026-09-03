package eu.scattering.core.design.aspect.randomize;

import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.aspect.randomize.distribution.FDistFactory;
import eu.scattering.core.design.component.ComponentAspectRand;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;

public interface FRandAspect extends ComponentAspectRand, Aspect, FDistFactory {

    FRandGenerator generator();
}
