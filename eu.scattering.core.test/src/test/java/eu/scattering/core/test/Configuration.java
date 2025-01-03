package eu.scattering.core.test;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.mutables.engine.random.FRandom;
import eu.scattering.core.design.helpers.engine.FRotationHelper;
import eu.scattering.core.impl.production.FactoryProd;

public final class Configuration {

    private Configuration() {
    }

    public static final FactoryDesignConcrete factory = FactoryProd.create();
    public static final double jitter = 1E-8;
    public static final FRandom random = factory.getFRandom();
    public static final FRotationHelper rotation = factory.getFRotationHelper();


}
