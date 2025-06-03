package eu.scattering.core.design.component.geometry.construct.draft;

import java.util.function.Function;

public interface FDraftProducer {

    void setConfig(Function<FDraft, FDraft> function);
    FDraftProducer addConfig(Function<FDraft, FDraft> function, double probability);

    FDraft produce();

    // -------------------------------------------------------------------------------------------------

    void setPresetUnitX();
    FDraftProducer addPresetUnitX(double probability);

    void setPresetUnitY();
    FDraftProducer addPresetUnitY(double probability);

    void setPresetUnitZ();
    FDraftProducer addPresetUnitZ(double probability);
}
