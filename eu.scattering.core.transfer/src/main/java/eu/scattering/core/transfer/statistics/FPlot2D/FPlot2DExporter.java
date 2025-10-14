package eu.scattering.core.transfer.statistics.FPlot2D;

import eu.scattering.core.transfer.statistics.FStat1D.FStat1D;

public interface FPlot2DExporter {

    String toPythonPlotlyLinear(FPlot2D... plot);

    String toPythonPlotlyHistogram(FStat1D... stat);
    String toPythonPlotlyHistogram(FPlot2D... plot);

    // -------------------------------------------------------------------------------------------------

    String getName();
    void setName(String name);

    String getNameX();
    void setNameX(String nameX);

    String getNameY();
    void setNameY(String nameY);
}
