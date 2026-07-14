package eu.scattering.core.design.component.aggregate.meta.df.structural;

import eu.scattering.core.design.component.aggregate.meta.df.FMetaDF;

public interface FMetaDC extends FMetaDF {

    int getRefParticlesCount();
    void setRefParticlesCount(int count);

    Script getScriptType();
    void setScriptType(Script script);

    // -------------------------------------------------------------------------------------------------

    enum Script {
        DEFAULT, DERIVATIVE
    }
}
