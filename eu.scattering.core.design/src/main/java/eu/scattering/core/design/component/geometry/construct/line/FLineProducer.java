package eu.scattering.core.design.component.geometry.construct.line;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public interface FLineProducer {

    FLineProducer setConfig(Function<FLine, FLine> function, double probability);
    FLineProducer addConfig(Function<FLine, FLine> function, double probability);

    FLine produce();

    // -------------------------------------------------------------------------------------------------

    FLineProducer setPresetOX();
    FLineProducer setPresetOY();
    FLineProducer setPresetOZ();

    FLineProducer setPresetFixedPoint(FPos3D point);

    // -------------------------------------------------------------------------------------------------

    default FLineProducer setConfig(Function<FLine, FLine> function) {

        return setConfig(function, 1);
    }
}
