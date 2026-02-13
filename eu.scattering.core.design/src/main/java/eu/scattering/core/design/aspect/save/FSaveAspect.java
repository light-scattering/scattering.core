package eu.scattering.core.design.aspect.save;

import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.component.ComponentAspectSave;
import eu.scattering.core.design.statistics.StatisticsAspectSave;
import eu.scattering.core.design.storage.StorageAspectSave;

public interface FSaveAspect extends Aspect {

    StorageAspectSave getStorageContext();

    ComponentAspectSave getComponentContext();

    StatisticsAspectSave getStatisticsContext();
}
