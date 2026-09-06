package eu.scattering.core.design.statistics;

import eu.scattering.core.design.statistics.base.FStatExporter;
import eu.scattering.core.design.statistics.construct.plot.FPlotExporter;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarExporter;

public interface StatisticsExporter extends FStatExporter, FPlotExporter, FPlotBarExporter {
}
