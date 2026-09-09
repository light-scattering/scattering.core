package eu.scattering.cli.service;

import eu.scattering.cli.service.type.FORMAT_EXPORT;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateExporter;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;

public class ExportService {

    public static String export(ScatterFactory factory, FAggregate aggregate, FORMAT_EXPORT format) {
        FAggregateExporter export = factory.export();

        return switch (format) {
            case JSON -> export.toJSON(aggregate);
            case MULTISPHERE -> export.toBasic(aggregate, ExBasic.MULTISPHERE);
            case FLAGE -> export.toFLAGE(aggregate);
            case NETGEN -> export.toNGSolve(aggregate);
            case POVRAY_FREE -> export.toPovRay(aggregate, ExPovRay.FREE);
            case POVRAY -> export.toPovRay(aggregate, ExPovRay.BOUNDARY);
        };
    }
}
