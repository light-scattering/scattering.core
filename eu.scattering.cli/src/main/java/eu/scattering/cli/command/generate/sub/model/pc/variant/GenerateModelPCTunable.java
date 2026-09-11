package eu.scattering.cli.command.generate.sub.model.pc.variant;

import eu.scattering.cli.command.generate.GenerateHelper;
import eu.scattering.cli.command.generate.service.mixin.DistributionMixin;
import eu.scattering.cli.command.generate.service.mixin.TransformationMixin;
import eu.scattering.cli.command.generate.service.mixin.ValidationMixin;
import eu.scattering.cli.service.mixin.ExportMixin;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "tunable",
        description = "Generates a PC tunable aggregate model.",
        usageHelpAutoWidth = true,
        footer = {
                "%nExample Configuration:%n  scatter-cli generate model pc tunable -df 1.8 -kf 1.3 -rn 1000,2.0,0.1 -e povray"
        }
)
public class GenerateModelPCTunable implements Callable<Integer> {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Mixin
    private TransformationMixin transMixin;

    @CommandLine.Mixin
    private DistributionMixin disMixin;

    @CommandLine.Mixin
    private ValidationMixin valMixin;

    @CommandLine.Mixin
    private ExportMixin expMixin;

    @CommandLine.Option(
            names = {"-df"},
            required = true,
            description = "Fractal dimension."
    )
    public double df;

    @CommandLine.Option(
            names = {"-kf"},
            required = true,
            description = "Fractal prefactor."
    )
    public double kf;

    @Override
    public Integer call() throws Exception {

        if (df <= 0) {
            System.err.println("Error: The fractal dimension must be greater than zero.\n");
            spec.commandLine().usage(System.err);

            return 1;
        }

        if (kf <= 0) {
            System.err.println("Error: The fractal prefactor must be greater than zero.\n");
            spec.commandLine().usage(System.err);

            return 1;
        }

        return GenerateHelper.assemble(spec, transMixin, disMixin, valMixin, expMixin, (models, template) ->
                models.pc().tunable(template, df, kf));
    }
}
