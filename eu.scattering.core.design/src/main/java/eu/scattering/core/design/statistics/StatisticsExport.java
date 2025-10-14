package eu.scattering.core.design.statistics;

import eu.scattering.core.design.statistics.base.FStat1DExport;
import eu.scattering.core.design.statistics.construct.FPlot2DExport;

public interface StatisticsExport extends FStat1DExport, FPlot2DExport {

    String getName();
    void setName(String name);

    String getNameX();
    void setNameX(String nameX);

    String getNameY();
    void setNameY(String nameY);
}
