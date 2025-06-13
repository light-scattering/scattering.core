package eu.scattering.core.design.engine.randomize;

import eu.scattering.core.design.engine.Engine;
import eu.scattering.core.design.component.ComponentEngineRand;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;

public interface FRandEngine extends ComponentEngineRand, Engine<FRandEngine> {

    FRandGenerator getFRand();
}
