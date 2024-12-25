package eu.scattering.core.design.elements.algebra.geometry.primitive;

import eu.scattering.core.design.elements.algebra.Algebra;
import eu.scattering.core.design.elements.algebra.geometry.Geometry;
import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Primitive<T> extends Geometry, Algebra<T> {

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

    T applyStateFrom(T ref);
    T applyStateTo(T ref);

    T trans(Consumer<T> exp);
    double transDouble(Function<T, Double> exp);
    boolean transBoolean(Predicate<T> exp);

    T ext(Consumer<Geometry> exp);
    List<Double> extDouble(Function<Geometry, List<Double>> exp);
    List<Boolean> extBoolean(Function<Geometry, List<Boolean>> exp);
}
