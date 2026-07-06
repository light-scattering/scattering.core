package eu.scattering.core.design.component.aggregate.meta;

import eu.scattering.core.design.component.aggregate.meta.bc.FMetaBC;
import eu.scattering.core.design.component.aggregate.meta.dc.FMetaDC;

public interface FMetaFactory {

    FMetaBC getFMetaBC();
    FMetaDC getFMetaDC();
}
