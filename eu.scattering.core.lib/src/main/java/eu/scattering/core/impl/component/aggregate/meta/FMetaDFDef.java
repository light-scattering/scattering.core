package eu.scattering.core.impl.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.FMetaDF;
import eu.scattering.core.design.statistics.construct.plot.FPlot;

public class FMetaDFDef implements FMetaDF {
    private long milliseconds;
    private String script;
    private FPlot data;

    protected FMetaDFDef() {}

    public static FMetaDF create() {

        return new FMetaDFDef();
    }

    @Override
    public String getPythonRenderScript() {

        return this.script;
    }

    @Override
    public void setPythonRenderScript(String script) {

        if (script == null || script.isBlank()) {
            throw new IllegalArgumentException("The script cannot be empty");
        }

        this.script = script;
    }

    @Override
    public long getExecutionTimeMillis() {

        return this.milliseconds;
    }

    @Override
    public void setExecutionTimeMillis(long millis) {

        if (millis < 0) {
            throw new IllegalArgumentException("The time cannot be lower than zero");
        }

        this.milliseconds = millis;
    }

    @Override
    public FPlot getRefData() {

        return this.data;
    }

    @Override
    public void setRefData(FPlot data) {

        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null");
        }

        this.data = data;
    }
}
