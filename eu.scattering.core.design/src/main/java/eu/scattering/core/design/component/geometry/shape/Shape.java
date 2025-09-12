package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.transfer.container.buffer.cache.FCache;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.List;

public interface Shape extends Geometry,
        ShapeModuleDimension, ShapeModuleAggregate, ShapeModuleRelation, ShapeModuleMesh, ShapeModulePosition {

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
