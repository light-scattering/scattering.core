package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereFactory;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreAdvancedDef;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
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

    private final FSphereFactory factory;
    private final ProducerCoreAdvancedDef<FSphere> processor;
    private final FRandGenerator randomizer;

    private FSphereProducerDef(FSphereFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.randomizer = randomizer;
        this.processor = new ProducerCoreAdvancedDef<>(this.randomizer);
    }

    public static FSphereProducer create(FSphereFactory factory, FRandGenerator randomizer) {

        return new FSphereProducerDef(factory, randomizer);
    }

    @Override
    public FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function, int probability) {

        this.processor.addConfig(() -> function.apply(factory), probability);

        return this;
    }

    @Override
    public FSphere produce() {

        return processor.produce();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSphereProducer withFixedRadius(String tag, double radius, int probability) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FSphere fSphere = factory.getFSphere();

            fSphere.setTag(tag);
            fSphere.setRadius(radius);

            return fSphere;
        };

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FSphereProducer withRandomRadius(String tag, double min, double max, int probability) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FSphere fSphere = factory.getFSphere();

            fSphere.setTag(tag);
            fSphere.setRadius(randomizer.nextDouble(min, max));

            return fSphere;
        };

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FSphereProducer withCenterAndFixedRadius(String tag, FPointProducer producer, double radius, int probability) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FPoint fPoint = producer.produce();
            FSphere fSphere = factory.getRefFSphere(fPoint);

            fSphere.setTag(tag);
            fSphere.setRadius(radius);

            return fSphere;
        };

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FSphereProducer withCenterAndRandomRadius(String tag, FPointProducer producer, double min, double max, int probability) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FPoint fPoint = producer.produce();
            FSphere fSphere = factory.getRefFSphere(fPoint);

            fSphere.setTag(tag);
            fSphere.setRadius(randomizer.nextDouble(min, max));

            return fSphere;
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

