package eu.scattering.core.design.component.geometry.construct.ray;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FRayProducer extends Iterable<FRay> {

    FRay produce();
    Stream<FRay> stream();

    // -------------------------------------------------------------------------------------------------

    FRayProducer withCustomRule(Function<FRayFactory, FRay> function, int weight);

    FRayProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FRayProducer withCustomRule(Function<FRayFactory, FRay> function) {

        return withCustomRule(function, 1);
    }

    default FRayProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
