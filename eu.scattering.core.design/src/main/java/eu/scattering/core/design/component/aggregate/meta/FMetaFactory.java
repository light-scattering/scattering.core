package eu.scattering.core.design.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.FMetaDF;
import eu.scattering.core.design.component.aggregate.meta.df.bc.FMetaBC;
import eu.scattering.core.design.component.aggregate.meta.df.dc.FMetaDC;

public interface FMetaFactory {

    FMetaDF getFMetaDF();

    FMetaBC getFMetaBC();
    FMetaDC getFMetaDC();
}
