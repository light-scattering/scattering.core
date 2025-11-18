package eu.scattering.core.design.statistics.construct;

public interface FPlotExporter {

    String toPythonPlotlyLinear(FPlot... plot);
    String toPythonPlotlyHistogram(FPlot... plot);
}
