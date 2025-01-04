package eu.scattering.core.design.engines.rotation.processor;

import eu.scattering.core.transfer.containers.engine.FRot.FRot;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public interface FRotationProcessor {

    FRot getRotation(FPos3D axis, double angle);
    FRot getRotation(FPairPos3D axis, double angle);
}
