package eu.scattering.core.design.component.geometry.construct.plane;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FPlaneProducer extends Iterable<FPlane> {

    FPlane produce();
    Stream<FPlane> stream();

    // -------------------------------------------------------------------------------------------------

    FPlaneProducer withCustomRule(Function<FPlaneFactory, FPlane> function, int weight);

    FPlaneProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FPlaneProducer withCustomRule(Function<FPlaneFactory, FPlane> function) {

        return withCustomRule(function, 1);
    }

    default FPlaneProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
