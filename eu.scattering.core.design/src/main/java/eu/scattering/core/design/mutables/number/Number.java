package eu.scattering.core.design.mutables.number;

import eu.scattering.core.design.annotations.Fragment;
import eu.scattering.core.design.mutables.Mutable;

public interface Number<T> extends Mutable<T> {

    T add(T arg);
    T sub(T arg);
    T mul(T arg);
    T div(T arg);

    T add(double factor);
    T sub(double factor);
    T mul(double factor);
    T div(double factor);

    boolean isZero();

    double getMagnitude();
    T setMagnitude(double magnitude);

    double getDistance(T arg);
    T setDistance(T arg, double distance);

    T power(int n);
    T [] root(int n);

    T negate();
    T inverse();
    T conjugate();
    T normalize();

    T applyStateTo(T arg);
    T applyStateFrom(T arg);

    @Fragment
    double getMagnitudeP2();
    @Fragment
    double getDistanceP2(T arg);
}
