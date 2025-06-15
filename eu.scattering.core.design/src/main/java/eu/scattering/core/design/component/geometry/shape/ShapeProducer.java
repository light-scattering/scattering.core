package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.util.support.Producer;

import java.util.List;
import java.util.stream.Stream;

public interface ShapeProducer extends Producer<Shape> {

    @Override
    Shape produce();
    @Override
    Stream<Shape> stream();

    List<Shape> getListAuto();
    List<Shape> getListRandomized(int quantity);
    List<Shape> getListFixed(int quantity);

    // -------------------------------------------------------------------------------------------------

    ShapeProducer withProducer(Producer<? extends Shape> producer, int weight);

    // -------------------------------------------------------------------------------------------------

    default ShapeProducer withProducer(Producer<? extends Shape> producer) {

        return withProducer(producer, 1);
    }
}
