package eu.scattering.core.design.aspect.load;

import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.aspect.load.aggregate.FAggregateLoader;

public interface FLoadAspect extends Aspect {

    FAggregateLoader aggregate();
}
