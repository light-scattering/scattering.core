package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.storage.transfer.position.p2.variants.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos3D;
import eu.scattering.core.design.transfer.complex.FRotQt;

public interface FVectorAspectRot {

    void rotQtAround(Geometry in, FVector ref, double angle);
    void rotRgAround(Geometry in, FVector ref, double angle);

    FVector setRgAngle(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector setRgAngle(FVector in, FVector ref, double angle);
    FVector setRgAngle(FVector in, FPairPos3D ref, double angle);
    FVector setRgAngleBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector setRgAngleBaseCommon(FVector in, FPoint ref, double angle);
    FVector setRgAngleBaseCommon(FVector in, FPos3D ref, double angle);
    FVector setRgAngleBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector setRgAngleBaseZero(FVector in, FPoint ref, double angle);
    FVector setRgAngleBaseZero(FVector in, FPos3D ref, double angle);

    FPoint rotRgAround(FPoint in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FPoint rotRgAround(FPoint in, FVector ref, double angle);
    FPoint rotRgAround(FPoint in, FPairPos3D ref, double angle);

    FVector rotRgAround(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector rotRgAround(FVector in, FVector ref, double angle);
    FVector rotRgAround(FVector in, FPairPos3D ref, double angle);
    FVector rotRgAroundBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotRgAroundBaseCommon(FVector in, FPoint ref, double angle);
    FVector rotRgAroundBaseCommon(FVector in, FPos3D ref, double angle);
    FVector rotRgAroundBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotRgAroundBaseZero(FVector in, FPoint ref, double angle);
    FVector rotRgAroundBaseZero(FVector in, FPos3D ref, double angle);

    FVector rotRgAroundAxis(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector rotRgAroundAxis(FVector in, FVector ref, double angle);
    FVector rotRgAroundAxis(FVector in, FPairPos3D ref, double angle);
    FVector rotRgAroundAxisBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotRgAroundAxisBaseCommon(FVector in, FPoint ref, double angle);
    FVector rotRgAroundAxisBaseCommon(FVector in, FPos3D ref, double angle);
    FVector rotRgAroundAxisBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotRgAroundAxisBaseZero(FVector in, FPoint ref, double angle);
    FVector rotRgAroundAxisBaseZero(FVector in, FPos3D ref, double angle);

    FVector setQtAngle(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector setQtAngle(FVector in, FVector ref, double angle);
    FVector setQtAngle(FVector in, FPairPos3D ref, double angle);
    FVector setQtAngleBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector setQtAngleBaseCommon(FVector in, FPoint ref, double angle);
    FVector setQtAngleBaseCommon(FVector in, FPos3D ref, double angle);
    FVector setQtAngleBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector setQtAngleBaseZero(FVector in, FPoint ref, double angle);
    FVector setQtAngleBaseZero(FVector in, FPos3D ref, double angle);

    FPoint rotQtAround(FPoint in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FPoint rotQtAround(FPoint in, FVector ref, double angle);
    FPoint rotQtAround(FPoint in, FPairPos3D ref, double angle);

    FVector rotQtAround(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector rotQtAround(FVector in, FVector ref, double angle);
    FVector rotQtAround(FVector in, FPairPos3D ref, double angle);
    FVector rotQtAroundBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotQtAroundBaseCommon(FVector in, FPoint ref, double angle);
    FVector rotQtAroundBaseCommon(FVector in, FPos3D ref, double angle);
    FVector rotQtAroundBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotQtAroundBaseZero(FVector in, FPoint ref, double angle);
    FVector rotQtAroundBaseZero(FVector in, FPos3D ref, double angle);

    FVector rotQtAroundAxis(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector rotQtAroundAxis(FVector in, FVector ref, double angle);
    FVector rotQtAroundAxis(FVector in, FPairPos3D ref, double angle);
    FVector rotQtAroundAxisBaseCommon(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotQtAroundAxisBaseCommon(FVector in, FPoint ref, double angle);
    FVector rotQtAroundAxisBaseCommon(FVector in, FPos3D ref, double angle);
    FVector rotQtAroundAxisBaseZero(FVector in, double hX, double hY, double hZ, double angle);
    FVector rotQtAroundAxisBaseZero(FVector in, FPoint ref, double angle);
    FVector rotQtAroundAxisBaseZero(FVector in, FPos3D ref, double angle);

    FVector rotQt(FVector in, FRotQt qt);
}
