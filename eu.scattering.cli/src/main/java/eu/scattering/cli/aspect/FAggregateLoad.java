package eu.scattering.cli.aspect;

import eu.scattering.cli.type.FORMAT_INPUT;
import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateAspectLoad;
import eu.scattering.core.design.utility.type.preset.ExBasic;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class FAggregateLoad {

    public static Optional<FAggregate> load(ScatFactory factory, String file, FORMAT_INPUT format) {

        return "-".equals(file) ? loadFromStreamConsole(factory, format) : loadFromStreamFile(factory, file, format);
    }

    private static Optional<FAggregate> loadFromStreamConsole(ScatFactory factory, FORMAT_INPUT format) {

        try {
            return loadFromStream(factory, System.in, format);
        } catch (Exception e) {
            System.err.println("Failed to read input: " + e.getMessage());

            return Optional.empty();
        }
    }

    private static Optional<FAggregate> loadFromStreamFile(ScatFactory factory, String file, FORMAT_INPUT format) {

        try (InputStream is = Files.newInputStream(Paths.get(file))) {
            return loadFromStream(factory, is, format);
        } catch (Exception e) {
            System.err.println("Failed to read input: " + e.getMessage());

            return Optional.empty();
        }
    }

    private static Optional<FAggregate> loadFromStream(ScatFactory factory, InputStream stream, FORMAT_INPUT format) throws IOException {
        String data = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        return loadFromString(factory, data, format);
    }

    private static Optional<FAggregate> loadFromString(ScatFactory factory, String data, FORMAT_INPUT format) {
        FAggregateAspectLoad load = factory.getLoadAspect().getFAggregateContext();

        return Optional.ofNullable(switch (format) {
            case JSON -> load.fromJSON(data);
            case MULTISPHERE -> load.fromBasic(data, ExBasic.MULTISPHERE);
        });
    }
}
