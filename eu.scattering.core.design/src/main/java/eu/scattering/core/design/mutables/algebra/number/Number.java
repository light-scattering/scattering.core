package eu.scattering.core.design.mutables.algebra.number;

import eu.scattering.core.design.annotations.IntermediateResults;
import eu.scattering.core.design.mutables.algebra.Algebra;

public interface Number<T> extends Algebra<T> {

    T add(T ref);
    T sub(T ref);
    T mul(T ref);
    T div(T ref);

    T add(double factor);
    T sub(double factor);
    T mul(double factor);
    T div(double factor);

    boolean isZero();

    double getMagnitude();
    T setMagnitude(double magnitude);

    double getDistance(T ref);
    T setDistance(T ref, double distance);

    T power(int n);
    T [] root(int n);

    T negate();
    T inverse();
    T conjugate();
    T normalize();

    T applyStateTo(T ref);
    T applyStateFrom(T ref);

    @IntermediateResults
    double getMagnitudeP2();
    @IntermediateResults
    double getDistanceP2(T ref);
}
