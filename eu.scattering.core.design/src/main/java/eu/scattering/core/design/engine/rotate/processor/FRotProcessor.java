package eu.scattering.core.design.engine.rotate.processor;

import eu.scattering.core.transfer.container.engine.FRotQt.FRotQt;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.position.FPos3D.FPos3D;

public interface FRotProcessor {

    FRotQt getRotQt(double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FRotQt getRotQt(FPairPos3D axis, double angle);
    FRotQt getRotQt(double x, double y, double z, double angle);
    FRotQt getRotQt(FPos3D axis, double angle);

    double getAngle(FRotQt core);
    FPairPos3D getAxis(FRotQt core);

    // FRotQt getRotationQt(FPos4D arg);
    // FRotQt getRotationQt(double angle1, double angle2, double angle3);
    // FRotQt getRotationQt(FRotEuler angles);

    // FRotEuler getEulerAngles(FRotQt core);


}
