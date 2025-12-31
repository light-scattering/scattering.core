package eu.scattering.core.design.aspect.export;

import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.component.aggregate.FAggregateAspectExport;
import eu.scattering.core.design.statistics.StatisticsAspectExport;

public interface FExportAspect extends Aspect {

    StatisticsAspectExport getStatisticsContext();

    FAggregateAspectExport getFAggregateContext();
}
