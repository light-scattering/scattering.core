package eu.scattering.core.design.elements;

import eu.scattering.core.design.transfers.TransfersFactory;
import eu.scattering.core.design.elements.algebra.AlgebraFactory;
import eu.scattering.core.design.elements.engine.EngineFactory;

public interface CoreFactory extends AlgebraFactory, EngineFactory, TransfersFactory {
}
