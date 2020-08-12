package eu.scattering.core.geometry.base;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IBaseExtension<T> extends IBaseExtensionAssembly {

    T fun(Consumer<T> exp);
    double funVal(Function<T, Double> exp);
    boolean funLog(Predicate<T> exp);

    T ext(Consumer<IBaseExtensionAssembly> exp);
    List<Double> extVal(Function<IBaseExtensionAssembly, List<Double>> exp);
    List<Boolean> extLog(Function<IBaseExtensionAssembly, List<Boolean>> exp);
}
