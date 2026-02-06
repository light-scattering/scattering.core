package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.functionality.Producer;

import java.util.List;
import java.util.stream.Stream;

public interface ShapeProducer extends Producer<Shape> {

    @Override
    Shape produce();
    @Override
    List<Shape> getList();
    @Override
    List<Shape> getListRandomized(int quantity);
    @Override
    List<Shape> getListFixed(int quantity);
    @Override
    Stream<Shape> stream();

    // -------------------------------------------------------------------------------------------------

    ShapeProducer withProducer(Producer<? extends Shape> producer, int weight);

    // -------------------------------------------------------------------------------------------------

    default ShapeProducer withProducer(Producer<? extends Shape> producer) {

        return withProducer(producer, 1);
    }
}
