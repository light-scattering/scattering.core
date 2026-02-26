package eu.scattering.core.design.component.aggregate;

public interface FAggregateFactoryContextGeometry {

    FAggregate d1(int d1, double radius);
    FAggregate d2(int d1, int d2, double radius);
    FAggregate d3(int d1, int d2, int d3, double radius);

    FAggregate fullCircle(int layers, double radius);
    FAggregate fullSphere(int layers, double radius);

    //--------------------------------------------------

    default FAggregate d1(int d1) {

        return d1(d1, 1);
    }

    default FAggregate d2(int d1, int d2) {

        return d2(d1, d2, 1);
    }

    default FAggregate d3(int d1, int d2, int d3) {

        return d3(d1, d2, d3, 1);
    }

    default FAggregate fullCircle(int layers) {

        return fullCircle(layers, 1);
    }

    default FAggregate fullSphere(int layers) {

        return fullSphere(layers, 1);
    }
}
