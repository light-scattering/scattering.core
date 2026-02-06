package eu.scattering.core.impl.component.support;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ProducerCoreDef<E> {
    private final List<ProducerRecord> records = new ArrayList<>();

    private final List<E> storage = new ArrayList<>();

    private final List<Consumer<List<E>>> mutations = new ArrayList<>();
    private final List<BiFunction<E, List<E>, Boolean>> validations = new ArrayList<>();
    private final List<BiConsumer<E, FRandGenerator>> corrections = new ArrayList<>();

    private final FRandGenerator randomizer;

    public ProducerCoreDef(FRandGenerator randomizer) {

        this.randomizer = randomizer;
    }

    public void addConfig(Supplier<E> supplier, int weight) {

        this.records.add(new ProducerRecord(weight, supplier));
    }

    public void addMutation(Consumer<List<E>> mutation) {

        this.mutations.add(mutation);
    }

    public void addValidation(BiFunction<E, List<E>, Boolean> validation) {

        this.validations.add(validation);
    }

    public void addCorrection(BiConsumer<E, FRandGenerator> correction) {

        this.corrections.add(correction);
    }

    public E produce() {

        return produce(getSupplier());
    }

    private E produce(Supplier<E> supplier) {
        validateState(0);

        if (this.validations.size() == 0) {
            E candidate = supplier.get();

            for (var correction : this.corrections) {
                correction.accept(candidate, randomizer);
            }

            return candidate;
        }

        int iteration = 0;
        int maxIteration = 100;

        validation:
        while (iteration++ < maxIteration) {
            E candidate = supplier.get();

            for (var correction : this.corrections) {
                correction.accept(candidate, randomizer);
            }

            for (var validation : this.validations) {
                if (!validation.apply(candidate, this.storage)) {
                    continue validation;
                }
            }

            this.storage.add(candidate);

            return candidate;
        }

        return null;
    }

    private void produceAndAdd(Supplier<E> supplier, List<E> results, int quantity) {

        for (int i = 0; i < quantity; i++) {
            E candidate = produce(supplier);

            if (candidate != null) {
                results.add(candidate);
            } else {
                throw new IllegalStateException("Generation error");
            }
        }
    }

    // -------------------------------------------------------------------------------------------------

    private Supplier<E> getSupplier() {
        validateState(0);

        if (this.records.size() == 1) {
            return this.getSupplierFixed();
        }

        return this.getSupplierRandomized();
    }

    private Supplier<E> getSupplierFixed() {

        return this.records.get(0).getSupplier();
    }

    private Supplier<E> getSupplierRandomized() {
        double valueMax = this.records.stream().map(ProducerRecord::getWeight).reduce(0, Integer::sum);
        double valueRandom = this.randomizer.nextDouble(0, valueMax);

        double value = 0;
        for (var record : this.records) {
            value += record.getWeight();

            if (valueRandom < value) {
                return record.getSupplier();
            }
        }

        throw new IllegalStateException("The element could not be created");
    }

    // -------------------------------------------------------------------------------------------------

    public Stream<E> stream() {
        validateState(0);

        return Stream.generate(this::produce);
    }

    public List<E> getList() {
        validateState(0);

        List<E> results = getListInitial(getWeightTotal());

        try {
            updateListWeighted(results);
        } catch (Exception ignored) {}

        mutateResults(results);

        return results;
    }

    public List<E> getListFixed(int quantity) {
        validateState(quantity);

        List<E> results = getListInitial(quantity);

        try {
             if (quantity == 0) {
                updateListEmpty();
            } else if (getWeightTotal() % quantity == 0) {
                updateListWeightedMultiplied(results, quantity);
            } else {
                updateListDefault(results, quantity);
            }
        } catch (Exception ignored) {}

        mutateResults(results);

        return results;
    }

    public List<E> getListRandomized(int quantity) {
        validateState(quantity);

        List<E> results = getListInitial(quantity);

        try {
            for (int i = 0; i < quantity; i++) {
                produceAndAdd(getSupplier(), results, 1);
            }
        } catch (Exception ignored) {}

        mutateResults(results);

        return results;
    }

    private List<E> getListInitial(int quantity) {

        return new ArrayList<>(Math.max(quantity, 0));
    }

    private void updateListEmpty() {}

    private void updateListWeighted(List<E> results) {

        for (ProducerRecord record : this.records) {
            produceAndAdd(record.getSupplier(), results, record.getWeight());
        }
    }

    private void updateListWeightedMultiplied(List<E> results, int quantity) {

        for (int i = 0; i < (getWeightTotal() / quantity); i++) {
            updateListWeighted(results);
        }
    }

    private void updateListDefault(List<E> results, int quantity) {
        int quantityRemaining = quantity;

        for (int i = 0; i < this.records.size() ; i++) {
            if (i == this.records.size() - 1) {
                quantityRemaining -= iterateLast(results, quantityRemaining);
            } else {
                quantityRemaining -= iterate(results, quantityRemaining, i);
            }
        }
    }

    private int iterate(List<E> results, int size, int index) {
        ProducerRecord record = this.records.get(index);

        double weight = getWeightRemaining(index);
        double probability = record.getWeight() / weight;

        int quantity = (int) Math.round(probability * size);

        produceAndAdd(record.getSupplier(), results, quantity);

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

    private void iterateLastKeep() {}

    private void iterateLastTrim(List<E> results, int size) {

        for (int i = 0 ; i > size ; i--) {
            results.remove(results.size() - 1);
        }
    }

    private void iterateLastExpand(List<E> results, int size) {
        ProducerRecord record = this.records.get(this.records.size() - 1);

        produceAndAdd(record.getSupplier(), results, size);
    }

    private int getWeightTotal() {

        return getWeightRemaining(0);
    }

    private int getWeightRemaining(int index) {

        if (index < 0 || index >= this.records.size()) {
            throw new IllegalStateException("The index position is erroneous");
        }

        int weight = 0;
        for (int j = index; j < this.records.size(); j++) {
            weight += this.records.get(j).getWeight();
        }

        return weight;
    }

    private void mutateResults(List<E> results) {

        if (this.mutations.size() > 0) {
            this.randomizer.shuffle(results);

            for (var mutation : this.mutations) {
                mutation.accept(results);
            }
        }
    }

    private void validateState(int quantity) {

        if (this.records.isEmpty()) {
            throw new IllegalStateException("The provider is not configured");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity must be at least zero");
        }
    }

    // -------------------------------------------------------------------------------------------------

    private class ProducerRecord {
        private final int weight;
        private final Supplier<E> supplier;

        private ProducerRecord(int weight, Supplier<E> supplier) {

            this.weight = weight;
            this.supplier = supplier;
        }

        private int getWeight() {

            return this.weight;
        }

        private Supplier<E> getSupplier() {

            return this.supplier;
        }
    }
}
