package eu.scattering.core.design.statistics.construct;

public interface FPlotEngineExport {

    String exportPythonPlotlyLinear(FPlot... plot);
    String exportPythonPlotlyHistogram(FPlot... plot);
}
