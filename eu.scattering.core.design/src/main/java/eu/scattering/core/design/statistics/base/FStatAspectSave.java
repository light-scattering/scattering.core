package eu.scattering.core.design.statistics.base;

public interface FStatAspectSave {

    String toCLI(FStat stat);

    String toPythonPlotlyHistogram(FStatMeta config, FStat... stat);

    //--------------------------------------------------

    default String toPythonPlotlyHistogram(FStat... stat) {

        return toPythonPlotlyHistogram(null, stat);
    }
}
