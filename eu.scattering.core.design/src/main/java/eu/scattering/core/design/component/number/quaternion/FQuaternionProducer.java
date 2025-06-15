package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.util.support.Producer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FQuaternionProducer extends Producer<FQuaternion> {

    @Override
    FQuaternion produce();
    @Override
    Stream<FQuaternion> stream();

    List<FQuaternion> getListAuto();
    List<FQuaternion> getListRandomized(int quantity);
    List<FQuaternion> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    FQuaternionProducer withCustomRule(Function<FQuaternionFactory, FQuaternion> function, int weight);
    FQuaternionProducer withCustomRule(BiFunction<FQuaternionFactory, FRandGenerator, FQuaternion> function, int weight);

    FQuaternionProducer withZero(int weight);

    // -------------------------------------------------------------------------------------------------

    default FQuaternionProducer withCustomRule(Function<FQuaternionFactory, FQuaternion> function) {

        return withCustomRule(function, 1);
    }

    default FQuaternionProducer withCustomRule(BiFunction<FQuaternionFactory, FRandGenerator, FQuaternion> function) {

        return withCustomRule(function, 1);
    }

    default FQuaternionProducer withZero() {

        return withZero(1);
    }
}
