package eu.scattering.core.design.component.aggregate;

public interface FAggregateAspectRand {

    boolean attach(FAggregate ref, FAggregate arg);
    boolean attach(FAggregate ref, FAggregate arg, int corrections);

    boolean project(FAggregate ref, FAggregate arg);
    boolean project(FAggregate ref, FAggregate arg, int corrections);

    //--------------------------------------------------

    boolean attach2D(FAggregate ref, FAggregate arg);
    boolean attach2D(FAggregate ref, FAggregate arg, int corrections);

    boolean project2D(FAggregate ref, FAggregate arg);
    boolean project2D(FAggregate ref, FAggregate arg, int corrections);
}
