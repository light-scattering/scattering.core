package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;

public interface Shape extends Geometry,
        ShapeModuleDimension, ShapeModuleInteraction, ShapeModuleRelation, ShapeModuleComposition, ShapeModulePosition {

    boolean isExact(Shape arg);
    boolean isExactCenter(Shape arg);

    boolean isSimilar(Shape arg);
    boolean isSimilarCenter(Shape arg);

    //--------------------------------------------------

    double getEpsilon();
    Shape setEpsilon(double epsilon);

    double getDelta();
    Shape setDelta(double delta);

    //--------------------------------------------------

    double getIndex();
    Shape setIndex(double index);

    //--------------------------------------------------

    String getMeta(int index);
    Shape setMeta(String... meta);

    //--------------------------------------------------

    Shape copy();

    //--------------------------------------------------

    default String getMeta() {

        return getMeta(0);
    }
}
