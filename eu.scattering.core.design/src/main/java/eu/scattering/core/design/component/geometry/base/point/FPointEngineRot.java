package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.transfer.container.storage.FRotQt.FRotQt;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

public interface FPointEngineRot {

    // Methods utilizing the Rodrigues rotation formula.
    // Optimized for rotating a single element using only primitive operations.

    FPoint setRgAngle(FPoint in, double x, double y, double z, double angle);
    FPoint setRgAngle(FPoint in, FPoint ref, double angle);
    FPoint setRgAngle(FPoint in, FPos3D ref, double angle);

    FPoint rotRgAround(FPoint in, double x, double y, double z, double angle);
    FPoint rotRgAround(FPoint in, FPoint ref, double angle);
    FPoint rotRgAround(FPoint in, FPos3D ref, double angle);

    // Methods utilizing the quaternion rotation formula.
    // Included for consistency but offer no advantages over 'Rg' methods.
    // Each method internally creates a new FRotQt object.

    FPoint setQtAngle(FPoint in, double x, double y, double z, double angle);
    FPoint setQtAngle(FPoint in, FPoint ref, double angle);
    FPoint setQtAngle(FPoint in, FPos3D ref, double angle);

    FPoint rotQtAround(FPoint in, double x, double y, double z, double angle);
    FPoint rotQtAround(FPoint in, FPoint ref, double angle);
    FPoint rotQtAround(FPoint in, FPos3D ref, double angle);

    // Preferred when an FRotQt object is already available.

    FPoint rotQt(FPoint in, FRotQt qt);
}
