package eu.scattering.core.design.component.geometry.construct.draft;

import java.util.function.Function;

public interface FDraftProducer {

    FDraftProducer setConfig(Function<FDraft, FDraft> function, double probability);
    FDraftProducer addConfig(Function<FDraft, FDraft> function, double probability);

    FDraft produce();

    // -------------------------------------------------------------------------------------------------

    FDraftProducer setPresetUnitX();
    FDraftProducer setPresetUnitY();
    FDraftProducer setPresetUnitZ();

    // -------------------------------------------------------------------------------------------------

    default FDraftProducer setConfig(Function<FDraft, FDraft> function) {

        return setConfig(function, 1);
    }
}
