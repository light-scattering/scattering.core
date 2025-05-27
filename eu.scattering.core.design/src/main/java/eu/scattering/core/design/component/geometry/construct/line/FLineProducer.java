package eu.scattering.core.design.component.geometry.construct.line;

import java.util.function.Function;

public interface FLineProducer {

    FLineProducer setConfig(Function<FLine, FLine> function);
    FLineProducer addConfig(Function<FLine, FLine> function, double probability);

    FLine produce();

    // -------------------------------------------------------------------------------------------------

    FLineProducer setPresetEmpty();

    FLineProducer setPresetUnitX();
    FLineProducer setPresetUnitY();
    FLineProducer setPresetUnitZ();
}
