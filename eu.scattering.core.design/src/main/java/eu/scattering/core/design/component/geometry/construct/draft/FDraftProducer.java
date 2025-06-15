package eu.scattering.core.design.component.geometry.construct.draft;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.util.support.Producer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FDraftProducer extends Producer<FDraft> {

    @Override
    FDraft produce();
    @Override
    Stream<FDraft> stream();

    List<FDraft> getListAuto();
    List<FDraft> getListRandomized(int quantity);
    List<FDraft> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    FDraftProducer withCustomRule(Function<FDraftFactory, FDraft> function, int weight);
    FDraftProducer withCustomRule(BiFunction<FDraftFactory, FRandEngine, FDraft> function, int weight);

    FDraftProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FDraftProducer withCustomRule(Function<FDraftFactory, FDraft> function) {

        return withCustomRule(function, 1);
    }

    default FDraftProducer withCustomRule(BiFunction<FDraftFactory, FRandEngine, FDraft> function) {

        return withCustomRule(function, 1);
    }

    default FDraftProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
