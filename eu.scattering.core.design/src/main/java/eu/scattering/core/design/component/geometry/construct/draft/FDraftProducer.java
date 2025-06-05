package eu.scattering.core.design.component.geometry.construct.draft;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FDraftProducer extends Iterable<FDraft> {

    FDraft produce();
    Stream<FDraft> stream();

    // -------------------------------------------------------------------------------------------------

    FDraftProducer withCustomRule(Function<FDraftFactory, FDraft> function, int weight);

    FDraftProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FDraftProducer withCustomRule(Function<FDraftFactory, FDraft> function) {

        return withCustomRule(function, 1);
    }

    default FDraftProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
