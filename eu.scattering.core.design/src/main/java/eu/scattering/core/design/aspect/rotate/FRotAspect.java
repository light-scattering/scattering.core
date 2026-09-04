package eu.scattering.core.design.aspect.rotate;

import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.aspect.rotate.mutation.FRotMutationFactory;
import eu.scattering.core.design.aspect.rotate.state.FRotStateFactory;

public interface FRotAspect extends Aspect, FRotStateFactory, FRotMutationFactory {
}
