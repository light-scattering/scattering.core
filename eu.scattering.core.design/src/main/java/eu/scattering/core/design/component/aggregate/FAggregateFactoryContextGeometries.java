package eu.scattering.core.design.component.aggregate;

public interface FAggregateFactoryContextGeometries {

    FAggregate grid1D(int d1, double radius);
    FAggregate grid2D(int d1, int d2, double radius);
    FAggregate grid3D(int d1, int d2, int d3, double radius);

    FAggregate hex2D(double reach, double radius);
    FAggregate hex3D(double reach, double radius);

    FAggregate fullCircle(int layers, double radius);
    FAggregate fullSphere(int layers, double radius);

    //--------------------------------------------------

    default FAggregate grid1D(int d1) {

        return grid1D(d1, 1);
    }

    default FAggregate grid2D(int d1, int d2) {

        return grid2D(d1, d2, 1);
    }

    default FAggregate grid3D(int d1, int d2, int d3) {

        return grid3D(d1, d2, d3, 1);
    }

    default FAggregate hex2D(double reach) {

        return hex2D(reach, 1);
    }

    default FAggregate hex3D(double reach) {

        return hex3D(reach, 1);
    }

    default FAggregate fullCircle(int layers) {

        return fullCircle(layers, 1);
    }

    default FAggregate fullSphere(int layers) {

        return fullSphere(layers, 1);
    }
}
