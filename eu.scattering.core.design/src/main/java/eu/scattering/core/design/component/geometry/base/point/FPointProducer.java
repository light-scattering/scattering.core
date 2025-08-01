package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.design.util.support.Producer;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FPointProducer extends Producer<FPoint> {

    @Override
    FPoint produce();
    @Override
    List<FPoint> getList();
    @Override
    List<FPoint> getListFixed(int quantity);
    @Override
    List<FPoint> getListRandomized(int quantity);
    @Override
    Stream<FPoint> stream();

    FPointProducer addMutation(Consumer<List<FPoint>> mutation);

    FPointProducer addValidation(BiFunction<FPoint, List<FPoint>, Boolean> validation);

    FPointProducer addCorrection(BiConsumer<FPoint, FRandGenerator> correction);

    // -------------------------------------------------------------------------------------------------

    FPointProducer withCustomRule(Function<FPointFactory, FPoint> function, int weight);
    FPointProducer withCustomRule(BiFunction<FPointFactory, FRandEngine, FPoint> function, int weight);

    FPointProducer withZero(int weight);

    FPointProducer withOnSphere(double radius, int weight);
    FPointProducer withInSphere(double radius, int weight);

    FPointProducer withInShell(double radiusMin, double radiusMax, int weight);

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

    default FPointProducer withOnSphere(double radius) {

        return withOnSphere(radius, 1);
    }

    default FPointProducer withInSphere(double radius) {

        return withInSphere(radius, 1);
    }

    default FPointProducer withInShell(double radiusMin, double radiusMax) {

        return withInShell(radiusMin, radiusMax, 1);
    }

    default FPointProducer withInRange(FPairPos3D range) {

        return withInRange(range, 1);
    }

    default FPointProducer withDist(FDist3D dist) {

        return withDist(dist, 1);
    }

    // -------------------------------------------------------------------------------------------------

    enum Location { IN_SPHERE, ON_SPHERE }
}
