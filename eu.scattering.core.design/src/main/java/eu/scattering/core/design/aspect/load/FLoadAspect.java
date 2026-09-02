package eu.scattering.core.design.aspect.load;

import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.component.aggregate.FAggregateAspectLoad;

public interface FLoadAspect extends Aspect {

    FAggregateAspectLoad aggregates();
}
