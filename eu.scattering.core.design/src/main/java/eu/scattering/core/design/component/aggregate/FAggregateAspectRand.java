package eu.scattering.core.design.component.aggregate;

public interface FAggregateAspectRand {

    void moveMassCenter(FAggregate ref, FAggregate arg, double distance);
    void moveMassCenterOnSurface(FAggregate ref, FAggregate arg, double distance);

    void rotate(FAggregate ref, FAggregate arg);
    void rotateOnSurface(FAggregate ref, FAggregate arg);

    void attach(FAggregate ref, FAggregate arg);
    void attachOnSurface(FAggregate ref, FAggregate arg);

    void project(FAggregate ref, FAggregate arg);
    void projectOnSurface(FAggregate ref, FAggregate arg);
}
