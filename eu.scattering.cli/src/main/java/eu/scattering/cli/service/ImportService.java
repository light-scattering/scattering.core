package eu.scattering.cli.service;

import eu.scattering.cli.service.mixin.ImportMixin;
import eu.scattering.cli.service.type.FORMAT_IMPORT;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateLoader;
import eu.scattering.core.design.utility.type.preset.ExBasic;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class ImportService {

    public static Optional<FAggregate> importData(ScatterFactory factory, ImportMixin impMixin) throws IOException {

        return "-".equals(impMixin.file) ? importFromStreamConsole(factory, impMixin.format) : importFromStreamFile(factory, impMixin);
    }

    //---------------------------------------------------------------------

    private static Optional<FAggregate> importFromStreamConsole(ScatterFactory factory, FORMAT_IMPORT format) throws IOException {

        return importFromStream(factory, System.in, format);
    }

    private static Optional<FAggregate> importFromStreamFile(ScatterFactory factory, ImportMixin impMixin) throws IOException {

        try (InputStream is = Files.newInputStream(Paths.get(impMixin.file))) {

            return importFromStream(factory, is, impMixin.format);
        }
    }

    private static Optional<FAggregate> importFromStream(ScatterFactory factory, InputStream stream, FORMAT_IMPORT format) throws IOException {
        String data = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        return importFromString(factory, data, format);
    }

    //---------------------------------------------------------------------

    private static Optional<FAggregate> importFromString(ScatterFactory factory, String data, FORMAT_IMPORT format) {
        FAggregateLoader load = factory.load().aggregate();

        return Optional.ofNullable(switch (format) {
            case JSON -> load.fromJSON(data);
            case MULTISPHERE -> load.fromBasic(data, ExBasic.MULTISPHERE);
        });
    }
}
