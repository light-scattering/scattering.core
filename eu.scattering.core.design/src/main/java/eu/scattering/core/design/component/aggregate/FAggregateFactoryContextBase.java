package eu.scattering.core.design.component.aggregate;

public interface FAggregateFactoryContextBase {

    FAggregate monodisperse(int quantity, double radius);

    FAggregate polydisperse(int quantity, double avg, double std);
    FAggregate polydisperse(int quantity, double avg, double std, double avgErr, double stdErr);
}
