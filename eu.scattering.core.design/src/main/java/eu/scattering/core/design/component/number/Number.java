package eu.scattering.core.design.component.number;

import eu.scattering.core.design.component.Component;

public interface Number<T> extends Component<T> {

    T add(T arg);
    T sub(T arg);
    T mul(T arg);
    T div(T arg);

    T addFactor(double factor);
    T subFactor(double factor);
    T mulFactor(double factor);
    T divFactor(double factor);

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
}
