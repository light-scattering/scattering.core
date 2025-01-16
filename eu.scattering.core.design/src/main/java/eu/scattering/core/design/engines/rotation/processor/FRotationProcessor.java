package eu.scattering.core.design.engines.rotation.processor;

import eu.scattering.core.transfer.containers.engine.FRot.FRot;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public interface FRotationProcessor {

    // FRotRg getRotationRg(FPos3D axis, double angle);
    // FRotRg getRotationRg(FPairPos3D axis, double angle);

    // FRotQt getRotationQt(FPos3D axis, double angle);
    // FRotQt getRotationQt(FPairPos3D axis, double angle);
    // FRotQt getRotationQt(double angle1, double angle2, angle3);
    // FRotQt getRotationQt(FRotEuler angles);

    // FRotEuler getEulerAngles(FRotQt core);

    FRot getRotation(FPos3D axis, double angle);
    FRot getRotation(FPairPos3D axis, double angle);
}
