package eu.scattering.core.design.statistics;

import eu.scattering.core.design.statistics.base.FStatExporter;
import eu.scattering.core.design.statistics.construct.FPlotExporter;

public interface StatisticsExporter extends FStatExporter, FPlotExporter {

    String getName();
    void setName(String name);

    String getNameX();
    void setNameX(String nameX);

    String getNameY();
    void setNameY(String nameY);
}
