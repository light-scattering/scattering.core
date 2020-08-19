package eu.scattering.core.main.engine.base;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IBaseExtension<T> extends IBaseExtensionAssembly {

    T cus(Consumer<T> exp);
    double cusDouble(Function<T, Double> exp);
    boolean cusBoolean(Predicate<T> exp);

    T ext(Consumer<IBaseExtensionAssembly> exp);
    List<Double> extDouble(Function<IBaseExtensionAssembly, List<Double>> exp);
    List<Boolean> extBoolean(Function<IBaseExtensionAssembly, List<Boolean>> exp);
}
