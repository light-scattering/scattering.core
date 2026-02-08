package eu.scattering.core.design.aspect.save;

import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.component.ComponentAspectSave;
import eu.scattering.core.design.statistics.StatisticsAspectSave;

public interface FSaveAspect extends Aspect {

    ComponentAspectSave getComponentContext();

    StatisticsAspectSave getStatisticsContext();
}
