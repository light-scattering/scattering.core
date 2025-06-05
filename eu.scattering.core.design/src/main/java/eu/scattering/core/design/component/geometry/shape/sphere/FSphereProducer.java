package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.component.geometry.base.point.FPointProducer;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FSphereProducer extends Iterable<FSphere> {

    FSphere produce();
    Stream<FSphere> stream();

    // -------------------------------------------------------------------------------------------------

    FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function, int weight);

    FSphereProducer withFixedRadius(String tag, double radius, int weight);
    FSphereProducer withRandomRadius(String tag, double min, double max, int weight);

    FSphereProducer withCenterAndFixedRadius(String tag, FPointProducer pCenter, double radius, int weight);
    FSphereProducer withCenterAndRandomRadius(String tag, FPointProducer pCenter, double min, double max, int weight);

    // -------------------------------------------------------------------------------------------------

    default FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function) {

        return withCustomRule(function, 1);
    }

    default FSphereProducer withFixedRadius(String tag, double radius) {

        return withFixedRadius(tag, radius, 1);
    }

    default FSphereProducer withRandomRadius(String tag, double min, double max) {

        return withRandomRadius(tag, min, max, 1);
    }

    default FSphereProducer withCenterAndFixedRadius(String tag, FPointProducer pCenter, double radius) {

        return withCenterAndFixedRadius(tag, pCenter, radius, 1);
    }

    default FSphereProducer withCenterAndRandomRadius(String tag, FPointProducer pCenter, double min, double max) {

        return withCenterAndRandomRadius(tag, pCenter, min, max, 1);
    }
}
