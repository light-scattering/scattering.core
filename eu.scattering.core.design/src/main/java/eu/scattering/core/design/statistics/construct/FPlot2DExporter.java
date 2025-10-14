package eu.scattering.core.design.statistics.construct;

public interface FPlot2DExporter {

    String toPythonPlotlyLinear(FPlot2D... plot);
    String toPythonPlotlyHistogram(FPlot2D... plot);
}
