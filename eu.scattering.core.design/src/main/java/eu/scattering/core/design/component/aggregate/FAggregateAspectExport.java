package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;

public interface FAggregateAspectExport {

    void toJSON(FAggregate aggregate, StringBuilder builder);

    void toBasic(FAggregate aggregate, ExBasic preset, StringBuilder builder);

    void toFLAGE(FAggregate aggregate, StringBuilder builder);

    void toNGSolve(FAggregate aggregate, StringBuilder builder);

    void toPovRay(FAggregate aggregate, ExPovRay preset, StringBuilder builder);

    //--------------------------------------------------

    default String toJSON(FAggregate aggregate) {
        StringBuilder builder = new StringBuilder();

        toJSON(aggregate, builder);

        return builder.toString();
    }

    default String toBasic(FAggregate aggregate, ExBasic preset) {
        StringBuilder builder = new StringBuilder();

        toBasic(aggregate, preset, builder);

        return builder.toString();
    }

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

    default String toPovRay(FAggregate aggregate, ExPovRay preset) {
        StringBuilder builder = new StringBuilder();

        toPovRay(aggregate, preset, builder);

        return builder.toString();
    }
}
