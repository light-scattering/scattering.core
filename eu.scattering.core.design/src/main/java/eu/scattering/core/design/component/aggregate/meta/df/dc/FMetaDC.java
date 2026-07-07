package eu.scattering.core.design.component.aggregate.meta.df.dc;

import eu.scattering.core.design.statistics.construct.plot.FPlot;

public interface FMetaDC {

    String getPlotApproximation();
    void setPlotApproximation(String plot);

    String getPlotDerivative();
    void setPlotDerivative(String plot);

    FPlot getData();
    void setData(FPlot data);

    int getReferenceParticleCount();
    void setReferenceParticleTime(int refs);

    long getExecutionTime();
    void setExecutionTime(long time);
}
