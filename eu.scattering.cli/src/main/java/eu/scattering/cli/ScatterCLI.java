package eu.scattering.cli;

import eu.scattering.cli.command.demo.Demo;
import eu.scattering.cli.command.generate.Generate;
import eu.scattering.cli.command.measure.Measure;
import eu.scattering.cli.command.transform.Transform;
import eu.scattering.cli.util.VersionProvider;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "scatter-cli",
        customSynopsis = {
                "",
                "scatter-cli [-h | --version | --diagnostics]",
                "scatter-cli <command> [OPTIONS]"
        },
        mixinStandardHelpOptions = true,
        versionProvider = VersionProvider.class,
        subcommands = { Demo.class, Measure.class, Generate.class, Transform.class, CommandLine.HelpCommand.class},
        description = "The root commands:")
public class ScatterCLI implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Show the help message and exit.")
    boolean printHelp;

    @CommandLine.Option(
            names = {"--version"},
            versionHelp = true,
            description = "Print version information and exit.")
    boolean printVersion;

    @CommandLine.Option(
            names = {"--diagnostics"},
            description = "Print build information and exit."
    )
    boolean printDiagnostics;

    @Override
    public Integer call() {

        if (printDiagnostics) {
            try {
                VersionProvider provider = new VersionProvider();
                String[] infoLines = provider.getInfo();

                for (String line : infoLines) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                System.err.println("Error reading info: " + e.getMessage());

                return 1;
            }

            return 0;
        }

        CommandLine.usage(this, System.out);

        return 0;
    }


    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new ScatterCLI());

        cmd.setCaseInsensitiveEnumValuesAllowed(true);

        int exitCode = cmd.execute(args);

        System.exit(exitCode);
    }
}
