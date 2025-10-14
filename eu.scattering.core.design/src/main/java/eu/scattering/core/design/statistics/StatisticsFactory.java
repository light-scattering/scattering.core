package eu.scattering.core.design.statistics;

import eu.scattering.core.design.statistics.construct.FPlot2DFactory;
import eu.scattering.core.design.statistics.base.FStat1DFactory;

public interface StatisticsFactory extends FStat1DFactory, FPlot2DFactory {

    StatisticsExporter getStatisticsExporter();

    StatisticsHelper getStatisticsHelper();
}
