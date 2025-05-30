package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.transfer.container.storage.FRotQt.FRotQt;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

public interface FVectorEngineRot {

    // Methods utilizing the Rodrigues rotation formula.
    // Optimized for rotating a single element using only primitive operations.

    FVector setRgAngle(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector setRgAngle(FVector in, FVector ref, double angle);
    FVector setRgAngle(FVector in, FPairPos3D ref, double angle);
    FVector setRgAngleBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector setRgAngleBaseCommon(FVector in, FPoint ref, double angle);
    FVector setRgAngleBaseCommon(FVector in, FPos3D ref, double angle);
    FVector setRgAngleBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector setRgAngleBaseZero(FVector in, FPoint ref, double angle);
    FVector setRgAngleBaseZero(FVector in, FPos3D ref, double angle);

    FPoint rotRgAround(FPoint in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FPoint rotRgAround(FPoint in, FVector ref, double angle);
    FPoint rotRgAround(FPoint in, FPairPos3D ref, double angle);

    FVector rotRgAround(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector rotRgAround(FVector in, FVector ref, double angle);
    FVector rotRgAround(FVector in, FPairPos3D ref, double angle);
    FVector rotRgAroundBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotRgAroundBaseCommon(FVector in, FPoint ref, double angle);
    FVector rotRgAroundBaseCommon(FVector in, FPos3D ref, double angle);
    FVector rotRgAroundBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotRgAroundBaseZero(FVector in, FPoint ref, double angle);
    FVector rotRgAroundBaseZero(FVector in, FPos3D ref, double angle);

    FVector rotRgAroundFixed(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector rotRgAroundFixed(FVector in, FVector ref, double angle);
    FVector rotRgAroundFixed(FVector in, FPairPos3D ref, double angle);
    FVector rotRgAroundFixedBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotRgAroundFixedBaseCommon(FVector in, FPoint ref, double angle);
    FVector rotRgAroundFixedBaseCommon(FVector in, FPos3D ref, double angle);
    FVector rotRgAroundFixedBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotRgAroundFixedBaseZero(FVector in, FPoint ref, double angle);
    FVector rotRgAroundFixedBaseZero(FVector in, FPos3D ref, double angle);

    // Methods utilizing the quaternion rotation formula.
    // Included for consistency but offer no advantages over 'Rg' methods.
    // Each method internally creates a new FRotQt object.

    FVector setQtAngle(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector setQtAngle(FVector in, FVector ref, double angle);
    FVector setQtAngle(FVector in, FPairPos3D ref, double angle);
    FVector setQtAngleBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector setQtAngleBaseCommon(FVector in, FPoint ref, double angle);
    FVector setQtAngleBaseCommon(FVector in, FPos3D ref, double angle);
    FVector setQtAngleBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector setQtAngleBaseZero(FVector in, FPoint ref, double angle);
    FVector setQtAngleBaseZero(FVector in, FPos3D ref, double angle);

    FPoint rotQtAround(FPoint in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FPoint rotQtAround(FPoint in, FVector ref, double angle);
    FPoint rotQtAround(FPoint in, FPairPos3D ref, double angle);

    FVector rotQtAround(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector rotQtAround(FVector in, FVector ref, double angle);
    FVector rotQtAround(FVector in, FPairPos3D ref, double angle);
    FVector rotQtAroundBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotQtAroundBaseCommon(FVector in, FPoint ref, double angle);
    FVector rotQtAroundBaseCommon(FVector in, FPos3D ref, double angle);
    FVector rotQtAroundBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotQtAroundBaseZero(FVector in, FPoint ref, double angle);
    FVector rotQtAroundBaseZero(FVector in, FPos3D ref, double angle);

    FVector rotQtAroundFixed(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector rotQtAroundFixed(FVector in, FVector ref, double angle);
    FVector rotQtAroundFixed(FVector in, FPairPos3D ref, double angle);
    FVector rotQtAroundFixedBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotQtAroundFixedBaseCommon(FVector in, FPoint ref, double angle);
    FVector rotQtAroundFixedBaseCommon(FVector in, FPos3D ref, double angle);
    FVector rotQtAroundFixedBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotQtAroundFixedBaseZero(FVector in, FPoint ref, double angle);
    FVector rotQtAroundFixedBaseZero(FVector in, FPos3D ref, double angle);

    // Preferred when an FRotQt object is already available.

    FVector rotQt(FVector in, FRotQt qt);
}
