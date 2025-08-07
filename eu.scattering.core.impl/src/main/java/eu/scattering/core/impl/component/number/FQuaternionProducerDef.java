package eu.scattering.core.impl.component.number;

import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.component.number.quaternion.FQuaternionFactory;
import eu.scattering.core.design.component.number.quaternion.FQuaternionProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FQuaternionProducerDef implements FQuaternionProducer {

    private final FQuaternionFactory factory;
    private final ProducerCoreDef<FQuaternion> processor;
    private final FRandGenerator randomizer;

    private FQuaternionProducerDef(FQuaternionFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.randomizer = randomizer;
        this.processor = new ProducerCoreDef<>(randomizer);
    }

    public static FQuaternionProducer create(FQuaternionFactory factory, FRandGenerator randomizer) {

        return new FQuaternionProducerDef(factory, randomizer);
    }

    @Override
    public FQuaternionProducer withCustomRule(Function<FQuaternionFactory, FQuaternion> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FQuaternionProducer withCustomRule(BiFunction<FQuaternionFactory, FRandGenerator, FQuaternion> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, randomizer), weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FQuaternionProducer withZero(int weight) {
        Function<FQuaternionFactory, FQuaternion> function = FQuaternionFactory::getFQuaternion;

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FQuaternion produce() {

        return processor.produce();
    }

    @Override
    public List<FQuaternion> getList() {

        return this.processor.getList();
    }

    @Override
    public List<FQuaternion> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<FQuaternion> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public Stream<FQuaternion> stream() {

        return this.processor.stream();
    }
}
