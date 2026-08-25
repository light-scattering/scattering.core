package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.functionality.Producer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FRayProducer extends Producer<FRay> {

    @Override
    FRay produce();
    @Override
    List<FRay> getList();
    @Override
    List<FRay> getListRandomized(int quantity);
    @Override
    List<FRay> getListFixed(int quantity);
    @Override
    Stream<FRay> stream();

    @Override
    FRayProducer setRetriesLimited(int limit);
    @Override
    FRayProducer setRetriesInfinite();
    @Override
    FRayProducer setSkipOnFailure(boolean skip);

    // -------------------------------------------------------------------------------------------------

    FRayProducer withCustomRule(Function<FRayFactory, FRay> function, int weight);
    FRayProducer withCustomRule(BiFunction<FRayFactory, FRandAspect, FRay> function, int weight);

    FRayProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FRayProducer withCustomRule(Function<FRayFactory, FRay> function) {

        return withCustomRule(function, 1);
    }

    default FRayProducer withCustomRule(BiFunction<FRayFactory, FRandAspect, FRay> function) {

        return withCustomRule(function, 1);
    }

    default FRayProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
