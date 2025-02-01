package eu.scattering.core.design.mutables.geometry.primitive.vector;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public interface FVectorEngineRotation {

    FVector setQtAngle(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector setQtAngle(FVector ref, FVector in, double angle);
    FVector setQtAngle(FPairPos3D ref, FVector in, double angle);
    FVector setQtAngleSimple(double hX, double hY, double hZ, FVector in, double angle);
    FVector setQtAngleSimple(FPoint ref, FVector in, double angle);
    FVector setQtAngleSimple(FPos3D ref, FVector in, double angle);

    FPoint rotQtAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FPoint in, double angle);
    FPoint rotQtAround(FVector ref, FPoint in, double angle);
    FPoint rotQtAround(FPairPos3D ref, FPoint in, double angle);

    FVector rotQtAround(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAround(FVector ref, FVector in, double angle);
    FVector rotQtAround(FPairPos3D ref, FVector in, double angle);
    FVector rotQtAroundSimple(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundSimple(FPoint ref, FVector in, double angle);
    FVector rotQtAroundSimple(FPos3D ref, FVector in, double angle);

    FVector rotQtAroundAxis(double bX, double bY, double bZ, double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundAxis(FVector ref, FVector in, double angle);
    FVector rotQtAroundAxis(FPairPos3D ref, FVector in, double angle);
    FVector rotQtAroundAxisSimple(double hX, double hY, double hZ, FVector in, double angle);
    FVector rotQtAroundAxisSimple(FPoint ref, FVector in, double angle);
    FVector rotQtAroundAxisSimple(FPos3D ref, FVector in, double angle);

    FVector rotQt(FVector in, FRotQt qt);
}
