package eu.scattering.cli.command.generate.sub.geometry.variant;

import eu.scattering.cli.service.ExportService;
import eu.scattering.cli.service.mixin.ExportMixin;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.impl.ScatterCore;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "grid3D",
        description = "Generates a 3D grid composed of d0xd1xd2 primary particles.",
        usageHelpAutoWidth = true,
        footer = {
                "%nExample Configuration:%n  scatter-cli generate geometry grid3D 1.5 20 40 60 -e povray"
        }
)
public class GenerateGeometryGrid3D implements Callable<Integer> {

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

    @CommandLine.Parameters(
            index = "3",
            paramLabel = "<d2>",
            description = "The number of particles along the D2 axis."
    )
    private int d2;

    @CommandLine.Mixin
    private ExportMixin export;

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

        if (d2 < 1) {
            System.err.println("Error: The d2 value must be at least one.\n");
            spec.commandLine().usage(System.err);

            return 1;
        }

        ScatterFactory factory = ScatterCore.createFactory();

        FAggregate aggregate = factory.aggregates().geometries().grid3D(d0, d1, d2, rp);
        String results = ExportService.export(factory, aggregate, export.format);

        System.out.println(results);

        return 0;
    }
}
