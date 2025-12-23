package eu.scattering.core.design.component.aggregate;

public interface FAggregateAspectRand {

    boolean project(FAggregate ref, FAggregate arg);
    boolean project(FAggregate ref, FAggregate arg, int corrections);

    //--------------------------------------------------

    boolean project2D(FAggregate ref, FAggregate arg);
    boolean project2D(FAggregate ref, FAggregate arg, int corrections);
}
