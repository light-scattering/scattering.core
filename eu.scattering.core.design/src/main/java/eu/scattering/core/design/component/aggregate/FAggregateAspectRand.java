package eu.scattering.core.design.component.aggregate;

public interface FAggregateAspectRand {

    void moveMassCenter(FAggregate ref, FAggregate arg, double distance);

    boolean rotate(FAggregate ref, FAggregate arg);
    boolean rotate(FAggregate ref, FAggregate arg, int corrections);

    boolean attach(FAggregate ref, FAggregate arg);
    boolean attach(FAggregate ref, FAggregate arg, int corrections);

    boolean project(FAggregate ref, FAggregate arg);
    boolean project(FAggregate ref, FAggregate arg, int corrections);

    //--------------------------------------------------

    void moveMassCenter2D(FAggregate ref, FAggregate arg, double distance);

    boolean rotate2D(FAggregate ref, FAggregate arg);
    boolean rotate2D(FAggregate ref, FAggregate arg, int corrections);

    boolean attach2D(FAggregate ref, FAggregate arg);
    boolean attach2D(FAggregate ref, FAggregate arg, int corrections);

    boolean project2D(FAggregate ref, FAggregate arg);
    boolean project2D(FAggregate ref, FAggregate arg, int corrections);
}
