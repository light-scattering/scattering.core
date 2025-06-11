package eu.scattering.core.design.component.number.quaternion;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FQuaternionProducer {

    FQuaternion produce();

    Stream<FQuaternion> stream();

    List<FQuaternion> getListAuto();
    List<FQuaternion> getListRandomized(int quantity);
    List<FQuaternion> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    FQuaternionProducer withCustomRule(Function<FQuaternionFactory, FQuaternion> function, int weight);

    FQuaternionProducer withZero(int weight);

    // -------------------------------------------------------------------------------------------------

    default FQuaternionProducer withCustomRule(Function<FQuaternionFactory, FQuaternion> function) {

        return withCustomRule(function, 1);
    }

    default FQuaternionProducer withZero() {

        return withZero(1);
    }
}
