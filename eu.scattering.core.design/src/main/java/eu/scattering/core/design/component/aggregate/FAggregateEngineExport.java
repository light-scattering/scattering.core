package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.util.annotation.Legacy;

public interface FAggregateEngineExport {

    @Legacy
    void exportFLAGE(FAggregate aggregate, StringBuilder builder);

    void exportNGSolve(FAggregate aggregate, StringBuilder builder);
}
