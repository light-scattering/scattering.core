package eu.scattering.core.design.component.geometry.shape;

import java.util.List;

public interface ShapeModuleRelation {

    boolean repels(Shape shape);
    int repels(Iterable<? extends Shape> shapes);
    int repels(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean touches(Shape shape);
    int touches(Iterable<? extends Shape> shapes);
    int touches(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean overlaps(Shape shape);
    int overlaps(Iterable<? extends Shape> shapes);
    int overlaps(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean encloses(Shape shape);
    int encloses(Iterable<? extends Shape> shapes);
    int encloses(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean intersects(Shape shape);
    int intersects(Iterable<? extends Shape> shapes);
    int intersects(Iterable<? extends Shape> shapes, List<Shape> in);

    // -------------------------------------------------------------------------------------------------

    boolean touchesOrRepels(Shape shape);
    int touchesOrRepels(Iterable<? extends Shape> shapes);
    int touchesOrRepels(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean touchesOrOverlaps(Shape shape);
    int touchesOrOverlaps(Iterable<? extends Shape> shapes);
    int touchesOrOverlaps(Iterable<? extends Shape> shapes, List<Shape> in);

    // -------------------------------------------------------------------------------------------------
}
