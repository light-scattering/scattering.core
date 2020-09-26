package eu.scattering.core;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.support.helper.RandomHelper;
import eu.scattering.core.impl.production.FactoryDefault;
import eu.scattering.core.impl.production.FactoryDevelopment;

public final class Configuration {

    private Configuration() { }

    public static final Factory factory = FactoryDevelopment.create(FactoryDefault.create());
    public static final RandomHelper random = factory.getHelperRandom();
    public static final double jitter = factory.getJitter();
}
