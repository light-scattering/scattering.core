package eu.scattering.core.design.component.geometry.construct.line;

import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public interface FLineProducer {

    void setConfig(Function<FLine, FLine> function);
    FLineProducer addConfig(Function<FLine, FLine> function, double probability);

    FLine produce();

    // -------------------------------------------------------------------------------------------------

    void setPresetOX();
    FLineProducer addPresetOX(double probability);

    void setPresetOY();
    FLineProducer addPresetOY(double probability);

    void setPresetOZ();
    FLineProducer addPresetOZ(double probability);

    void setPresetFixedPoint(FPos3D point);
    FLineProducer addPresetFixedPoint(FPos3D point, double probability);
}
