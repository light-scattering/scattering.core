package eu.scattering.core.impl.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.bc.FMetaBC;

public class FMetaBCDef extends FMetaDFDef implements FMetaBC {

    private FMetaBCDef() {}

    public static FMetaBC create() {

        return new FMetaBCDef();
    }
}
