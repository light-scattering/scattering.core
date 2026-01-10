package eu.scattering.core.design.component.aggregate.model.pc;

import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallisticFactory;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelPCDLAFactory;
import eu.scattering.core.design.component.aggregate.model.pc.rla.FModelPCRLAFactory;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunableFactory;

public interface FModelPCFactoryContext extends FModelPCTunableFactory, FModelPCRLAFactory, FModelPCBallisticFactory, FModelPCDLAFactory {
}
