package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1D;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3D;
import eu.scattering.core.design.functionality.Producer;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FSphereProducer extends Producer<FSphere> {

    @Override
    FSphere produce();
    @Override
    List<FSphere> getList();
    @Override
    List<FSphere> getListFixed(int quantity);
    @Override
    List<FSphere> getListRandomized(int quantity);
    @Override
    Stream<FSphere> stream();

    @Override
    FSphereProducer setRetriesLimited(int limit);
    @Override
    FSphereProducer setRetriesInfinite();
    @Override
    FSphereProducer setSkipOnFailure(boolean skip);

    FSphereProducer addMutation(Consumer<List<FSphere>> mutation);

    FSphereProducer addCorrection(BiConsumer<FSphere, FRandEngine> correction);

    FSphereProducer addValidation(BiFunction<FSphere, List<FSphere>, Boolean> validation);

    // -------------------------------------------------------------------------------------------------

    FSphereProducer setMeta(String... meta);

    FSphereProducer setDelta(double delta);
    FSphereProducer setEpsilon(double epsilon);

    FSphereProducer mutateAddCoat(double... width);

    FSphereProducer correctAddCoat(double... width);

    FSphereProducer validateNoOverlap();

    // -------------------------------------------------------------------------------------------------

    FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function, int weight);
    FSphereProducer withCustomRule(BiFunction<FSphereFactory, FRandAspect, FSphere> function, int weight);

    FSphereProducer withFixRadius(double radius, int weight);
    FSphereProducer withDistRadius(FRandDist1D radius, int weight);

    FSphereProducer withDistCenterAndFixRadius(FRandDist3D dCenter, double radius, int weight);
    FSphereProducer withDistCenterAndDistRadius(FRandDist3D dCenter, FRandDist1D radius, int weight);

    FSphereProducer withProdCenterAndFixRadius(Producer<FPoint> pCenter, double radius, int weight);
    FSphereProducer withProdCenterAndDistRadius(Producer<FPoint> pCenter, FRandDist1D radius, int weight);

    // -------------------------------------------------------------------------------------------------

    default FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function) {

        return withCustomRule(function, 1);
    }

    default FSphereProducer withCustomRule(BiFunction<FSphereFactory, FRandAspect, FSphere> function) {

        return withCustomRule(function, 1);
    }

    default FSphereProducer withFixRadius(double radius) {

        return withFixRadius(radius, 1);
    }

    default FSphereProducer withDistRadius(FRandDist1D radius) {

        return withDistRadius(radius, 1);
    }

    default FSphereProducer withDistCenterAndFixRadius(FRandDist3D dCenter, double radius) {

        return withDistCenterAndFixRadius(dCenter, radius, 1);
    }

    default FSphereProducer withDistCenterAndDistRadius(FRandDist3D dCenter, FRandDist1D radius) {

        return withDistCenterAndDistRadius(dCenter, radius, 1);
    }

    default FSphereProducer withProdCenterAndFixRadius(Producer<FPoint> pCenter, double radius) {

        return withProdCenterAndFixRadius(pCenter, radius, 1);
    }

    default FSphereProducer withProdCenterAndDistRadius(Producer<FPoint> pCenter, FRandDist1D radius) {

        return withProdCenterAndDistRadius(pCenter, radius, 1);
    }
}
