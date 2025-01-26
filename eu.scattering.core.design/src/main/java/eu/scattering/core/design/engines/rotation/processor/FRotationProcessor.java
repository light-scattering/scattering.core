package eu.scattering.core.design.engines.rotation.processor;

import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public interface FRotationProcessor {

    FRotQt getRotationQt(FPos3D axis, double angle);
    FRotQt getRotationQt(FPairPos3D axis, double angle);

    double getAngle(FRotQt core);
    FPairPos3D getAxis(FRotQt core);

    // FRotQt getRotationQt(double angle1, double angle2, double angle3);
    // FRotQt getRotationQt(FRotEuler angles);

    // FRotEuler getEulerAngles(FRotQt core);


}
