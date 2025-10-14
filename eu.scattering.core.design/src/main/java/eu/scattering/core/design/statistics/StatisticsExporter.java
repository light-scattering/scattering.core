package eu.scattering.core.design.statistics;

import eu.scattering.core.design.statistics.base.FStat1DExporter;
import eu.scattering.core.design.statistics.construct.FPlot2DExporter;

public interface StatisticsExporter extends FStat1DExporter, FPlot2DExporter {

    String getName();
    void setName(String name);

    String getNameX();
    void setNameX(String nameX);

    String getNameY();
    void setNameY(String nameY);
}
