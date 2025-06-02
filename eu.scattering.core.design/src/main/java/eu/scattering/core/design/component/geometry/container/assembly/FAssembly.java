package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.Container;

import java.util.Collection;
import java.util.function.Consumer;

public interface FAssembly<T extends Geometry> extends Container<FAssembly<T>>, Iterable<T> {

    boolean register(T element);

    void applyFPoint(Consumer<FPoint> consumer);

    void applyGeometry(Consumer<T> consumer);

    //--------------------------------------------------

    @Modificator
    Collection<T> getGeometries();
}
