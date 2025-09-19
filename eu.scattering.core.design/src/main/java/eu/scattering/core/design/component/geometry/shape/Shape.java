package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.transfer.container.buffer.cache.FCache;

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

    double getIndex();
    Shape setIndex(double index);

    String getMeta(int index);
    Shape setMeta(String... meta);

    //--------------------------------------------------

    Shape copy();

    //--------------------------------------------------

    @Modificator
    FCache getCache();
    @Modificator
    Shape setCache(FCache cache);

    //--------------------------------------------------

    default String getMeta() {

        return getMeta(0);
    }
}
