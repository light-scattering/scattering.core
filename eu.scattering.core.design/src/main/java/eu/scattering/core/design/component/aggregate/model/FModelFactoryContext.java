package eu.scattering.core.design.component.aggregate.model;

import eu.scattering.core.design.component.aggregate.model.cc.FModelCCFactoryContext;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPCFactoryContext;

public interface FModelFactoryContext {

    FModelPCFactoryContext pc();
    FModelCCFactoryContext cc();
}
