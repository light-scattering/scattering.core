package eu.scattering.cli.service;

import eu.scattering.cli.service.mixin.ExportMixin;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateExporter;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExportService {

    public static void exportData(ScatterFactory factory, FAggregate aggregate, ExportMixin exportMixin) throws IOException {
        FAggregateExporter export = factory.export();

        String data = switch (exportMixin.format) {
            case JSON -> export.toJSON(aggregate);
            case MULTISPHERE -> export.toBasic(aggregate, ExBasic.MULTISPHERE);
            case FLAGE -> export.toFLAGE(aggregate);
            case NETGEN -> export.toNGSolve(aggregate);
            case POVRAY_FREE -> export.toPovRay(aggregate, ExPovRay.FREE);
            case POVRAY -> export.toPovRay(aggregate, ExPovRay.BOUNDARY);
        };

        if (exportMixin.file != null) {
            Path path = Paths.get(exportMixin.file);
            Files.writeString(path, data, StandardCharsets.UTF_8);
        } else {
            System.out.println(data);
        }
    }
}
