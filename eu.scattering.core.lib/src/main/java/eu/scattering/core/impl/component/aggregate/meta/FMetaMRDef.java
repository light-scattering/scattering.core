package eu.scattering.core.impl.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.FMetaMR;

public class FMetaMRDef extends FMetaDFDef implements FMetaMR {
    private int refs = -1;

    private FMetaMRDef() {}

    public static FMetaMR create() {

        return new FMetaMRDef();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int getRefParticlesCount() {

        return this.refs;
    }

    @Override
    public void setRefParticlesCount(int count) {

        this.refs = count;
    }
}
