package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.transfer.container.engine.FRotQt.FRotQt;
import eu.scattering.core.transfer.container.position.FPos3D.FPos3D;

public interface FPointEngineRot {

    // Methods utilizing the Rodrigues rotation formula.
    // Optimized for rotating a single element using only primitive operations.

    FPoint setRgAngle(double x, double y, double z, FPoint in, double angle);
    FPoint setRgAngle(FPoint ref, FPoint in, double angle);
    FPoint setRgAngle(FPos3D ref, FPoint in, double angle);

    FPoint rotRgAround(double x, double y, double z, FPoint in, double angle);
    FPoint rotRgAround(FPoint ref, FPoint in, double angle);
    FPoint rotRgAround(FPos3D ref, FPoint in, double angle);

    // Methods utilizing the quaternion rotation formula.
    // Included for consistency but offer no advantages over 'Rg' methods.
    // Each method internally creates a new FRotQt object.

    FPoint setQtAngle(double x, double y, double z, FPoint in, double angle);
    FPoint setQtAngle(FPoint ref, FPoint in, double angle);
    FPoint setQtAngle(FPos3D ref, FPoint in, double angle);

    FPoint rotQtAround(double x, double y, double z, FPoint in, double angle);
    FPoint rotQtAround(FPoint ref, FPoint in, double angle);
    FPoint rotQtAround(FPos3D ref, FPoint in, double angle);

    // Preferred when an FRotQt object is already available.

    FPoint rotQt(FPoint in, FRotQt qt);
}
