package eu.scattering.cli.service.mixin;

import picocli.CommandLine;

public class HelpMixin {
    @CommandLine.Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message and exit."
    )
    public boolean help;
}