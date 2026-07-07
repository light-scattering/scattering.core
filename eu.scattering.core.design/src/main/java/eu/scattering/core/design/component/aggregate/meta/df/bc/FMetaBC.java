package eu.scattering.core.design.component.aggregate.meta.df.bc;

import eu.scattering.core.design.component.aggregate.meta.df.FMetaDF;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.utility.annotation.Modificator;

public interface FMetaBC extends FMetaDF {

    String getPythonRenderScript();
    void setPythonRenderScript(String script);

    long getExecutionTimeMillis();
    void setExecutionTimeMillis(long millis);

    @Modificator
    FPlot getRefData();
    @Modificator
    void setRefData(FPlot data);
}
