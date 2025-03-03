package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.transfer.container.position.FPos4D.FPos4D;

public interface FQuaternionFactory {

    FQuaternion getFQuaternion();

    FQuaternion getFQuaternion(double re, double i, double j, double k);

    //--------------------------------------------------

    default FQuaternion getFQuaternion(double re) {

        return getFQuaternion(re, 0, 0, 0);
    }

    //--------------------------------------------------

    default FQuaternion getFQuaternion(FPos4D origin) {

        return getFQuaternion(origin.getD0(), origin.getD1(), origin.getD2(), origin.getD3());
    }
}
