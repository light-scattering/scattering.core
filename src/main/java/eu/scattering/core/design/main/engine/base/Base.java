package eu.scattering.core.design.main.engine.base;

import eu.scattering.core.design.main.engine.Disassemble;
import eu.scattering.core.design.main.engine.base.point.FPoint;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Base<T> extends Disassemble {

    T add(FPoint fPoint);
    T add(double x, double y, double z);
    T add(double factor);
    T addX(double x);
    T addY(double y);
    T addZ(double z);

    T sub(FPoint fPoint);
    T sub(double x, double y, double z);
    T sub(double factor);
    T subX(double x);
    T subY(double y);
    T subZ(double z);

    T mul(FPoint fPoint);
    T mul(double x, double y, double z);
    T mul(double factor);
    T mulX(double x);
    T mulY(double y);
    T mulZ(double z);

    T div(FPoint fPoint);
    T div(double x, double y, double z);
    T div(double factor);
    T divX(double x);
    T divY(double y);
    T divZ(double z);

    T set(T element);
    T imprint(T element);

    T cus(Consumer<T> exp);
    double cusDouble(Function<T, Double> exp);
    boolean cusBoolean(Predicate<T> exp);

    T ext(Consumer<Disassemble> exp);
    List<Double> extDouble(Function<Disassemble, List<Double>> exp);
    List<Boolean> extBoolean(Function<Disassemble, List<Boolean>> exp);
}
