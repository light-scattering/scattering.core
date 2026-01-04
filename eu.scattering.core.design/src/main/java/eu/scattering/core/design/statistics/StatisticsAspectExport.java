package eu.scattering.core.design.statistics;

import eu.scattering.core.design.statistics.base.FStatAspectExport;
import eu.scattering.core.design.statistics.construct.plot.FPlotAspectExport;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarAspectExport;
import eu.scattering.core.design.transfer.primitive.FPos2D;

public interface StatisticsAspectExport extends FStatAspectExport, FPlotAspectExport, FPlotBarAspectExport {

    String getName();
    StatisticsAspectExport setName(String name);

    String getNameX();
    StatisticsAspectExport setNameX(String nameX);

    String getNameY();
    StatisticsAspectExport setNameY(String nameY);

    String getAnnotation();
    StatisticsAspectExport setAnnotation(String annotation);

    FPos2D getRangeX();
    StatisticsAspectExport setRangeX(double min, double max);

    FPos2D getRangeY();
    StatisticsAspectExport setRangeY(double min, double max);
}
