package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FPointProducer extends Iterable<FPoint> {

    FPoint produce();
    Stream<FPoint> stream();

    // -------------------------------------------------------------------------------------------------

    FPointProducer withCustomRule(Function<FPointFactory, FPoint> function, int probability);

    FPointProducer withZero(int probability);
    FPointProducer withInsideSphere(double radius, int probability);
    FPointProducer withOnSphere(double radius, int probability);
    FPointProducer withInRange(FPairPos3D range, int probability);

    // -------------------------------------------------------------------------------------------------

    default FPointProducer withCustomRule(Function<FPointFactory, FPoint> function) {

        return withCustomRule(function, 1);
    }

    default FPointProducer withZero() {

        return withZero(1);
    }

    default FPointProducer withInsideSphere(double radius) {

        return withInsideSphere(radius, 1);
    }

    default FPointProducer withOnSphere(double radius) {

        return withOnSphere(radius, 1);
    }

    default FPointProducer withInRange(FPairPos3D range) {

        return withInRange(range, 1);
    }
}
