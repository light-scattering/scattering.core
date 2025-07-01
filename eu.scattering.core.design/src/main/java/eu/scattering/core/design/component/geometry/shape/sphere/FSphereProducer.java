package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.util.support.Producer;
import eu.scattering.core.transfer.container.buffer.cache.FCache;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FSphereProducer extends Producer<FSphere> {

    @Override
    FSphere produce();
    @Override
    List<FSphere> getListAuto();
    @Override
    List<FSphere> getListRandomized(int quantity);
    @Override
    List<FSphere> getListFixed(int quantity);
    @Override
    Stream<FSphere> stream();

    // -------------------------------------------------------------------------------------------------

    FSphereProducer createCache();

    FSphereProducer setTag(String tag);
    FSphereProducer setCache(FCache cache);
    FSphereProducer setDelta(double delta);
    FSphereProducer setEpsilon(double epsilon);

    // -------------------------------------------------------------------------------------------------

    FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function, int weight);
    FSphereProducer withCustomRule(BiFunction<FSphereFactory, FRandEngine, FSphere> function, int weight);

    FSphereProducer withFixedRadius(double radius, int weight);
    FSphereProducer withDistRadius(FDist1D radius, int weight);

    FSphereProducer withCenterAndFixedRadius(Producer<FPoint> pCenter, double radius, int weight);
    FSphereProducer withCenterAndDistRadius(Producer<FPoint> pCenter, FDist1D radius, int weight);

    // -------------------------------------------------------------------------------------------------

    default FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function) {

        return withCustomRule(function, 1);
    }

    default FSphereProducer withCustomRule(BiFunction<FSphereFactory, FRandEngine, FSphere> function) {

        return withCustomRule(function, 1);
    }

    default FSphereProducer withFixedRadius(double radius) {

        return withFixedRadius(radius, 1);
    }

    default FSphereProducer withDistRadius(FDist1D radius) {

        return withDistRadius(radius, 1);
    }

    default FSphereProducer withCenterAndFixedRadius(Producer<FPoint> pCenter, double radius) {

        return withCenterAndFixedRadius(pCenter, radius, 1);
    }

    default FSphereProducer withCenterAndDistRadius(Producer<FPoint> pCenter, FDist1D radius) {

        return withCenterAndDistRadius(pCenter, radius, 1);
    }
}
