package eu.scattering.core.impl.component.support;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ProducerCoreDef<E> {
    private final List<AbstractMap.SimpleEntry<Integer, Supplier<E>>> config = new ArrayList<>();

    private final FRandGenerator randomizer;

    public ProducerCoreDef(FRandGenerator randomizer) {

        this.randomizer = randomizer;
    }

    public void addConfig(Supplier<E> supplier) {

        this.addConfig(supplier, 1);
    }

    public void addConfig(Supplier<E> supplier, int weight) {

        this.config.add(new AbstractMap.SimpleEntry<>(weight, supplier));
    }

    public E produce() {

        validateState();

        return getSupplier().get();
    }

    // -------------------------------------------------------------------------------------------------

    private Supplier<E> getSupplier() {

        validateState();

        if (this.config.size() == 1) {
            return this.getSupplierFixed();
        }

        return this.getSupplierRandomized();
    }

    private Supplier<E> getSupplierFixed() {

        return this.config.get(0).getValue();
    }

    private Supplier<E> getSupplierRandomized() {
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

        validateState();

        return Stream.generate(this::produce);
    }

    public Iterator<E> getIterator() {

        return new ProducerIterator(null);
    }

    public Iterator<E> getIterator(Consumer<List<E>> processor) {

        return new ProducerIterator(processor);
    }

    public List<E> getListAdopted(Consumer<List<E>> consumer) {

        validateState();

        List<E> results = getEmptyList(getWeight());

        getListAuto(results);

        mutateResults(results, consumer);

        return results;
    }

    public List<E> getListRandomized(int quantity, Consumer<List<E>> consumer) {

        validateState();

        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity must be at least zero");
        }

        List<E> results = getEmptyList(quantity);

        for (int i = 0 ; i < quantity ; i++) {
            results.add(produce());
        }

        mutateResults(results, consumer);

        return results;
    }

    public List<E> getListFixed(int quantity, Consumer<List<E>> consumer) {

        validateState();

        List<E> results = getEmptyList(quantity);

        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity must be at least zero");
        } else if (quantity == 0) {
            getListEmpty();
        } else if (getWeight() % quantity == 0) {
            getListAuto(results, quantity);
        } else {
            getListDefault(results, quantity);
        }

        mutateResults(results, consumer);

        return results;
    }

    private void validateState() {

        if (this.config.isEmpty()) {
            throw new IllegalStateException("The provider has not been configured");
        }
    }

    private void getListEmpty() {}

    private void getListAuto(List<E> results) {

        this.config.forEach(e -> {
            for (int i = 0 ; i < e.getKey() ; i++) {
                results.add(e.getValue().get());
            }
        });
    }

    private void getListAuto(List<E> results, int quantity) {

        for (int i = 0; i < (getWeight() / quantity); i++) {
            getListAuto(results);
        }
    }

    private void getListDefault(List<E> results, int quantity) {
        int quantityRemaining = quantity;

        for (int i = 0 ; i < this.config.size() ; i++) {
            if (isLastIteration(i)) {
                quantityRemaining -= iterateLast(results, quantityRemaining);
            } else {
                quantityRemaining -= iterate(results, quantityRemaining, i);
            }
        }
    }

    private boolean isLastIteration(int index) {

        return index == this.config.size() - 1;
    }

    private int iterate(List<E> results, int size, int index) {
        AbstractMap.SimpleEntry<Integer, Supplier<E>> record = this.config.get(index);

        double weight = getWeight(index);
        double probability = record.getKey() / weight;

        int quantity = (int) Math.round(probability * size);

        for (int i = 0; i < quantity; i++) {
            results.add(record.getValue().get());
        }

        return quantity;
    }

    private int iterateLast(List<E> results, int size) {

        if (size < 0) {
            iterateLastTrim(results, size);
        } else if (size == 0) {
            iterateLastKeep();
        } else {
            iterateLastExpand(results, size);
        }

        return 0;
    }

    private void iterateLastTrim(List<E> results, int size) {

        for (int i = 0 ; i > size ; i--) {
            results.remove(results.size() - 1);
        }
    }

    private void iterateLastKeep() {}

    private void iterateLastExpand(List<E> results, int size) {
        AbstractMap.SimpleEntry<Integer, Supplier<E>> record = this.config.get(this.config.size() - 1);

        for (int i = 0 ; i < size ; i++) {
            results.add(record.getValue().get());
        }
    }

    private int getWeight() {

        return getWeight(0);
    }

    private int getWeight(int index) {

        if (index < 0 || index >= this.config.size()) {
            throw new IllegalStateException("The index position is erroneous");
        }

        int weight = 0;
        for (int j = index; j < this.config.size(); j++) {
            weight += this.config.get(j).getKey();
        }

        return weight;
    }

    private List<E> getEmptyList(int quantity) {

        return new ArrayList<>(Math.max(quantity, 0));
    }

    private void mutateResults(List<E> results, Consumer<List<E>> consumer) {

        Collections.shuffle(results);

        if (consumer != null) {
            consumer.accept(results);
        }
    }

















    class ProducerIterator implements Iterator<E> {
        private final List<E> list;
        private int index = 0;

        public ProducerIterator(Consumer<List<E>> processor) {
            list = new ArrayList<>();
            ProducerCoreDef.this.getListAuto(list);
            Collections.shuffle(list);

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
