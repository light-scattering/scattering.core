package eu.scattering.core.impl.component.support;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ProducerCoreDef<P, E>  {

    private final P self;
    private final FRandGenerator random;
    private final List<AbstractMap.SimpleEntry<Double, Function<E, E>>> config;

    public ProducerCoreDef(P self, FRandGenerator random) {

        this.self = self;
        this.random = random;
        this.config = new ArrayList<>();
    }

    public P setConfig(Function<E, E> function) {
        config.clear();

        config.add(new AbstractMap.SimpleEntry<>(1.0, function));

        return self;
    }

    public P addConfig(Function<E, E> function, double probability) {

        config.add(new AbstractMap.SimpleEntry<>(probability, function));

        return self;
    }

    public Function<E, E> getFunction() {

        if (config.isEmpty()) {
            throw new IllegalStateException("The provider has not been configured");
        }

        if (config.size() == 1) {
            return getFunctionFixed();
        }

        return getFunctionRandomized();
    }

    // -------------------------------------------------------------------------------------------------

    private Function<E, E> getFunctionFixed() {

        return config.get(0).getValue();
    }

    private Function<E, E> getFunctionRandomized() {
        double valueMax = config.stream().map(AbstractMap.SimpleEntry::getKey).reduce(0d, Double::sum);
        double valueRandom = random.nextDouble(0, valueMax);

        double value = 0;
        for (var record : config) {
            value += record.getKey();

            if (valueRandom < value) {
                return record.getValue();
            }
        }

        throw new IllegalStateException("The element could not be created");
    }
}
