package eu.scattering.core.design.component.aggregate.meta.df;

public interface FMetaMR extends FMetaDF {

    int getRefParticlesCount();
    void setRefParticlesCount(int count);

    // -------------------------------------------------------------------------------------------------

    enum Script {
        DEFAULT, DERIVATIVE
    }
}
