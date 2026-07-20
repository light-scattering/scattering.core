package eu.scattering.core.impl.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaMR;

public class FMetaMRDef extends FMetaDFDef implements FMetaMR {
    private int references = -1;

    private FMetaMRDef() {}

    public static FMetaMR create() {

        return new FMetaMRDef();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int getRefParticlesCount() {

        return this.references;
    }

    @Override
    public void setRefParticlesCount(int count) {

        this.references = count;
    }
}
