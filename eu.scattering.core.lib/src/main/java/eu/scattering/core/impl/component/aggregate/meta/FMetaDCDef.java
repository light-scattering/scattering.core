package eu.scattering.core.impl.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaDC;

public class FMetaDCDef extends FMetaDFDef implements FMetaDC {
    private int refs = -1;

    private FMetaDCDef() {}

    public static FMetaDC create() {

        return new FMetaDCDef();
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
