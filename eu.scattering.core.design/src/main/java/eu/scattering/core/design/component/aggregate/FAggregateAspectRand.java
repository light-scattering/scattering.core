package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.utility.type.method.MassCenter;

public interface FAggregateAspectRand {

    FAggregate moveMassCenter(FAggregate in, FAggregate arg, MassCenter type, double distance);
    FAggregate moveMassCenterOnPlane(FAggregate in, FAggregate arg, MassCenter type, double distance);

    FAggregate attach(FAggregate in, FAggregate arg);
    FAggregate attachOnPlane(FAggregate in, FAggregate arg);

    // -------------------------------------------------------------------------------------------------

    void project(FAggregate inA, FAggregate inB);
    void projectOnPlane(FAggregate inA, FAggregate inB);

    boolean rotate(FAggregate inA, FAggregate inB, FPoint cA, FPoint cB, int corrections);
    boolean rotateOnPlane(FAggregate inA, FAggregate inB, FPoint cA, FPoint cB, int corrections);
}
