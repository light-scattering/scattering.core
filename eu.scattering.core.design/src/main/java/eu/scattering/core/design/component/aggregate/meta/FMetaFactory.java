package eu.scattering.core.design.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.FMetaDF;
import eu.scattering.core.design.component.aggregate.meta.df.FMetaBC;
import eu.scattering.core.design.component.aggregate.meta.df.FMetaDC;
import eu.scattering.core.design.component.aggregate.meta.df.FMetaMR;

public interface FMetaFactory {

    FMetaDF getFMetaDF();

    FMetaBC getFMetaBC();
    FMetaDC getFMetaDC();
    FMetaMR getFMetaMR();
}
