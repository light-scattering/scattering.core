package eu.scattering.core.design.helpers.engine;

import eu.scattering.core.design.elements.engine.random.FRandom;

public interface RandomHelperFactory {

    FRandomHelper getFRandomHelper(FRandom random);
}
