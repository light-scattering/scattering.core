package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.aspect.rotate.state.FRotState;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface FPointAspectRot {

    FPoint setAngleRg(FPoint in, double x, double y, double z, double angle);
    FPoint setAngleRg(FPoint in, FPoint ref, double angle);
    FPoint setAngleRg(FPoint in, FPos3D ref, double angle);

    FPoint aroundRg(FPoint in, double x, double y, double z, double angle);
    FPoint aroundRg(FPoint in, FPoint ref, double angle);
    FPoint aroundRg(FPoint in, FPos3D ref, double angle);

    FPoint setAngleQt(FPoint in, double x, double y, double z, double angle);
    FPoint setAngleQt(FPoint in, FPoint ref, double angle);
    FPoint setAngleQt(FPoint in, FPos3D ref, double angle);

    FPoint aroundQt(FPoint in, double x, double y, double z, double angle);
    FPoint aroundQt(FPoint in, FPoint ref, double angle);
    FPoint aroundQt(FPoint in, FPos3D ref, double angle);

    FPoint apply(FPoint in, FRotState qt);
}
