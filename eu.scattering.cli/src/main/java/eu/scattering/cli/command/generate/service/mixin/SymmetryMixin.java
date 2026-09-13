package eu.scattering.cli.command.generate.service.mixin;

import picocli.CommandLine;

public class SymmetryMixin {

    @CommandLine.Option(
            names = {"-a", "--asymmetric"},
            description = "Allow asymmetric cluster growth."
    )
    public boolean asymmetric;
}
