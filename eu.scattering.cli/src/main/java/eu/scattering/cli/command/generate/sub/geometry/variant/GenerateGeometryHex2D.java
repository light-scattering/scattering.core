package eu.scattering.cli.command.generate.sub.geometry.variant;

import eu.scattering.cli.service.ExportService;
import eu.scattering.cli.service.mixin.ExportMixin;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.impl.ScatterCore;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "hex2D",
        description = "Create a 2D hexagonal cluster limited by an outer radius.",
        usageHelpAutoWidth = true,
        footer = {
                "%nExample Configuration:%n  scatter-cli generate geometry hex2D 1.0 15.0 -e povray"
        }
)
public class GenerateGeometryHex2D implements Callable<Integer> {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(
            index = "0",
            paramLabel = "<radius>",
            description = {"", "Particle radius."}
    )
    private double rp;

    @CommandLine.Parameters(
            index = "1",
            paramLabel = "<outer_radius>",
            description = {"", "Geometry outer radius."}
    )
    private double reach;

    @CommandLine.Mixin
    private ExportMixin exportMixin;

    @Override
    public Integer call() {

        if (rp <= 0) {
            System.err.println("Error: The particle radius must be greater than zero.\n");
            spec.commandLine().usage(System.err);

            return 1;
        }

        if (reach < rp) {
            System.err.println("Error: The outer radius must not be less than the particle radius.\n");
            spec.commandLine().usage(System.err);

            return 1;
        }

        try {
            ScatterFactory factory = ScatterCore.createFactory();

            FAggregate aggregate = factory.aggregates().geometries().hex2D(reach, rp);
            String results = ExportService.export(factory, aggregate, exportMixin);

            System.out.println(results);

        } catch (Exception e) {
            System.err.println("Error generating geometry: " + e.getMessage());

            return 2;
        }

        return 0;
    }
}
