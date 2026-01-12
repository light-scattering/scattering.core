package eu.scattering.core.design.component.aggregate.model.cc;

import eu.scattering.core.design.component.aggregate.model.cc.ballistic.FModelCCBallisticFactory;
import eu.scattering.core.design.component.aggregate.model.cc.rlca.FModelCCRLCAFactory;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunableFactory;

public interface FModelCCFactoryContext extends FModelCCBallisticFactory, FModelCCRLCAFactory, FModelCCTunableFactory {
}
