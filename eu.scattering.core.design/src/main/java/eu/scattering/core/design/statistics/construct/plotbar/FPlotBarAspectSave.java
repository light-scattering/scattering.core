package eu.scattering.core.design.statistics.construct.plotbar;

public interface FPlotBarAspectSave {

    String toPythonPlotly(FPlotBarMeta config, FPlotBar plotBar);

    //--------------------------------------------------

    default String toPythonPlotly(FPlotBar plotBar) {

        return toPythonPlotly(null, plotBar);
    }
}
