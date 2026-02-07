package eu.scattering.core.design.helper.trigonometry;

import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;

public interface FTrigHelper {

    double convertRadToDeg(double radian);
    double convertDegToRad(double degree);

    double getAngleBetweenVectors(FPos3D origin, FPos3D headA, FPos3D headB);

    double getAngle(double adjA, double adjB, double oppC);

    boolean isValid(double sideA, double sideB, double sideC);
}
