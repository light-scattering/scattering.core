package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FPointProducer extends Iterable<FPoint> {

    FPoint produce();
    Stream<FPoint> stream();

    // -------------------------------------------------------------------------------------------------

    FPointProducer withCustomRule(Function<FPointFactory, FPoint> function, int weight);

    FPointProducer withZero(int weight);

    FPointProducer withInSphere(double radius, int weight);
    FPointProducer withRadius(double radius, int weight);

    FPointProducer withInRange(FPairPos3D range, int weight);

    // -------------------------------------------------------------------------------------------------

    default FPointProducer withCustomRule(Function<FPointFactory, FPoint> function) {

        return withCustomRule(function, 1);
    }

    default FPointProducer withZero() {

        return withZero(1);
    }

    default FPointProducer withInSphere(double radius) {

        return withInSphere(radius, 1);
    }

    default FPointProducer withRadius(double radius) {

        return withRadius(radius, 1);
    }

    default FPointProducer withInRange(FPairPos3D range) {

        return withInRange(range, 1);
    }
}
