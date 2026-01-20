package eu.scattering.core.design.statistics.construct.plot;

public interface FPlotAspectExport {

    String toPythonPlotly(FPlot... plot);
    String toPythonPlotlyHistogram(FPlot... plot);
}
