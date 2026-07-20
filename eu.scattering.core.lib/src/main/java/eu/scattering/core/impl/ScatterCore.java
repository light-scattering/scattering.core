package eu.scattering.core.impl;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.impl.factory.ScatterFactoryDef;

public final class ScatterCore {

    public static ScatterFactory createFactory() {

        return ScatterFactoryDef.create();
    }

    public static ScatterFactory createFactory(long seed) {

        return ScatterFactoryDef.create(seed);
    }
}
