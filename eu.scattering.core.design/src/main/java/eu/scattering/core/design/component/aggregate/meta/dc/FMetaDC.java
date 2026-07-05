package eu.scattering.core.design.component.aggregate.meta.dc;

import eu.scattering.core.design.statistics.construct.plot.FPlot;

public interface FMetaDC {

    String getPlotApproximation();
    void setPlotApproximation(String plot);

    String getPlotDerivative();
    void setPlotDerivative(String plot);

    FPlot getData();
    void setData(FPlot data);

    int getNumberOfRefs();
    void setNumberOfRefs(int refs);

    long getExecutionTime();
    void setExecutionTime(long time);
}
