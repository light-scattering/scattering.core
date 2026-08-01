package eu.scattering.cli.command;

import eu.scattering.cli.aspect.FAggregateLoad;
import eu.scattering.cli.type.FORMAT_INPUT;
import eu.scattering.cli.type.TYPE_METRIC;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.impl.ScatterCoreConfig;
import eu.scattering.core.impl.factory.ScatterFactoryDef;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "measure",
        description = "Calculates morphological parameters",
        mixinStandardHelpOptions = true,
        usageHelpAutoWidth = true
)
public class MeasureCommand implements Callable<Integer> {

    static class MetricConverter implements CommandLine.ITypeConverter<TYPE_METRIC> {

        @Override
        public TYPE_METRIC convert(String value) {
            String normalized = value.replace(":", "__").replace("-", "_");

            for (TYPE_METRIC metric : TYPE_METRIC.values()) {

                if (metric.name().equalsIgnoreCase(normalized)) {
                    return metric;
                }
            }
            throw new CommandLine.TypeConversionException("Unknown metric: '" + value + "'");
        }
    }

    static class MetricCandidates implements Iterable<String> {

        @Override
        public java.util.Iterator<String> iterator() {

            return java.util.Arrays.stream(TYPE_METRIC.values())
                    .map(m -> m.name().replace("__", ":"))
                    .map(m -> m.replace("_", "-"))
                    .map(String::toLowerCase)
                    .iterator();
        }
    }

    @CommandLine.Option(
            names = {"-m", "--metrics"},
            arity = "1..*",
            required = true,
            converter = MetricConverter.class,
            completionCandidates = MetricCandidates.class,
            description = {
                    "Measurement types to calculate. Modifiers are indicated by [S] and [F].",
                    "",
                    "  Core              : np, rp[S]",
                    "  Connectivity      : conn, conn-pt, conn-non-ov",
                    "                      ov-p-vol/lin/num[S], ov-c-vol",
                    "  Dimension         : len, box, diam",
                    "                      len-x/y/z",
                    "                      r-cm-mono/poly/mesh/adapt",
                    "                      r-cs, r-cb",
                    "  Center            : cm-mono/poly/mesh/adapt",
                    "                      cs, cb",
                    "  Volume            : vol-sum/mesh/adapt, r-vol-sum/mesh/adapt",
                    "  Surface           : srf-sum/mesh/adapt, r-srf-sum/mesh/adapt",
                    "  Gyration          : rg-mesh",
                    "                      rg-mono, rg-mono-06r1/10r2",
                    "                      rg-poly, rg-poly-06r1/10r2",
                    "  Topology          : coord[S|F], angle[S|F], dist[S|F]",
                    "  Fractal dimension : df-bc/mr/dc",
                    "",
                    "Modifiers:",
                    "  [S] Stat : Append :avg, :std, :max, or :min (e.g., rp:avg)",
                    "  [F] Fun  : Append :fun for full distribution data (e.g., coord:fun)",
                    "",
                    "Note: Some methods require a buffer to be set via -b/--buffer."
            }
    )
    private List<TYPE_METRIC> metrics;

    @CommandLine.Option(names = {"-f", "--format"}, defaultValue = "json", description = "Input format")
    private FORMAT_INPUT format;

    @CommandLine.Option(names = {"-e", "--epsilon"}, defaultValue = "1E-4", description = "Tolerance (default: ${DEFAULT-VALUE})")
    private double epsilon;

    @CommandLine.Option(names = {"-d", "--delta"}, defaultValue = "1E-2", description = "Grid (default: ${DEFAULT-VALUE})")
    private double delta;

    @CommandLine.Option(names = {"-b", "--buffer"}, defaultValue = "0", description = "Buffer (default: ${DEFAULT-VALUE})")
    private int buffer;

    @CommandLine.Parameters(index = "0", defaultValue = "-", description = "Input file or '-' for stdin")
    private String file;

    @Override
    public Integer call() {
        try {
            ScatterFactory factory = ScatterFactoryDef.create();

            FAggregate fAggregate = FAggregateLoad.load(factory, file, format)
                    .orElseThrow(() -> new IllegalArgumentException("The geometry could not be parsed"));

            if (epsilon != ScatterCoreConfig.SHAPE_EPSILON) {
                fAggregate.setParticleEpsilon(epsilon);
            }

            if (delta != ScatterCoreConfig.SHAPE_DELTA) {
                fAggregate.setParticleDelta(delta);
            }

            if (buffer > 0) {
                fAggregate.addFBuffer(buffer);
            }

            String results = Measure.measure(factory, fAggregate, metrics);

            System.out.println(results);

            return 0;
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());

            return 1;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());

            return 2;
        }
    }
}
