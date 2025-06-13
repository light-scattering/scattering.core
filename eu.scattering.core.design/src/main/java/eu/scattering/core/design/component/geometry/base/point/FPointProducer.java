package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FPointProducer {

    FPoint produce();

    Stream<FPoint> stream();

    List<FPoint> getListAuto();
    List<FPoint> getListRandomized(int quantity);
    List<FPoint> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    FPointProducer withCustomRule(Function<FPointFactory, FPoint> function, int weight);
    FPointProducer withCustomRule(BiFunction<FPointFactory, FRandEngine, FPoint> function, int weight);

    FPointProducer withZero(int weight);

    FPointProducer withInSphere(double radius, int weight);
    FPointProducer withRadius(double radius, int weight);

    FPointProducer withInRange(FPairPos3D range, int weight);

    FPointProducer withDist(FDist3D dist, int weight);

    // -------------------------------------------------------------------------------------------------

    default FPointProducer withCustomRule(Function<FPointFactory, FPoint> function) {

        return withCustomRule(function, 1);
    }

    default FPointProducer withCustomRule(BiFunction<FPointFactory, FRandEngine, FPoint> function) {

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

    default FPointProducer withDist(FDist3D dist) {

        return withDist(dist, 1);
    }
}
