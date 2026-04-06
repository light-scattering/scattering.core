package eu.scattering.core.design.statistics.construct.plot;

public interface FPlotAspectSave {

    String toCLI(FPlot plot);

    String toPythonPlotly(FPlotMetaGlobal config, FPlot... plot);

    String toPythonPlotlyHistogram(FPlotMetaGlobal config, FPlot... plot);

    //--------------------------------------------------

    default String toPythonPlotly(FPlot... plot) {

        return toPythonPlotly(null, plot);
    }

    default String toPythonPlotlyHistogram(FPlot... plot) {

        return toPythonPlotlyHistogram(null, plot);
    }
}
