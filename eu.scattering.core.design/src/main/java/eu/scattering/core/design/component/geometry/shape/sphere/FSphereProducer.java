package eu.scattering.core.design.component.geometry.shape.sphere;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FSphereProducer extends Iterable<FSphere> {

    FSphere produce();
    Stream<FSphere> stream();

    // -------------------------------------------------------------------------------------------------

    FSphereProducer withCustomRule(Function<FSphere, FSphere> function, int probability);

    FSphereProducer withFixedRadius(String tag, double radius, int probability);
    FSphereProducer withRandomRadius(String tag, double min, double max, int probability);
}
