package eu.scattering.core.design.mutables.geometry.primitive.point;

import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;

public interface FPointEngineRotation {

    FPoint setQtAngle(FPoint in, FPoint arg, double angle);
    // TODO - should be - FPoint rotQtAround(FPoint ref, FPoint in, double angle);
    FPoint rotQtAround(FPoint in, FPoint arg, double angle);
//    FPoint rotFPointQtAround(FPoint in, FPos3D arg, double angle);

    FPoint rotQt(FPoint in, FRotQt qt);
}
