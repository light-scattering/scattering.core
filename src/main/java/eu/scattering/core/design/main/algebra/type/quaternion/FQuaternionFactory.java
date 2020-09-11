package eu.scattering.core.design.main.algebra.type.quaternion;

public interface FQuaternionFactory {

    FQuaternion getFQuaternion();

    default FQuaternion getFQuaternion(double re, double i, double j, double k) {

        return getFQuaternion().set(re, i, j, k);
    }

    default FQuaternion getFQuaternion(double re) {

        return getFQuaternion().setRe(re);
    }

}
