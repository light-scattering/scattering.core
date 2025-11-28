package eu.scattering.core.design.statistics;

import eu.scattering.core.design.statistics.base.FStatEngineExport;
import eu.scattering.core.design.statistics.construct.FPlotEngineExport;
import eu.scattering.core.design.transfer.primitive.FPos2D;

public interface StatisticsEngineExport extends FStatEngineExport, FPlotEngineExport {

    String getName();
    StatisticsEngineExport setName(String name);

    String getNameX();
    StatisticsEngineExport setNameX(String nameX);

    String getNameY();
    StatisticsEngineExport setNameY(String nameY);

    String getAnnotation();
    StatisticsEngineExport setAnnotation(String annotation);

    FPos2D getRangeX();
    StatisticsEngineExport setRangeX(double min, double max);

    FPos2D getRangeY();
    StatisticsEngineExport setRangeY(double min, double max);
}
