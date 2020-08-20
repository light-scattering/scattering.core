package eu.scattering.core.design.main.engine.base;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface BaseExtension<T> extends BaseComposite {

    T cus(Consumer<T> exp);
    double cusDouble(Function<T, Double> exp);
    boolean cusBoolean(Predicate<T> exp);

    T ext(Consumer<BaseComposite> exp);
    List<Double> extDouble(Function<BaseComposite, List<Double>> exp);
    List<Boolean> extBoolean(Function<BaseComposite, List<Boolean>> exp);
}
