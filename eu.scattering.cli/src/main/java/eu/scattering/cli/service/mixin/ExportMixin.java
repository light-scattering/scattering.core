package eu.scattering.cli.service.mixin;

import eu.scattering.cli.service.type.FORMAT_EXPORT;
import picocli.CommandLine;

public class ExportMixin {

    @CommandLine.Option(
            names = {"-e", "--export"},
            defaultValue = "json",
            description = {"", "Geometry export format."}
    )
    public FORMAT_EXPORT format;
}
