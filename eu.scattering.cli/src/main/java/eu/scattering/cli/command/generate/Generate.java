package eu.scattering.cli.command.generate;

import eu.scattering.cli.command.generate.geometry.GenerateGeometry;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "generate",
        description = "Generates sphere assemblies.",
        subcommands = {
                GenerateGeometry.class
        },
        mixinStandardHelpOptions = true,
        usageHelpAutoWidth = true
)
public class Generate implements Callable<Integer> {

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }
}
