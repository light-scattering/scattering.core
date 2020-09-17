package eu.scattering.core;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.support.helper.RandomHelper;
import eu.scattering.core.implementation.FactoryDefault;
import eu.scattering.core.implementation.FactoryDevelopment;

public final class Configuration {

    private Configuration() { }

    static final Factory factory = FactoryDevelopment.create(FactoryDefault.create());
    static final RandomHelper random = factory.getHelperRandom();
    static final double jitter = 1E-8;
}
