package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.geometry.base.point.FPoint;

public interface FAggregateAspectRand {

    void moveMassCenter(FAggregate ref, FAggregate arg, double distance);
    void moveMassCenterOnSurface(FAggregate ref, FAggregate arg, double distance);

    boolean rotate(FAggregate ref, FAggregate arg, FPoint cRef, FPoint cArg, int corrections);
    boolean rotateOnSurface(FAggregate ref, FAggregate arg, FPoint cRef, FPoint cArg, int corrections);

    void attach(FAggregate ref, FAggregate arg);
    void attachOnSurface(FAggregate ref, FAggregate arg);

    void project(FAggregate ref, FAggregate arg);
    void projectOnSurface(FAggregate ref, FAggregate arg);
}
