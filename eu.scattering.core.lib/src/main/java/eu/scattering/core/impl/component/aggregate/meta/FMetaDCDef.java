package eu.scattering.core.impl.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.FMetaDC;

public class FMetaDCDef extends FMetaDFDef implements FMetaDC {
    private Script script = Script.DEFAULT;
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

    @Override
    public Script getScriptType() {

        return this.script;
    }

    @Override
    public void setScriptType(Script script) {

        this.script = script;
    }
}
