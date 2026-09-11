package eu.scattering.cli.command.generate.service.mixin;

import picocli.CommandLine;

public class ValidationMixin {

    public record TargetError(double target, double error) {
    }

    public static class TargetErrorConverter implements CommandLine.ITypeConverter<TargetError> {

        @Override
        public TargetError convert(String value) {
            String[] parts = value.split(",");

            if (parts.length != 2) {
                throw new CommandLine.TypeConversionException("Expected exactly 2 values in format <target,error>.");
            }
            try {
                return new TargetError(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
            } catch (NumberFormatException e) {
                throw new CommandLine.TypeConversionException("Both target and error must be numeric values.");
            }
        }
    }

    @CommandLine.Option(
            names = {"--vbc", "--val-df-bc"},
            paramLabel = "<target,error>",
            converter = TargetErrorConverter.class,
            description = "Validate Box-Counting fractal dimension."
    )
    public TargetError dfBc;

    @CommandLine.Option(
            names = {"--vmr", "--val-df-mr"},
            paramLabel = "<target,error>",
            converter = TargetErrorConverter.class,
            description = "Validate Mass-Radius fractal dimension."
    )
    public TargetError dfMr;

    @CommandLine.Option(
            names = {"--vdc", "--val-df-dc"},
            paramLabel = "<target,error>",
            converter = TargetErrorConverter.class,
            description = "Validate Density-Correlation fractal dimension."
    )
    public TargetError dfDc;
}