package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.annotation.Legacy;

public interface FAggregateAspectExport {

    @Legacy
    void exportFLAGE(FAggregate aggregate, StringBuilder builder);

    void exportNGSolve(FAggregate aggregate, StringBuilder builder);

    //--------------------------------------------------

    @Legacy
    default String exportFLAGE(FAggregate aggregate) {
        StringBuilder builder = new StringBuilder();

        exportFLAGE(aggregate, builder);

        return builder.toString();
    }

    default String exportNGSolve(FAggregate aggregate) {
        StringBuilder builder = new StringBuilder();

        exportNGSolve(aggregate, builder);

        return builder.toString();
    }
}
