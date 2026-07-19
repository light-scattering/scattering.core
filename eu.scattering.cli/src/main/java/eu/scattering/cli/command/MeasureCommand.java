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

@CommandLine.Command(name = "measure", description = "Calculates morphological parameters", usageHelpAutoWidth = true)
public class MeasureCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-m", "--metrics"}, arity = "1..*", required = true, description = "Measurement types")
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
