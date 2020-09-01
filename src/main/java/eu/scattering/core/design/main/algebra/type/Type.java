package eu.scattering.core.design.main.algebra.type;

import eu.scattering.core.design.main.algebra.Algebra;

public interface Type<T> extends Algebra<T> {

    T add(T element);
    T sub(T element);
    T mul(T element);
    T div(T element);

    T add(double factor);
    T sub(double factor);
    T mul(double factor);
    T div(double factor);

    double getMagnitude();
    T setMagnitude(double magnitude);

    T pow(int n);

    T negate();
    T inverse();
    T conjugate();
    T normalize();

    T imprint(T element);

    boolean isZero();
}
