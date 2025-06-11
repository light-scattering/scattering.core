package eu.scattering.core.design.component.geometry.construct.line;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FLineProducer{

    FLine produce();

    Stream<FLine> stream();

    List<FLine> getListAuto();
    List<FLine> getListRandomized(int quantity);
    List<FLine> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    FLineProducer withCustomRule(Function<FLineFactory, FLine> function, int weight);

    FLineProducer withFVector(FVectorProducer origin, int weight);

    // -------------------------------------------------------------------------------------------------

    default FLineProducer withCustomRule(Function<FLineFactory, FLine> function) {

        return withCustomRule(function, 1);
    }

    default FLineProducer withFVector(FVectorProducer origin) {

        return withFVector(origin, 1);
    }
}
