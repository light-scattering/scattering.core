package eu.scattering.core.test;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.helpers.random.FRandomHelper;
import eu.scattering.core.impl.production.FactoryProd;

public final class Configuration {

    private Configuration() { }

    public static final Factory factory = FactoryProd.create();
    public static final FRandomHelper random = factory.getFRandomHelper();
    public static final double jitter = factory.getJitter();
}
