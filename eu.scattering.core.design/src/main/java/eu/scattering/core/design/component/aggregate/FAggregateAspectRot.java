package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.aspect.rotate.transfer.variant.FRotQt;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface FAggregateAspectRot {

    FAggregate rotRgAround(FAggregate in, double x, double y, double z, double angle);
    FAggregate rotRgAround(FAggregate in, FPoint ref, double angle);
    FAggregate rotRgAround(FAggregate in, FPos3D ref, double angle);

    FAggregate rotRgAround(FAggregate in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FAggregate rotRgAround(FAggregate in, FVector ref, double angle);
    FAggregate rotRgAround(FAggregate in, FPairPos3D ref, double angle);

    FAggregate rotQtAround(FAggregate in, double x, double y, double z, double angle);
    FAggregate rotQtAround(FAggregate in, FPoint ref, double angle);
    FAggregate rotQtAround(FAggregate in, FPos3D ref, double angle);

    FAggregate rotQtAround(FAggregate in, double bX, double bY, double bZ, double hX, double hY, double hZ, double angle);
    FAggregate rotQtAround(FAggregate in, FVector ref, double angle);
    FAggregate rotQtAround(FAggregate in, FPairPos3D ref, double angle);

    FAggregate rotQt(FAggregate in, FRotQt qt);
}
