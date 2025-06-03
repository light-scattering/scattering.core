package eu.scattering.core.design.component.geometry.shape.sphere;

import java.util.function.Function;

public interface FSphereProducer {

    void setConfig(Function<FSphere, FSphere> function);
    FSphereProducer addConfig(Function<FSphere, FSphere> function, double probability);

    FSphere produce();

    // -------------------------------------------------------------------------------------------------

    void setPresetFixRadius(String tag, double radius);
    FSphereProducer addPresetFixRadius(String tag, double radius, double probability);

    void setPresetRndRadius(String tag, double min, double max);
    FSphereProducer addPresetRndRadius(String tag, double min, double max, double probability);
}
