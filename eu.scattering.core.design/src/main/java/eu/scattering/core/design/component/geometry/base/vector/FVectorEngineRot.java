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
    FVector setRgAngleBaseCommon(double hX, double hY, double hZ, FVector in, double angle);
    FVector setRgAngleBaseCommon(FPoint ref, FVector in, double angle);
    FVector setRgAngleBaseCommon(FPos3D ref, FVector in, double angle);
    FVector setRgAngleBaseZero(double hX, double hY, double hZ, FVector in, double angle);
    FVector setRgAngleBaseZero(FPoint ref, FVector in, double angle);
    FVector setRgAngleBaseZero(FPos3D ref, FVector in, double angle);

    FPoint rotRgAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FPoint in, double angle);
    FPoint rotRgAround(FVector ref, FPoint in, double angle);
    FPoint rotRgAround(FPairPos3D ref, FPoint in, double angle);

    FVector rotRgAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotRgAround(FVector ref, FVector in, double angle);
    FVector rotRgAround(FPairPos3D ref, FVector in, double angle);
    FVector rotRgAroundBaseCommon(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotRgAroundBaseCommon(FPoint ref, FVector in, double angle);
    FVector rotRgAroundBaseCommon(FPos3D ref, FVector in, double angle);
    FVector rotRgAroundBaseZero(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotRgAroundBaseZero(FPoint ref, FVector in, double angle);
    FVector rotRgAroundBaseZero(FPos3D ref, FVector in, double angle);

    FVector rotRgAroundFixed(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotRgAroundFixed(FVector ref, FVector in, double angle);
    FVector rotRgAroundFixed(FPairPos3D ref, FVector in, double angle);
    FVector rotRgAroundFixedBaseCommon(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotRgAroundFixedBaseCommon(FPoint ref, FVector in, double angle);
    FVector rotRgAroundFixedBaseCommon(FPos3D ref, FVector in, double angle);
    FVector rotRgAroundFixedBaseZero(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotRgAroundFixedBaseZero(FPoint ref, FVector in, double angle);
    FVector rotRgAroundFixedBaseZero(FPos3D ref, FVector in, double angle);

    // Methods utilizing the quaternion rotation formula.
    // Included for consistency but offer no advantages over 'Rg' methods.
    // Each method internally creates a new FRotQt object.

    FVector setQtAngle(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector setQtAngle(FVector ref, FVector in, double angle);
    FVector setQtAngle(FPairPos3D ref, FVector in, double angle);
    FVector setQtAngleBaseCommon(double hX, double hY, double hZ, FVector in, double angle);
    FVector setQtAngleBaseCommon(FPoint ref, FVector in, double angle);
    FVector setQtAngleBaseCommon(FPos3D ref, FVector in, double angle);
    FVector setQtAngleBaseZero(double hX, double hY, double hZ, FVector in, double angle);
    FVector setQtAngleBaseZero(FPoint ref, FVector in, double angle);
    FVector setQtAngleBaseZero(FPos3D ref, FVector in, double angle);

    FPoint rotQtAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FPoint in, double angle);
    FPoint rotQtAround(FVector ref, FPoint in, double angle);
    FPoint rotQtAround(FPairPos3D ref, FPoint in, double angle);

    FVector rotQtAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAround(FVector ref, FVector in, double angle);
    FVector rotQtAround(FPairPos3D ref, FVector in, double angle);
    FVector rotQtAroundBaseCommon(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundBaseCommon(FPoint ref, FVector in, double angle);
    FVector rotQtAroundBaseCommon(FPos3D ref, FVector in, double angle);
    FVector rotQtAroundBaseZero(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundBaseZero(FPoint ref, FVector in, double angle);
    FVector rotQtAroundBaseZero(FPos3D ref, FVector in, double angle);

    FVector rotQtAroundFixed(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundFixed(FVector ref, FVector in, double angle);
    FVector rotQtAroundFixed(FPairPos3D ref, FVector in, double angle);
    FVector rotQtAroundFixedBaseCommon(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundFixedBaseCommon(FPoint ref, FVector in, double angle);
    FVector rotQtAroundFixedBaseCommon(FPos3D ref, FVector in, double angle);
    FVector rotQtAroundFixedBaseZero(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundFixedBaseZero(FPoint ref, FVector in, double angle);
    FVector rotQtAroundFixedBaseZero(FPos3D ref, FVector in, double angle);

    // Preferred when an FRotQt object is already available.

    FVector rotQt(FVector in, FRotQt qt);
}
