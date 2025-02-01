package eu.scattering.core.design.mutables.geometry.primitive.point;

import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public interface FPointEngineRotation {

    FPoint setQtAngle(double x, double y, double z, FPoint in, double angle);
    FPoint setQtAngle(FPoint ref, FPoint in, double angle);
    FPoint setQtAngle(FPos3D ref, FPoint in, double angle);

    FPoint rotQtAround(double x, double y, double z, FPoint in, double angle);
    FPoint rotQtAround(FPoint ref, FPoint in, double angle);
    FPoint rotQtAround(FPos3D ref, FPoint in, double angle);

    FPoint rotQt(FPoint in, FRotQt qt);
}
