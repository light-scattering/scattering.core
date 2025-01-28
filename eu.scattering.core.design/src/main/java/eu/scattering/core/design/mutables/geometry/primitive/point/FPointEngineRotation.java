package eu.scattering.core.design.mutables.geometry.primitive.point;

import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;

public interface FPointEngineRotation {

    FPoint setFPointQtAngle(FPoint in, FPoint arg, double angle);

    FPoint rotFPointQtAround(FPoint in, FPoint arg, double angle);
    FPoint rotFPointQt(FPoint in, FRotQt core);
}
