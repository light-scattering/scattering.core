package eu.scattering.core.design.statistics;

import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarFactory;
import eu.scattering.core.design.statistics.construct.plot.FPlotFactory;
import eu.scattering.core.design.statistics.base.FStatFactory;

public interface StatisticsFactory extends FStatFactory, FPlotFactory, FPlotBarFactory {

    StatisticsHelper getStatisticsHelper();
}
