package eu.scattering.core.design.component.aggregate.model;

import eu.scattering.core.design.component.aggregate.model.cc.FModelCCFactory;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPCFactory;

public interface FModelFactoryContext {

    FModelPCFactory pc();
    FModelCCFactory cc();
}
