package eu.scattering.cli.command.generate.service.mixin;

import picocli.CommandLine;

import java.util.List;

public class DistributionMixin {

    public record RadFixed(int count, double radius) {
    }

    public record RadNormal(int count, double avg, double std) {
    }

    public record RadUniform(int count, double min, double max) {
    }

    public static class RadFixedConverter implements CommandLine.ITypeConverter<RadFixed> {

        @Override
        public RadFixed convert(String value) {
            String[] parts = value.split(",");

            if (parts.length != 2) {
                throw new CommandLine.TypeConversionException("Expected format: <count,radius>");
            }

            try {
                return new RadFixed(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]));
            } catch (NumberFormatException e) {
                throw new CommandLine.TypeConversionException("Count must be an integer; radius must be a decimal.");
            }
        }
    }

    public static class RadNormalConverter implements CommandLine.ITypeConverter<RadNormal> {

        @Override
        public RadNormal convert(String value) {
            String[] parts = value.split(",");

            if (parts.length != 3) {
                throw new CommandLine.TypeConversionException("Expected format: <count,avg,std>");
            }

            try {
                return new RadNormal(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
            } catch (NumberFormatException e) {
                throw new CommandLine.TypeConversionException("Count must be an integer; avg and std must be decimals.");
            }
        }
    }

    public static class RadUniformConverter implements CommandLine.ITypeConverter<RadUniform> {

        @Override
        public RadUniform convert(String value) {
            String[] parts = value.split(",");

            if (parts.length != 3) {
                throw new CommandLine.TypeConversionException("Expected format: <count,min,max>");
            }

            try {
                return new RadUniform(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
            } catch (NumberFormatException e) {
                throw new CommandLine.TypeConversionException("Count must be an integer; min and max must be decimals.");
            }
        }
    }
    @CommandLine.Option(
            names = {"--rf", "--rad-fixed"},
            paramLabel = "<count,radius>",
            converter = RadFixedConverter.class,
            description = "Fixed radius distribution."
    )
    public List<RadFixed> fixed;

    @CommandLine.Option(
            names = {"--rn", "--rad-normal"},
            paramLabel = "<count,avg,std>",
            converter = RadNormalConverter.class,
            description = "Normal radius distribution."
    )
    public List<RadNormal> normal;

    @CommandLine.Option(
            names = {"--ru", "--rad-uniform"},
            paramLabel = "<count,min,max>",
            converter = RadUniformConverter.class,
            description = "Uniform radius distribution."
    )
    public List<RadUniform> uniform;
}