package eu.scattering.core.design.component.geometry.shape;

import java.util.List;

public interface ShapeModuleRelation {

    boolean misses(Shape shape);
    int misses(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean touches(Shape shape);
    int touches(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean overlaps(Shape shape);
    int overlaps(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean encloses(Shape shape);
    int encloses(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean intersects(Shape shape);
    int intersects(Iterable<? extends Shape> shapes, List<Shape> in);

    // -------------------------------------------------------------------------------------------------

    boolean touchesOrMisses(Shape shape);
    int touchesOrMisses(Iterable<? extends Shape> shapes, List<Shape> in);

    boolean touchesOrOverlaps(Shape shape);
    int touchesOrOverlaps(Iterable<? extends Shape> shapes, List<Shape> in);

    // -------------------------------------------------------------------------------------------------

    default int misses(Iterable<? extends Shape> shapes) {

        return misses(shapes, null);
    }

    default int touches(Iterable<? extends Shape> shapes) {

        return touches(shapes, null);
    }

    default int overlaps(Iterable<? extends Shape> shapes) {

        return overlaps(shapes, null);
    }

    default int encloses(Iterable<? extends Shape> shapes) {

        return encloses(shapes, null);
    }

    default int intersects(Iterable<? extends Shape> shapes) {

        return intersects(shapes, null);
    }

    default int touchesOrMisses(Iterable<? extends Shape> shapes) {

        return touchesOrMisses(shapes, null);
    }

    default int touchesOrOverlaps(Iterable<? extends Shape> shapes) {

        return touchesOrOverlaps(shapes, null);
    }
}
