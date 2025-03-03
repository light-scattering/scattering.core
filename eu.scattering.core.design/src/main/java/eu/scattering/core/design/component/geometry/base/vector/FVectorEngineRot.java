package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.transfer.container.storage.FRotQt.FRotQt;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

public interface FVectorEngineRot {

    // Methods utilizing the Rodrigues rotation formula.
    // Optimized for rotating a single element using only primitive operations.

    FVector setRgAngle(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector setRgAngle(FVector ref, FVector in, double angle);
    FVector setRgAngle(FPairPos3D ref, FVector in, double angle);
    FVector setRgAngleCompact(double hX, double hY, double hZ, FVector in, double angle);
    FVector setRgAngleCompact(FPoint ref, FVector in, double angle);
    FVector setRgAngleCompact(FPos3D ref, FVector in, double angle);

    FPoint rotRgAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FPoint in, double angle);
    FPoint rotRgAround(FVector ref, FPoint in, double angle);
    FPoint rotRgAround(FPairPos3D ref, FPoint in, double angle);

    FVector rotRgAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotRgAround(FVector ref, FVector in, double angle);
    FVector rotRgAround(FPairPos3D ref, FVector in, double angle);
    FVector rotRgAroundCompact(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotRgAroundCompact(FPoint ref, FVector in, double angle);
    FVector rotRgAroundCompact(FPos3D ref, FVector in, double angle);

    FVector rotRgAroundBase(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotRgAroundBase(FVector ref, FVector in, double angle);
    FVector rotRgAroundBase(FPairPos3D ref, FVector in, double angle);
    FVector rotRgAroundBaseCompact(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotRgAroundBaseCompact(FPoint ref, FVector in, double angle);
    FVector rotRgAroundBaseCompact(FPos3D ref, FVector in, double angle);

    // Methods utilizing the quaternion rotation formula.
    // Included for consistency but offer no advantages over 'Rg' methods.
    // Each method internally creates a new FRotQt object.

    FVector setQtAngle(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector setQtAngle(FVector ref, FVector in, double angle);
    FVector setQtAngle(FPairPos3D ref, FVector in, double angle);
    FVector setQtAngleCompact(double hX, double hY, double hZ, FVector in, double angle);
    FVector setQtAngleCompact(FPoint ref, FVector in, double angle);
    FVector setQtAngleCompact(FPos3D ref, FVector in, double angle);

    FPoint rotQtAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FPoint in, double angle);
    FPoint rotQtAround(FVector ref, FPoint in, double angle);
    FPoint rotQtAround(FPairPos3D ref, FPoint in, double angle);

    FVector rotQtAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAround(FVector ref, FVector in, double angle);
    FVector rotQtAround(FPairPos3D ref, FVector in, double angle);
    FVector rotQtAroundCompact(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundCompact(FPoint ref, FVector in, double angle);
    FVector rotQtAroundCompact(FPos3D ref, FVector in, double angle);

    FVector rotQtAroundBase(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundBase(FVector ref, FVector in, double angle);
    FVector rotQtAroundBase(FPairPos3D ref, FVector in, double angle);
    FVector rotQtAroundBaseCompact(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundBaseCompact(FPoint ref, FVector in, double angle);
    FVector rotQtAroundBaseCompact(FPos3D ref, FVector in, double angle);

    // Preferred when an FRotQt object is already available.

    FVector rotQt(FVector in, FRotQt qt);
}
