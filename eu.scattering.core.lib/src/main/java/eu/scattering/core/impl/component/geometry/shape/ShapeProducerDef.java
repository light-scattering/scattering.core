package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.ShapeProducer;
import eu.scattering.core.design.functionality.Producer;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ShapeProducerDef implements ShapeProducer {
    private static final Consumer<List<Shape>> MUTATION_ITERATION;

    static {
        MUTATION_ITERATION =  (list) -> {
            for (int i = 0 ; i < list.size() ; i++) {
                list.get(i).setIndex(i);
            }
        };
    }

    private final ProducerCoreDef<Shape> processor;

    private ShapeProducerDef(FRandGenerator randomizer) {

        this.processor = new ProducerCoreDef<>(randomizer);

        this.processor.addMutation(MUTATION_ITERATION);
    }

    public static ShapeProducer create(FRandGenerator randomizer) {

        return new ShapeProducerDef(randomizer);
    }

    @Override
    public ShapeProducer withProducer(Producer<? extends Shape> producer, int weight) {

        this.processor.addConfig(producer::produce, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Shape produce() {

        return processor.produce();
    }

    @Override
    public List<Shape> getList() {

        return this.processor.getList();
    }

    @Override
    public List<Shape> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<Shape> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public Stream<Shape> stream() {

        return this.processor.stream();
    }
}
