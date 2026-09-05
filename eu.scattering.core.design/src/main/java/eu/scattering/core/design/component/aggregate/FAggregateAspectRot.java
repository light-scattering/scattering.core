package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.aspect.rotate.state.FRotState;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface FAggregateAspectRot {

    FAggregate aroundRg(FAggregate in, double x, double y, double z, double angle);
    FAggregate aroundRg(FAggregate in, FPoint ref, double angle);
    FAggregate aroundRg(FAggregate in, FPos3D ref, double angle);

    FAggregate aroundRg(FAggregate in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FAggregate aroundRg(FAggregate in, FVector ref, double angle);
    FAggregate aroundRg(FAggregate in, FPairPos3D ref, double angle);

    FAggregate aroundQt(FAggregate in, double x, double y, double z, double angle);
    FAggregate aroundQt(FAggregate in, FPoint ref, double angle);
    FAggregate aroundQt(FAggregate in, FPos3D ref, double angle);

    FAggregate aroundQt(FAggregate in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FAggregate aroundQt(FAggregate in, FVector ref, double angle);
    FAggregate aroundQt(FAggregate in, FPairPos3D ref, double angle);

    FAggregate apply(FAggregate in, FRotState qt);
}
