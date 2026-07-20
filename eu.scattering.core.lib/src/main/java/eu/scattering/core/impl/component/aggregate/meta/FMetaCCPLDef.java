package eu.scattering.core.impl.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.kinetic.cc.FMetaCCPL;

public class FMetaCCPLDef implements FMetaCCPL {
    private String scriptParsed;
    private String scriptRaw;

    private FMetaCCPLDef() {}

    public static FMetaCCPL create() {

        return new FMetaCCPLDef();
    }

    @Override
    public String getPythonRenderScript(Plot type) {

        return switch (type) {
            case RAW -> this.scriptRaw;
            case PARSED -> this.scriptParsed;
        };
    }

    @Override
    public void setPythonRenderScript(Plot type, String script) {

        if (script == null || script.isBlank()) {
            throw new IllegalArgumentException("The script cannot be empty");
        }

        switch (type) {
            case RAW -> this.scriptRaw = script;
            case PARSED -> this.scriptParsed = script;
        }
    }
}
