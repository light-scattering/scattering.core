package eu.scattering.core.design.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.df.*;
import eu.scattering.core.design.component.aggregate.meta.df.kinetic.cc.FMetaCCPL;
import eu.scattering.core.design.component.aggregate.meta.df.kinetic.pc.FMetaPCPL;
import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaBC;
import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaDC;
import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaMR;

public interface FMetaFactory {

    FMetaDF getFMetaDF();

    FMetaBC getFMetaBC();
    FMetaDC getFMetaDC();
    FMetaMR getFMetaMR();

    FMetaPCPL getFMetaPCPL();
    FMetaCCPL getFMetaCCPL();
}
