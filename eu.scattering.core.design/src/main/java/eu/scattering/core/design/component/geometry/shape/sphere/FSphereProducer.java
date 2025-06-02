package eu.scattering.core.design.component.geometry.shape.sphere;

import java.util.function.Function;

public interface FSphereProducer {

    FSphereProducer setConfig(Function<FSphere, FSphere> function, double probability);
    FSphereProducer addConfig(Function<FSphere, FSphere> function, double probability);

    FSphere produce();

    // -------------------------------------------------------------------------------------------------

    FSphereProducer setPresetDefault();

    FSphereProducer setPresetRndRadius(double min, double max);

    // -------------------------------------------------------------------------------------------------

    default FSphereProducer setConfig(Function<FSphere, FSphere> function) {

        return setConfig(function, 1);
    }
}
