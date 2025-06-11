package eu.scattering.core.design.component.geometry.construct.draft;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FDraftProducer {

    FDraft produce();

    Stream<FDraft> stream();

    List<FDraft> getListAuto();
    List<FDraft> getListRandomized(int quantity);
    List<FDraft> getListFixed(int quantity);

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
