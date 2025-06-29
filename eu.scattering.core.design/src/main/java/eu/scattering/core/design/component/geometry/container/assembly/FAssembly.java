package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.Container;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface FAssembly<T extends Geometry> extends Container<FAssembly<T>>, Iterable<T> {

    boolean registerWithCheck(T element);
    boolean registerWithCheck(Collection<T> elements);

    FAssembly<T> register(T element);
    FAssembly<T> register(Collection<T> elements);

    boolean deregisterWithCheck(T element);
    boolean deregisterWithCheck(Collection<T> elements);

    FAssembly<T> deregister(T element);
    FAssembly<T> deregister(Collection<T> elements);

    FAssembly<T> applyFPoint(Consumer<FPoint> consumer);
    FAssembly<T> applyGeometry(Consumer<T> consumer);

    //--------------------------------------------------

//    FAssembly<T> translate(FPos3D offset);
//    FAssembly<T> scale(double factory);

    //--------------------------------------------------

    @Modificator
    List<T> getListGeometry();
}
