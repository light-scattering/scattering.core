package eu.scattering.cli.service;

import eu.scattering.cli.service.type.FORMAT_LOAD;
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

public class LoadService {

    public static Optional<FAggregate> load(ScatterFactory factory, String file, FORMAT_LOAD format) throws IOException {

        return "-".equals(file) ? loadFromStreamConsole(factory, format) : loadFromStreamFile(factory, file, format);
    }

    //---------------------------------------------------------------------

    private static Optional<FAggregate> loadFromStreamConsole(ScatterFactory factory, FORMAT_LOAD format) throws IOException {

        return loadFromStream(factory, System.in, format);
    }

    private static Optional<FAggregate> loadFromStreamFile(ScatterFactory factory, String file, FORMAT_LOAD format) throws IOException {

        try (InputStream is = Files.newInputStream(Paths.get(file))) {

            return loadFromStream(factory, is, format);
        }
    }

    private static Optional<FAggregate> loadFromStream(ScatterFactory factory, InputStream stream, FORMAT_LOAD format) throws IOException {
        String data = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        return loadFromString(factory, data, format);
    }

    //---------------------------------------------------------------------

    private static Optional<FAggregate> loadFromString(ScatterFactory factory, String data, FORMAT_LOAD format) {
        FAggregateLoader load = factory.load().aggregate();

        return Optional.ofNullable(switch (format) {
            case JSON -> load.fromJSON(data);
            case MULTISPHERE -> load.fromBasic(data, ExBasic.MULTISPHERE);
        });
    }
}
