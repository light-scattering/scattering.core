package eu.scattering.core.design.component.aggregate;

public interface FAggregateAspectRand {

    void moveMassCenter(FAggregate ref, FAggregate arg, double distance);
    void moveMassCenterOnSurface(FAggregate ref, FAggregate arg, double distance);

    boolean rotate(FAggregate ref, FAggregate arg, int corrections);
    boolean rotateOnSurface(FAggregate ref, FAggregate arg, int corrections);

    void attach(FAggregate ref, FAggregate arg);
    void attachOnSurface(FAggregate ref, FAggregate arg);

    void project(FAggregate ref, FAggregate arg);
    void projectOnSurface(FAggregate ref, FAggregate arg);
}
