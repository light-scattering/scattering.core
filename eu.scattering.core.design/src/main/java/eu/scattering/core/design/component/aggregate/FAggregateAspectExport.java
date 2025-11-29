package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.annotation.Legacy;

public interface FAggregateAspectExport {

    @Legacy
    void toFLAGE(FAggregate aggregate, StringBuilder builder);

    void toNGSolve(FAggregate aggregate, StringBuilder builder);

    //--------------------------------------------------

    @Legacy
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
}
