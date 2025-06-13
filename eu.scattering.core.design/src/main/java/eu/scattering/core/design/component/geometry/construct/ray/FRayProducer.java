package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.engine.randomize.FRandEngine;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FRayProducer {

    FRay produce();

    Stream<FRay> stream();

    List<FRay> getListAuto();
    List<FRay> getListRandomized(int quantity);
    List<FRay> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    FRayProducer withCustomRule(Function<FRayFactory, FRay> function, int weight);
    FRayProducer withCustomRule(BiFunction<FRayFactory, FRandEngine, FRay> function, int weight);

    FRayProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FRayProducer withCustomRule(Function<FRayFactory, FRay> function) {

        return withCustomRule(function, 1);
    }

    default FRayProducer withCustomRule(BiFunction<FRayFactory, FRandEngine, FRay> function) {

        return withCustomRule(function, 1);
    }

    default FRayProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
