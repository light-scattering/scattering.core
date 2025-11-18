package eu.scattering.core.design.statistics;

import eu.scattering.core.design.statistics.construct.FPlotFactory;
import eu.scattering.core.design.statistics.base.FStatFactory;

public interface StatisticsFactory extends FStatFactory, FPlotFactory {

    StatisticsExporter getStatisticsExporter();

    StatisticsHelper getStatisticsHelper();
}
