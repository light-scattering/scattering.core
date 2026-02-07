package eu.scattering.core.design.aspect.rotate.generator;

import eu.scattering.core.design.aspect.rotate.transfer.variant.FRotQt;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface FRotGenerator {

    FRotQt getRRotQt(double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FRotQt getRRotQt(FPairPos3D axis, double angle);
    FRotQt getRRotQt(double x, double y, double z, double angle);
    FRotQt getRRotQt(FPos3D axis, double angle);

    double getAngle(FRotQt core);
    FPairPos3D getAxis(FRotQt core);

    // FRotQt getRotQt(FPos4D arg);
    // FRotQt getRotQt(double angle1, double angle2, double angle3);
    // FRotQt getRotQt(FRotEuler angles);

    // FRotEuler getEulerAngles(FRotQt core);
}
