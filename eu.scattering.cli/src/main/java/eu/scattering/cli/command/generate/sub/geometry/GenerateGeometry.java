package eu.scattering.cli.command.generate.sub.geometry;

import eu.scattering.cli.command.generate.sub.geometry.variant.*;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "geometry",
        description = "Generates common geometric arrangements.",
        subcommands = {
                GenerateGeometryGrid1D.class,
                GenerateGeometryGrid2D.class,
                GenerateGeometryGrid3D.class,
                GenerateGeometryHex2D.class,
                GenerateGeometryHex3D.class
        }
)
public class GenerateGeometry implements Callable<Integer> {

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);

        return 0;
    }
}