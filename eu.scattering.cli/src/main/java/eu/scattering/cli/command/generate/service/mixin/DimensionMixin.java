package eu.scattering.cli.command.generate.service.mixin;

import picocli.CommandLine;

public class DimensionMixin {

    @CommandLine.Option(
            names = {"--2d"},
            description = "Assemble in two dimensions."
    )
    public boolean d2;
}
