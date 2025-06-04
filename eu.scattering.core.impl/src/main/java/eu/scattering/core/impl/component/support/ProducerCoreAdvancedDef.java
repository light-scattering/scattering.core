package eu.scattering.core.impl.component.support;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ProducerCoreAdvancedDef<E>{
    private final List<AbstractMap.SimpleEntry<Integer, Function<E, E>>> config = new ArrayList<>();

    private final Supplier<E> supplier;
    private final FRandGenerator randomizer;

    public ProducerCoreAdvancedDef(Supplier<E> supplier, FRandGenerator randomizer) {

        this.supplier = supplier;
        this.randomizer = randomizer;
    }

    public void addConfig(Function<E, E> function) {

        this.addConfig(function, 1);
    }

    public void addConfig(Function<E, E> function, int probability) {

        this.config.add(new AbstractMap.SimpleEntry<>(probability, function));
    }

    public E produce() {

        if (this.config.size() == 0) {
            throw new IllegalStateException("The producer is not configured");
        }

        return getFunction().apply(this.supplier.get());
    }

    // -------------------------------------------------------------------------------------------------

    private Function<E, E> getFunction() {

        if (this.config.isEmpty()) {
            throw new IllegalStateException("The provider has not been configured");
        }

        if (this.config.size() == 1) {
            return this.getFunctionFixed();
        }

        return this.getFunctionRandomized();
    }

    private Function<E, E> getFunctionFixed() {

        return this.config.get(0).getValue();
    }

    private Function<E, E> getFunctionRandomized() {
        double valueMax = this.config.stream().map(AbstractMap.SimpleEntry::getKey).reduce(0, Integer::sum);
        double valueRandom = this.randomizer.nextDouble(0, valueMax);

        double value = 0;
        for (var record : this.config) {
            value += record.getKey();

            if (valueRandom < value) {
                return record.getValue();
            }
        }

        throw new IllegalStateException("The element could not be created");
    }

    // -------------------------------------------------------------------------------------------------

    public Stream<E> stream() {

        return Stream.generate(this::produce);
    }

    public Iterator<E> getIterator() {

        return new ProducerIterator(null);
    }

    public Iterator<E> getIterator(Consumer<List<E>> processor) {

        return new ProducerIterator(processor);
    }

    private List<E> produceList() {
        List<E> list = new ArrayList<>();

        this.config.forEach(e -> {
            for (int i = 0 ; i < e.getKey() ; i++) {
                list.add(e.getValue().apply(this.supplier.get()));
            }
        });

        Collections.shuffle(list);

        return list;
    }

    class ProducerIterator implements Iterator<E> {
        private final List<E> list = ProducerCoreAdvancedDef.this.produceList();
        private int index = 0;

        public ProducerIterator(Consumer<List<E>> processor) {

            if (processor != null) {
                processor.accept(this.list);
            }
        }

        @Override
        public boolean hasNext() {

            return this.index < this.list.size();
        }

        @Override
        public E next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return this.list.get(this.index++);
        }
    }
}
