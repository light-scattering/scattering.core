package eu.scattering.core.design.mutables.engine.rotation;

import eu.scattering.core.design.mutables.engine.Engine;
import eu.scattering.core.transfer.containers.engine.FRot.FRot;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public interface FRotation extends Engine<FRotation> {

    FRot getRotation(FPos3D axis, double angle);
    FRot getRotation(FPairPos3D axis, double angle);
}
