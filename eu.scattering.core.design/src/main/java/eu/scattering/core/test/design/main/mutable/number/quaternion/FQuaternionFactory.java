package eu.scattering.core.test.design.main.mutable.number.quaternion;

public interface FQuaternionFactory {

    FQuaternion getFQuaternion();

    default FQuaternion getFQuaternion(double re, double i, double j, double k) {

        return getFQuaternion().set(re, i, j, k);
    }

    default FQuaternion getFQuaternion(double re) {

        return getFQuaternion().setRe(re);
    }
}
