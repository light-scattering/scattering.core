package eu.scattering.core.design.component.number;

import eu.scattering.core.design.utility.annotation.Fragment;
import eu.scattering.core.design.component.Component;
import org.json.JSONObject;

public interface Number<T> extends Component {

    T set(JSONObject json);

    T applyStateTo(T in);
    T applyStateFrom(T arg);

    boolean isExact(T arg);
    boolean isSimilar(T arg);

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

    T copy();

    //--------------------------------------------------

    @Fragment
    T self();
}
