package eu.scattering.core.design.component.aggregate.meta.bc;

import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.utility.annotation.Modificator;

public interface FMetaBC {

    String getPythonRenderScript();
    void setPythonRenderScript(String script);

    long getExecutionTimeMillis();
    void setExecutionTimeMillis(long millis);

    @Modificator
    FPlot getRefData();
    @Modificator
    void setRefData(FPlot data);
}
