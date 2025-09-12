package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.transfer.container.buffer.cache.FCache;

public interface Shape extends Geometry,
        ShapeModuleDimension, ShapeModuleAggregation, ShapeModuleRelation, ShapeModuleInclusion, ShapeModulePosition {

    boolean isExact(Shape arg);
    boolean isExactCenter(Shape arg);

    boolean isSimilar(Shape arg);
    boolean isSimilarCenter(Shape arg);

    //--------------------------------------------------

    FCache getFCache();
    Shape setFCache(FCache cache);
    Shape resetFCache();

    double getEpsilon();
    Shape setEpsilon(double epsilon);
    Shape resetEpsilon();

    double getDelta();
    Shape setDelta(double delta);
    Shape resetDelta();

    double getIndex();
    Shape setIndex(double index);
    Shape resetIndex();

    String getTag();
    Shape setTag(String tag);
    Shape resetTag();

    //--------------------------------------------------

    Shape copy();
}
