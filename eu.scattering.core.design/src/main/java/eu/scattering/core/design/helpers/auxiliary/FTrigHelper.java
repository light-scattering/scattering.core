package eu.scattering.core.design.helpers.auxiliary;

import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public interface FTrigHelper {

    double parseRadToDeg(double radian);
    double parseDegToRad(double degree);

    double getAngleBetweenVectors(FPos3D origin, FPos3D headA, FPos3D headB);
}
