package eu.scattering.cli.service.mixin;

import eu.scattering.cli.service.type.FORMAT_IMPORT;
import picocli.CommandLine;

public class ImportMixin {

    @CommandLine.Option(
            names = {"-i", "--import"},
            defaultValue = "json",
            description = {"", "Geometry import format."}
    )
    public FORMAT_IMPORT format;

    @CommandLine.Parameters(
            index = "0",
            defaultValue = "-",
            description = {"", "Input file or '-' for stdin."}
    )
    public String output;
}
