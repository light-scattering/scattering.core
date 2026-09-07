package eu.scattering.cli.command.generate.geometry;

import eu.scattering.cli.command.generate.geometry.variant.GenerateGeometryGrid1D;
import eu.scattering.cli.command.generate.geometry.variant.GenerateGeometryGrid2D;
import eu.scattering.cli.command.generate.geometry.variant.GenerateGeometryGrid3D;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "geometry",
        description = "Generates common geometric arrangements.",
        subcommands = {
                GenerateGeometryGrid1D.class,
                GenerateGeometryGrid2D.class,
                GenerateGeometryGrid3D.class
        },
        mixinStandardHelpOptions = true,
        usageHelpAutoWidth = true
)
public class GenerateGeometry implements Callable<Integer> {

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }
}