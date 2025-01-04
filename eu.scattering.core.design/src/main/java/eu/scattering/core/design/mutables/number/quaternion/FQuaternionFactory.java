package eu.scattering.core.design.mutables.algebra.number.quaternion;

import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;

public interface FQuaternionFactory {

    FQuaternion getFQuaternion();

    //--------------------------------------------------

    default FQuaternion getFQuaternion(double re, double i, double j, double k) {

        return getFQuaternion().set(re, i, j, k);
    }

    default FQuaternion getFQuaternion(double re) {

        return getFQuaternion().setRe(re);
    }

    //--------------------------------------------------

    default FQuaternion getFQuaternion(FPos4D origin) {

        return getFQuaternion().set(origin);
    }
}
