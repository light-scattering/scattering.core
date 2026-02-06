package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.utility.type.PovRay;

public interface FAggregateAspectExport {

    void toFLAGE(FAggregate aggregate, StringBuilder builder);

    void toNGSolve(FAggregate aggregate, StringBuilder builder);

    void toPovRay(FAggregate aggregate, PovRay preset, StringBuilder builder);

    //--------------------------------------------------

    default String toFLAGE(FAggregate aggregate) {
        StringBuilder builder = new StringBuilder();

        toFLAGE(aggregate, builder);

        return builder.toString();
    }

    default String toNGSolve(FAggregate aggregate) {
        StringBuilder builder = new StringBuilder();

        toNGSolve(aggregate, builder);

        return builder.toString();
    }

    default String toPovRay(FAggregate aggregate, PovRay preset) {
        StringBuilder builder = new StringBuilder();

        toPovRay(aggregate, preset, builder);

        return builder.toString();
    }
}
