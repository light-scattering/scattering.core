package eu.scattering.core.design.component.aggregate.model.pc;

import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallisticFactory;
import eu.scattering.core.design.component.aggregate.model.pc.rla.FModelRLAFactory;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunableFactory;

public interface FModelPCFactory extends FModelPCTunableFactory, FModelRLAFactory, FModelPCBallisticFactory {
}
