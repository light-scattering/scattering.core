package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.Container;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface FAssembly<T extends Geometry> extends Container<FAssembly<T>>, Iterable<T> {

    boolean registerWithCheck(T element);
    boolean registerWithCheck(T element, BiFunction<T, Collection<T>, Boolean> rule);

    boolean registerWithCheck(Collection<? extends T> elements);
    boolean registerWithCheck(Collection<? extends T> elements, BiFunction<T, Collection<T>, Boolean> rule);

    FAssembly<T> register(T element);
    FAssembly<T> register(T element, BiFunction<T, Collection<T>, Boolean> rule);

    FAssembly<T> register(Collection<? extends T> elements);
    FAssembly<T> register(Collection<? extends T> elements, BiFunction<T, Collection<T>, Boolean> rule);

    boolean deregisterWithCheck(T element);
    boolean deregisterWithCheck(Collection<T> elements);

    FAssembly<T> deregister(T element);
    FAssembly<T> deregister(Collection<T> elements);

    //--------------------------------------------------

    <U extends T> FAssembly<T> mutate(Class<U> type, Consumer<U> action);

    FAssembly<T> translate(double x, double y, double z);
    FAssembly<T> translate(FPos3D offset);

    FAssembly<T> scaleSize(double factor);
    FAssembly<T> scalePosition(double factor);

    double getVolume();
    double getVolume(double[] volume);

    double getSurface();

    double getOverlapFactor();
    double getOverlapFactorLegacy();

    FPairPos3D getRange();

    void setSpatialCenter(FPoint center);
    void setSphericalCenter(FPoint center);

    boolean isCompact();

    void forEachPairInContact(BiConsumer<Shape, Shape> consumer);

    //--------------------------------------------------

    @Modificator
    List<T> asList();

    //--------------------------------------------------

    default int size() {

        return asList().size();
    }
}
