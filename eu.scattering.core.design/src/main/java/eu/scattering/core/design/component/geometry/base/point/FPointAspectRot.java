package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.aspect.rotate.state.FRotState;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface FPointAspectRot {

    FPoint setRgAngle(FPoint in, double x, double y, double z, double angle);
    FPoint setRgAngle(FPoint in, FPoint ref, double angle);
    FPoint setRgAngle(FPoint in, FPos3D ref, double angle);

    FPoint rotRgAround(FPoint in, double x, double y, double z, double angle);
    FPoint rotRgAround(FPoint in, FPoint ref, double angle);
    FPoint rotRgAround(FPoint in, FPos3D ref, double angle);

    FPoint setQtAngle(FPoint in, double x, double y, double z, double angle);
    FPoint setQtAngle(FPoint in, FPoint ref, double angle);
    FPoint setQtAngle(FPoint in, FPos3D ref, double angle);

    FPoint rotQtAround(FPoint in, double x, double y, double z, double angle);
    FPoint rotQtAround(FPoint in, FPoint ref, double angle);
    FPoint rotQtAround(FPoint in, FPos3D ref, double angle);

    FPoint rotQt(FPoint in, FRotState qt);
}
