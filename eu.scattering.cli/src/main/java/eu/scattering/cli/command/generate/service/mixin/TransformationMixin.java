package eu.scattering.cli.command.generate.service.mixin;

import picocli.CommandLine;

public class TransformationMixin {

    @CommandLine.Option(
            names = {"--pca"},
            description = "Apply Principal Component Analysis (PCA) alignment."
    )
    public boolean pca;
}
