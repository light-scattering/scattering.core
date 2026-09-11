package eu.scattering.cli.command.transform.service.mixin;

import picocli.CommandLine;
import java.util.List;

public class TransformMixin {

    public record Translation(double d0, double d1, double d2) {
    }

    public record Rotation(double d0, double d1, double d2, double radians) {
    }


    public static class TranslationConverter implements CommandLine.ITypeConverter<Translation> {

        @Override
        public Translation convert(String value) {
            String[] parts = value.split(",");

            if (parts.length != 3){
                throw new CommandLine.TypeConversionException("Translation requires exactly 3 parameters (d0,d1,d2).");
            }

            try {
                return new Translation(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
            } catch (NumberFormatException e) {
                throw new CommandLine.TypeConversionException("Translation parameters must be numeric.");
            }
        }
    }

    public static class RotationConverter implements CommandLine.ITypeConverter<Rotation> {

        @Override
        public Rotation convert(String value) {
            String[] parts = value.split(",");

            if (parts.length != 4) {
                throw new CommandLine.TypeConversionException("Rotation requires exactly 4 parameters (d0,d1,d2,radians).");
            }

            try {
                return new Rotation(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
            } catch (NumberFormatException e) {
                throw new CommandLine.TypeConversionException("Rotation parameters must be numeric.");
            }
        }
    }

    public static class Step {

        @CommandLine.Option(
                names = {"--translate"},
                paramLabel = "<d0,d1,d2>",
                description = "Translate along the d0, d1, and d2 axes."
        )
        public Translation translate;

        @CommandLine.Option(
                names = {"--rotate"},
                paramLabel = "<d0,d1,d2,radians>",
                description = "Rotate around [d0, d1, d2] vector (radians)."
        )
        public Rotation rotate;

        @CommandLine.Option(
                names = {"--scale"},
                paramLabel = "<factor>",
                description = {"", "Scale the geometry uniformly."}
        )
        public Double scale;

        @CommandLine.Option(
                names = {"--pca"},
                description = {"", "Apply Principal Component Analysis (PCA) alignment."}
        )
        public boolean pca;
    }

    @CommandLine.ArgGroup(multiplicity = "0..*")
    public List<Step> steps;
}