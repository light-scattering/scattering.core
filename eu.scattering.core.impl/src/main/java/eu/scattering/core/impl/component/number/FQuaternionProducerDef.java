package eu.scattering.core.impl.component.number;

import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.component.number.quaternion.FQuaternionFactory;
import eu.scattering.core.design.component.number.quaternion.FQuaternionProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class FQuaternionProducerDef implements FQuaternionProducer {

    private final FQuaternionFactory factory;
    private final ProducerCoreDef<FQuaternion> processor;

    private FQuaternionProducerDef(FQuaternionFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
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
    public FQuaternion produce() {

        return processor.produce();
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
    public Stream<FQuaternion> stream() {

        return this.processor.stream();
    }

    @Override
    public List<FQuaternion> getListAuto() {

        return this.processor.getListAdopted(null);
    }

    @Override
    public List<FQuaternion> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity, null);
    }

    @Override
    public List<FQuaternion> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity, null);
    }
}
