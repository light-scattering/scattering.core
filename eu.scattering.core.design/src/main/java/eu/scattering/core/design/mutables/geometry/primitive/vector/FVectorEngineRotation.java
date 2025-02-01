package eu.scattering.core.design.mutables.geometry.primitive.vector;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public interface FVectorEngineRotation {

    // Methods utilizing the Rodrigues rotation formula.
    // Optimized for rotating a single element using only primitive operations.

    FVector setAngle(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector setAngle(FVector ref, FVector in, double angle);
    FVector setAngle(FPairPos3D ref, FVector in, double angle);
    FVector setAngleSimple(double hX, double hY, double hZ, FVector in, double angle);
    FVector setAngleSimple(FPoint ref, FVector in, double angle);
    FVector setAngleSimple(FPos3D ref, FVector in, double angle);

    FVector rotAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotAround(FVector ref, FVector in, double angle);
    FVector rotAround(FPairPos3D ref, FVector in, double angle);
    FVector rotAroundSimple(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotAroundSimple(FPoint ref, FVector in, double angle);
    FVector rotAroundSimple(FPos3D ref, FVector in, double angle);

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
