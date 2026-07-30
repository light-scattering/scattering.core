package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.annotation.Fragment;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.container.Container;
import eu.scattering.core.design.utility.annotation.Modificator;

import java.util.Collection;
import java.util.List;
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

    FAssembly<T> clear();

    //--------------------------------------------------

    <U extends T> FAssembly<T> mutate(Class<U> type, Consumer<U> action);

    FAssembly<T> translate(double x, double y, double z);
    FAssembly<T> translate(FPoint offset);
    FAssembly<T> translate(FPos3D offset);

    FAssembly<T> translate(double bX, double bY, double bZ, double hX, double hY, double hZ);
    FAssembly<T> translate(FVector offset);
    FAssembly<T> translate(FPairPos3D offset);

    FAssembly<T> translate(FPoint base, double x, double y, double z);
    FAssembly<T> translate(FPoint base, FPoint head);

    FAssembly<T> translate(FPos3D base, double x, double y, double z);
    FAssembly<T> translate(FPos3D base, FPos3D head);

    FAssembly<T> scale(double factor);

    FPairPos3D getBoundary();

    void getBoxCenter(FPoint center);
    void getBoxCenter(FPoint center, int steps);

    //--------------------------------------------------

    @Modificator
    List<T> asList();

    @Fragment
    FAssembly<T> translate(FPoint base, FPos3D head);
    @Fragment
    FAssembly<T> translate(FPos3D base, FPoint head);

    //--------------------------------------------------

    default int size() {

        return asList().size();
    }
}
