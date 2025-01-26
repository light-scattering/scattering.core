package eu.scattering.core.test;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.engines.random.processor.FRandomProcessor;
import eu.scattering.core.design.engines.rotation.FRotationEngine;
import eu.scattering.core.impl.FactoryProd;

public final class Configuration {

    private Configuration() {
    }

    public static final FactoryDesignConcrete factory = FactoryProd.create();
    public static final double jitter = 1E-8;
    public static final FRandomProcessor random = factory.getFRandomProcessor();
    public static final FRotationEngine rotation = factory.getFRotationEngine();

    static {
        factory.initialize();
    }
}
