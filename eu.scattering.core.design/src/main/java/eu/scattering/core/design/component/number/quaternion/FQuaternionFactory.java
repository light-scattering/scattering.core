package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;

public interface FQuaternionFactory {

    FQuaternion getFQuaternion();

    //--------------------------------------------------

    default FQuaternion getFQuaternion(double re, double i, double j, double k) {

        return getFQuaternion().setRe(re).setI(i).setJ(j).setK(k);
    }

    default FQuaternion getFQuaternion(double re) {

        return getFQuaternion().setRe(re);
    }

    //--------------------------------------------------

    default FQuaternion getFQuaternion(FPos4D position) {

        return getFQuaternion().applyStateFrom(position);
    }
}
