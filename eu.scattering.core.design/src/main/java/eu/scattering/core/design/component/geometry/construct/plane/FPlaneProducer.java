package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.util.support.Producer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FPlaneProducer extends Producer<FPlane> {

    @Override
    FPlane produce();
    @Override
    List<FPlane> getListAuto();
    @Override
    List<FPlane> getListRandomized(int quantity);
    @Override
    List<FPlane> getListFixed(int quantity);
    @Override
    Stream<FPlane> stream();

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
