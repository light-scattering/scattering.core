package eu.scattering.core.design.component.aggregate.meta.df.kinetic.cc;

public interface FMetaCCPL {

    String getPythonRenderScript(Plot type);
    void setPythonRenderScript(Plot type, String script);

    // -------------------------------------------------------------------------------------------------

    default String getPythonRenderScript() {

        return getPythonRenderScript(Plot.PARSED);
    }

    // -------------------------------------------------------------------------------------------------

    enum Plot {
        RAW, PARSED
    }
}
