package eu.scattering.core.transfer.statistics.FPlot2D;

public interface FPlot2DExporter {

    String toPythonPlotly(FPlot2D chart);
    String toPythonPlotly(FPlot2D... chart);
}
