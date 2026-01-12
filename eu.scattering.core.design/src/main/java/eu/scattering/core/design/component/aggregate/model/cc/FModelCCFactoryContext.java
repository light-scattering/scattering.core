package eu.scattering.core.design.component.aggregate.model.cc;

import eu.scattering.core.design.component.aggregate.model.cc.ballistic.FModelCCBallisticFactory;
import eu.scattering.core.design.component.aggregate.model.cc.rlca.FModelCCRLCAFactory;

public interface FModelCCFactoryContext extends FModelCCBallisticFactory, FModelCCRLCAFactory {
}
