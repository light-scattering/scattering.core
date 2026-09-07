package eu.scattering.cli.command.generate.geometry.variant;

import eu.scattering.cli.aspect.Export;
import eu.scattering.cli.mixin.ExportMixin;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.impl.ScatterCore;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "grid1D",
        description = "Generates a 1D grid composed of d0 primary particles.",
        mixinStandardHelpOptions = true,
        usageHelpAutoWidth = true
)
public class GenerateGeometryGrid1D implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Particle radius.")
    private double rp;

    @CommandLine.Parameters(index = "1", description = "The number of particles along the D0 axis.")
    private int d0;

    @CommandLine.Mixin
    private ExportMixin export;

    @Override
    public Integer call() throws Exception {

        if (rp <= 0) {
            System.out.println("The particle radius must be greater than zero.");

            return 1;
        }

        if (d0 < 1) {
            System.out.println("The d0 value must be greater than zero.");

            return 1;
        }

        ScatterFactory factory = ScatterCore.createFactory();

        FAggregate aggregate = factory.aggregates().geometries().grid1D(d0, rp);
        String results = Export.export(factory, aggregate, export.format);

        System.out.println(results);

        return 0;
    }
}
