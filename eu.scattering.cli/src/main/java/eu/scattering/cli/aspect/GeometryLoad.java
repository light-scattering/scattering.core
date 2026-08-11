package eu.scattering.cli.aspect;

import eu.scattering.cli.type.FORMAT_INPUT;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateAspectLoad;
import eu.scattering.core.design.utility.type.preset.ExBasic;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class GeometryLoad {

    public static Optional<FAggregate> load(ScatterFactory factory, String file, FORMAT_INPUT format) throws IOException {

        return "-".equals(file) ? loadFromStreamConsole(factory, format) : loadFromStreamFile(factory, file, format);
    }

    private static Optional<FAggregate> loadFromStreamConsole(ScatterFactory factory, FORMAT_INPUT format) throws IOException {

        return loadFromStream(factory, System.in, format);
    }

    private static Optional<FAggregate> loadFromStreamFile(ScatterFactory factory, String file, FORMAT_INPUT format) throws IOException {

        try (InputStream is = Files.newInputStream(Paths.get(file))) {
            return loadFromStream(factory, is, format);
        }
    }

    private static Optional<FAggregate> loadFromStream(ScatterFactory factory, InputStream stream, FORMAT_INPUT format) throws IOException {
        String data = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        return loadFromString(factory, data, format);
    }

    private static Optional<FAggregate> loadFromString(ScatterFactory factory, String data, FORMAT_INPUT format) {
        FAggregateAspectLoad load = factory.getLoadAspect().getFAggregateContext();

        return Optional.ofNullable(switch (format) {
            case JSON -> load.fromJSON(data);
            case MULTISPHERE -> load.fromBasic(data, ExBasic.MULTISPHERE);
        });
    }
}
