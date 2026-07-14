package eu.scattering.core.impl.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.kinetic.pc.FMetaPCPL;

public class FMetaPCPLDef implements FMetaPCPL {
    private String script;

    private FMetaPCPLDef() {}

    public static FMetaPCPL create() {

        return new FMetaPCPLDef();
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
}
