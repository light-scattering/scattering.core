package eu.scattering.cli.command.generate.sub.geometry.variant;

import eu.scattering.cli.service.ExportService;
import eu.scattering.cli.service.mixin.ExportMixin;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.impl.ScatterCore;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "grid2D",
        description = "Generates a 2D grid composed of d0xd1 primary particles.",
        usageHelpAutoWidth = true,
        footer = {
                "%nExample Configuration:%n  scatter-cli generate geometry grid2D 1.5 20 40 -e povray"
        }
)
public class GenerateGeometryGrid2D implements Callable<Integer> {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(
            index = "0",
            paramLabel = "<radius>",
            description = "Particle radius."
    )
    private double rp;

    @CommandLine.Parameters(
            index = "1",
            paramLabel = "<d0>",
            description = "The number of particles along the D0 axis."
    )
    private int d0;

    @CommandLine.Parameters(
            index = "2",
            paramLabel = "<d1>",
            description = "The number of particles along the D1 axis."
    )
    private int d1;

    @CommandLine.Mixin
    private ExportMixin exportMixin;

    @Override
    public Integer call() {

        if (rp <= 0) {
            System.err.println("Error: The particle radius must be greater than zero.\n");
            spec.commandLine().usage(System.err);

            return 1;
        }

        if (d0 < 1) {
            System.err.println("Error: The d0 value must be at least one.\n");
            spec.commandLine().usage(System.err);

            return 1;
        }

        if (d1 < 1) {
            System.err.println("Error: The d1 value must be at least one.\n");
            spec.commandLine().usage(System.err);

            return 1;
        }

        try {
            ScatterFactory factory = ScatterCore.createFactory();

            FAggregate aggregate = factory.aggregates().geometries().grid2D(d0, d1, rp);
            String results = ExportService.export(factory, aggregate, exportMixin);

            System.out.println(results);
        } catch (Exception e) {
            System.err.println("Error generating geometry: " + e.getMessage());

            return 2;
        }

        return 0;
    }
}
