package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.engine.randomize.FRandEngine;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FPlaneProducer {

    FPlane produce();

    Stream<FPlane> stream();

    List<FPlane> getListAuto();
    List<FPlane> getListRandomized(int quantity);
    List<FPlane> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    FPlaneProducer withCustomRule(Function<FPlaneFactory, FPlane> function, int weight);
    FPlaneProducer withCustomRule(BiFunction<FPlaneFactory, FRandEngine, FPlane> function, int weight);

    FPlaneProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FPlaneProducer withCustomRule(Function<FPlaneFactory, FPlane> function) {

        return withCustomRule(function, 1);
    }

    default FPlaneProducer withCustomRule(BiFunction<FPlaneFactory, FRandEngine, FPlane> function) {

        return withCustomRule(function, 1);
    }

    default FPlaneProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
