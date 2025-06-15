package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.util.support.Producer;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FSphereProducer extends Producer<FSphere> {

    @Override
    FSphere produce();

    Stream<FSphere> stream();

    List<FSphere> getListAuto();
    List<FSphere> getListRandomized(int quantity);
    List<FSphere> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function, int weight);
    FSphereProducer withCustomRule(BiFunction<FSphereFactory, FRandEngine, FSphere> function, int weight);

    FSphereProducer withFixedRadius(String tag, double radius, int weight);
    FSphereProducer withDistRadius(String tag, FDist1D radius, int weight);

    FSphereProducer withCenterAndFixedRadius(String tag, FPointProducer pCenter, double radius, int weight);
    FSphereProducer withCenterAndDistRadius(String tag, FPointProducer pCenter, FDist1D radius, int weight);

    // -------------------------------------------------------------------------------------------------

    default FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function) {

        return withCustomRule(function, 1);
    }

    default FSphereProducer withCustomRule(BiFunction<FSphereFactory, FRandEngine, FSphere> function) {

        return withCustomRule(function, 1);
    }

    default FSphereProducer withFixedRadius(String tag, double radius) {

        return withFixedRadius(tag, radius, 1);
    }

    default FSphereProducer withDistRadius(String tag, FDist1D radius) {

        return withDistRadius(tag, radius, 1);
    }

    default FSphereProducer withCenterAndFixedRadius(String tag, FPointProducer pCenter, double radius) {

        return withCenterAndFixedRadius(tag, pCenter, radius, 1);
    }

    default FSphereProducer withCenterAndDistRadius(String tag, FPointProducer pCenter, FDist1D radius) {

        return withCenterAndDistRadius(tag, pCenter, radius, 1);
    }
}
