package eu.scattering.core.design.component.geometry.shape.sphere;

import java.util.function.Function;

public interface FSphereProducer {

    FSphereProducer setConfig(Function<FSphere, FSphere> function);
    FSphereProducer addConfig(Function<FSphere, FSphere> function, double probability);

    FSphere produce();

    // -------------------------------------------------------------------------------------------------

    FSphereProducer setPresetEmpty();

    FSphereProducer setPresetRndRadius(double min, double max);
}
