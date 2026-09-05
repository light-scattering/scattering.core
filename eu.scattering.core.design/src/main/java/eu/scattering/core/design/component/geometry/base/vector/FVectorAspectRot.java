package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.aspect.rotate.state.FRotState;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface FVectorAspectRot {

    void aroundQt(Geometry in, FVector ref, double angle);
    void aroundRg(Geometry in, FVector ref, double angle);

    FVector setAngleRg(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector setAngleRg(FVector in, FVector ref, double angle);
    FVector setAngleRg(FVector in, FPairPos3D ref, double angle);
    FVector setAngleRgWithCommonBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector setAngleRgWithCommonBase(FVector in, FPoint ref, double angle);
    FVector setAngleRgWithCommonBase(FVector in, FPos3D ref, double angle);
    FVector setAngleRgAtZeroBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector setAngleRgAtZeroBase(FVector in, FPoint ref, double angle);
    FVector setAngleRgAtZeroBase(FVector in, FPos3D ref, double angle);

    FPoint aroundRg(FPoint in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FPoint aroundRg(FPoint in, FVector ref, double angle);
    FPoint aroundRg(FPoint in, FPairPos3D ref, double angle);

    FVector aroundRg(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector aroundRg(FVector in, FVector ref, double angle);
    FVector aroundRg(FVector in, FPairPos3D ref, double angle);
    FVector aroundRgWithCommonBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector aroundRgWithCommonBase(FVector in, FPoint ref, double angle);
    FVector aroundRgWithCommonBase(FVector in, FPos3D ref, double angle);
    FVector aroundRgAtZeroBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector aroundRgAtZeroBase(FVector in, FPoint ref, double angle);
    FVector aroundRgAtZeroBase(FVector in, FPos3D ref, double angle);

    FVector pivotRg(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector pivotRg(FVector in, FVector ref, double angle);
    FVector pivotRg(FVector in, FPairPos3D ref, double angle);
    FVector pivotRgWithCommonBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector pivotRgWithCommonBase(FVector in, FPoint ref, double angle);
    FVector pivotRgWithCommonBase(FVector in, FPos3D ref, double angle);
    FVector pivotRgAtZeroBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector pivotRgAtZeroBase(FVector in, FPoint ref, double angle);
    FVector pivotRgAtZeroBase(FVector in, FPos3D ref, double angle);

    FVector setAngleQt(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector setAngleQt(FVector in, FVector ref, double angle);
    FVector setAngleQt(FVector in, FPairPos3D ref, double angle);
    FVector setAngleQtWithCommonBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector setAngleQtWithCommonBase(FVector in, FPoint ref, double angle);
    FVector setAngleQtWithCommonBase(FVector in, FPos3D ref, double angle);
    FVector setAngleQtAtZeroBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector setAngleQtAtZeroBase(FVector in, FPoint ref, double angle);
    FVector setAngleQtAtZeroBase(FVector in, FPos3D ref, double angle);

    FPoint aroundQt(FPoint in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FPoint aroundQt(FPoint in, FVector ref, double angle);
    FPoint aroundQt(FPoint in, FPairPos3D ref, double angle);

    FVector aroundQt(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector aroundQt(FVector in, FVector ref, double angle);
    FVector aroundQt(FVector in, FPairPos3D ref, double angle);
    FVector aroundQtWithCommonBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector aroundQtWithCommonBase(FVector in, FPoint ref, double angle);
    FVector aroundQtWithCommonBase(FVector in, FPos3D ref, double angle);
    FVector aroundQtAtZeroBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector aroundQtAtZeroBase(FVector in, FPoint ref, double angle);
    FVector aroundQtAtZeroBase(FVector in, FPos3D ref, double angle);

    FVector pivotQt(FVector in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FVector pivotQt(FVector in, FVector ref, double angle);
    FVector pivotQt(FVector in, FPairPos3D ref, double angle);
    FVector pivotQtWithCommonBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector pivotQtWithCommonBase(FVector in, FPoint ref, double angle);
    FVector pivotQtWithCommonBase(FVector in, FPos3D ref, double angle);
    FVector pivotQAtZeroBase(FVector in, double hX, double hY, double hZ, double angle);
    FVector pivotQAtZeroBase(FVector in, FPoint ref, double angle);
    FVector pivotQAtZeroBase(FVector in, FPos3D ref, double angle);

    FVector apply(FVector in, FRotState qt);
}
