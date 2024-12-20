package eu.scattering.core.design;

import eu.scattering.core.design.elements.data.DataFactory;

public class FactoryPrimitive implements DataFactory {

    private FactoryPrimitive() {}

    public static FactoryPrimitive create() {

        return new FactoryPrimitive();
    }

}
