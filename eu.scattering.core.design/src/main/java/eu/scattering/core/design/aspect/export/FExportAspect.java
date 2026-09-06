package eu.scattering.core.design.aspect.export;

import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.component.ComponentExporter;
import eu.scattering.core.design.statistics.StatisticsExporter;
import eu.scattering.core.design.storage.StorageExporter;

public interface FExportAspect extends StorageExporter, ComponentExporter, StatisticsExporter, Aspect {
}
