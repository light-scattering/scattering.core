package eu.scattering.core.design.statistics.construct.plot;

public interface FPlotAspectExport {

    String toPythonPlotlyLinear(FPlot... plot);
    String toPythonPlotlyHistogram(FPlot... plot);
}
