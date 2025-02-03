package eu.scattering.core.design.helper.auxiliary;

import eu.scattering.core.transfer.container.position.FPos3D.FPos3D;

public interface FTrigHelper {

    double convertRadToDeg(double radian);
    double convertDegToRad(double degree);

    double getAngleBetweenVectors(FPos3D origin, FPos3D headA, FPos3D headB);
}
