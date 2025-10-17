package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.transfer.complex.FRotQt;
import eu.scattering.core.design.transfer.primitive.FPos3D;

public interface FPointEngineRot {

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

    FPoint rotQt(FPoint in, FRotQt qt);
}
