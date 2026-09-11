package eu.scattering.cli.command.transform.service.mixin;

import picocli.CommandLine;
import java.util.List;

public class TransformMixin {

    public static class Step {

        @CommandLine.Option(
                names = {"--translate"},
                paramLabel = "<d0,d1,d2>",
                split = ",",
                description = "Translate along the d0, d1, and d2 axes."
        )
        public double[] translate;

        @CommandLine.Option(
                names = {"--rotate"},
                paramLabel = "<d0,d1,d2,radians>",
                split = ",",
                description = "Rotate around [d0, d1, d2] vector (radians)."
        )
        public double[] rotate;

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