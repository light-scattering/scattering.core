package eu.scattering.core.design.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.FMetaDF;
import eu.scattering.core.design.component.aggregate.meta.df.FMetaBC;
import eu.scattering.core.design.component.aggregate.meta.df.FMetaDC;

public interface FMetaFactory {

    FMetaDF getFMetaDF();

    FMetaBC getFMetaBC();
    FMetaDC getFMetaDC();
}
