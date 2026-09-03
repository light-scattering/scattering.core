package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.functionality.Producer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FQuaternionProducer extends Producer<FQuaternion> {

    @Override
    FQuaternion produce();
    @Override
    List<FQuaternion> getList();
    @Override
    List<FQuaternion> getListRandomized(int quantity);
    @Override
    List<FQuaternion> getListFixed(int quantity);
    @Override
    Stream<FQuaternion> stream();

    @Override
    FQuaternionProducer setRetriesLimited(int limit);
    @Override
    FQuaternionProducer setRetriesInfinite();
    @Override
    FQuaternionProducer setSkipOnFailure(boolean skip);

    // -------------------------------------------------------------------------------------------------

    FQuaternionProducer withCustomRule(Function<FQuaternionFactory, FQuaternion> function, int weight);
    FQuaternionProducer withCustomRule(BiFunction<FQuaternionFactory, FRandEngine, FQuaternion> function, int weight);

    FQuaternionProducer withZero(int weight);

    // -------------------------------------------------------------------------------------------------

    default FQuaternionProducer withCustomRule(Function<FQuaternionFactory, FQuaternion> function) {

        return withCustomRule(function, 1);
    }

    default FQuaternionProducer withCustomRule(BiFunction<FQuaternionFactory, FRandEngine, FQuaternion> function) {

        return withCustomRule(function, 1);
    }

    default FQuaternionProducer withZero() {

        return withZero(1);
    }
}
