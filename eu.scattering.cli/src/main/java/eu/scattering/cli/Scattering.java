package eu.scattering.cli;

import eu.scattering.cli.command.MeasureCommand;
import eu.scattering.cli.util.VersionProvider;
import picocli.CommandLine;

@CommandLine.Command(name = "scat",
        mixinStandardHelpOptions = true,
        versionProvider = VersionProvider.class,
        subcommands = { MeasureCommand.class, CommandLine.HelpCommand.class },
        description = "Root command for ScatCore morphological analysis.")
public class Scattering {

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new Scattering());

        cmd.setCaseInsensitiveEnumValuesAllowed(true);

        int exitCode = cmd.execute(args);

        System.exit(exitCode);
    }
}
