package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.Container;

import java.util.List;
import java.util.function.Consumer;

public interface FAssembly<T extends Geometry> extends Container<FAssembly<T>>, Iterable<T> {

    boolean registerWithCheck(T element);

    FAssembly<T> register(T element);

    FAssembly<T> applyFPoint(Consumer<FPoint> consumer);
    FAssembly<T> applyGeometry(Consumer<T> consumer);

    //--------------------------------------------------

    @Modificator
    List<T> getGeometries();
}
