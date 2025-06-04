package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreAdvancedDef;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FSphereProducerDef implements FSphereProducer {
    private static final Consumer<List<FSphere>> ITERATOR_PROCESSOR;

    static {
        ITERATOR_PROCESSOR =  (list) -> {
            for (int i = 0 ; i < list.size() ; i++) {
                list.get(i).setIndex(i);
            }
        };
    }

    private final ProducerCoreAdvancedDef<FSphere> processor;
    private final FRandGenerator randomizer;

    private FSphereProducerDef(Supplier<FSphere> supplier, FRandGenerator randomizer) {

        this.randomizer = randomizer;
        this.processor = new ProducerCoreAdvancedDef<>(supplier, this.randomizer);
    }

    public static FSphereProducer create(Supplier<FSphere> supplier, FRandGenerator randomizer) {

        return new FSphereProducerDef(supplier, randomizer);
    }

    @Override
    public FSphereProducer withCustomRule(Function<FSphere, FSphere> function, int probability) {

        this.processor.addConfig(function, probability);

        return this;
    }

    @Override
    public FSphere produce() {

        return processor.produce();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSphereProducer withFixedRadius(String tag, double radius, int probability) {

        Function<FSphere, FSphere> function = (fSphere) -> {
            fSphere.setTag(tag);

            return fSphere.setRadius(radius);
        };

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FSphereProducer withRandomRadius(String tag, double min, double max, int probability) {

        Function<FSphere, FSphere> function = (fSphere) -> {
            fSphere.setTag(tag);

            return fSphere.setRadius(randomizer.nextDouble(min, max));
        };

        withCustomRule(function, probability);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Stream<FSphere> stream() {

        return this.processor.stream();
    }

    @Override
    public Iterator<FSphere> iterator() {

       return this.processor.getIterator(ITERATOR_PROCESSOR);
    }
}

