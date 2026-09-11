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

    public static Optional<FAggregate> load(ScatterFactory factory, ImportMixin importMixin) throws IOException {

        return "-".equals(importMixin.output) ? loadFromStreamConsole(factory, importMixin.format) : loadFromStreamFile(factory, importMixin);
    }

    //---------------------------------------------------------------------

    private static Optional<FAggregate> loadFromStreamConsole(ScatterFactory factory, FORMAT_IMPORT format) throws IOException {

        return loadFromStream(factory, System.in, format);
    }

    private static Optional<FAggregate> loadFromStreamFile(ScatterFactory factory, ImportMixin importMixin) throws IOException {

        try (InputStream is = Files.newInputStream(Paths.get(importMixin.output))) {

            return loadFromStream(factory, is, importMixin.format);
        }
    }

    private static Optional<FAggregate> loadFromStream(ScatterFactory factory, InputStream stream, FORMAT_IMPORT format) throws IOException {
        String data = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        return loadFromString(factory, data, format);
    }

    //---------------------------------------------------------------------

    private static Optional<FAggregate> loadFromString(ScatterFactory factory, String data, FORMAT_IMPORT format) {
        FAggregateLoader load = factory.load().aggregate();

        return Optional.ofNullable(switch (format) {
            case JSON -> load.fromJSON(data);
            case MULTISPHERE -> load.fromBasic(data, ExBasic.MULTISPHERE);
        });
    }
}
