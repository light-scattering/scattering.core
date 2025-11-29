package eu.scattering.core.design.aspect.export;

import eu.scattering.core.design.component.ComponentAspectExport;
import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.statistics.StatisticsAspectExport;

public interface FExportAspect extends ComponentAspectExport, Aspect {

    StatisticsAspectExport getFPlotContext();
}
