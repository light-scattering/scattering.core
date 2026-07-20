package eu.scattering.core.design.component.aggregate.meta.df;

import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.utility.annotation.Modificator;

public interface FMetaDF {

    String getPythonRenderScript();
    void setPythonRenderScript(String script);

    long getExecutionTimeMillis();
    void setExecutionTimeMillis(long millis);

    @Modificator
    FPlot getRefData();
    @Modificator
    void setRefData(FPlot data);
}
