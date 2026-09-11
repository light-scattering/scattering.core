package eu.scattering.cli.command.generate;

import eu.scattering.cli.command.generate.sub.geometry.GenerateGeometry;
import eu.scattering.cli.command.generate.sub.model.GenerateModel;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "generate",
        description = "Generates sphere assemblies.",
        usageHelpAutoWidth = true,
        abbreviateSynopsis = true,
        subcommands = {
                GenerateGeometry.class,
                GenerateModel.class
        }
)
public class Generate implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = {"", "Show this help message and exit."}
    )
    private boolean helpRequested;

    @CommandLine.Option(
            names = {"-V", "--version"},
            versionHelp = true,
            description = {"", "Print version information and exit."}
    )
    private boolean versionRequested;

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }
}
