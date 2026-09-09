package eu.scattering.cli.service.mixin;

import eu.scattering.cli.service.type.FORMAT_LOAD;
import picocli.CommandLine;

public class LoadMixin {

    @CommandLine.Option(
            names = {"-i", "--import"},
            defaultValue = "json",
            description = "Geometry import format."
    )
    public FORMAT_LOAD format;
}
