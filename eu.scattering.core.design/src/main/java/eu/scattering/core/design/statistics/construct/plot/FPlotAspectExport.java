package eu.scattering.core.design.statistics.construct.plot;

public interface FPlotAspectExport {

    String toPythonPlotly(FPlotMeta config, FPlot... plot);

    String toPythonPlotlyHistogram(FPlotMeta config, FPlot... plot);

    //--------------------------------------------------

    default String toPythonPlotly(FPlot... plot) {

        return toPythonPlotly(null, plot);
    }

    default String toPythonPlotlyHistogram(FPlot... plot) {

        return toPythonPlotlyHistogram(null, plot);
    }
}
