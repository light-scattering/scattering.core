package eu.scattering.cli.mixin;

import eu.scattering.cli.aspect.type.FORMAT_EXPORT;
import picocli.CommandLine;

public class ExportMixin {

    @CommandLine.Option(
            names = {"-e", "--export"},
            defaultValue = "json",
            description = "Export format"
    )
    public FORMAT_EXPORT format;
}
