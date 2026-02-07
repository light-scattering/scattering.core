package eu.scattering.core.design.aspect.rotate.generator;

import eu.scattering.core.design.storage.transfer.position.p2.variants.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos3D;
import eu.scattering.core.design.transfer.complex.FRotQt;

public interface FRotGenerator {

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
