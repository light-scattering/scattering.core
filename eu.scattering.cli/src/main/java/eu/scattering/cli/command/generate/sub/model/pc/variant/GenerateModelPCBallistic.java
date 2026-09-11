package eu.scattering.cli.command.generate.sub.model.pc.variant;

import eu.scattering.cli.command.generate.GenerateHelper;
import eu.scattering.cli.command.generate.service.mixin.DistributionMixin;
import eu.scattering.cli.command.generate.service.mixin.TransformationMixin;
import eu.scattering.cli.command.generate.service.mixin.ValidationMixin;
import eu.scattering.cli.service.mixin.ExportMixin;
import eu.scattering.core.design.utility.type.option.Dimension;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "ballistic",
        description = "Generates a PC ballistic aggregate model.",
        usageHelpAutoWidth = true,
        footer = {
                "%nExample Configuration:%n  scatter-cli generate model pc ballistic -rf 500,1.0 -rn 1000,2.0,0.1 -e povray"
        }
)
public class GenerateModelPCBallistic implements Callable<Integer> {

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
            names = {"--2d"},
            description = {"", "Generate in two dimensions."}
    )
    public boolean d2;

    @Override
    public Integer call() throws Exception {

        return GenerateHelper.assemble(spec, transMixin, disMixin, valMixin, expMixin, (models, template) ->
                models.pc().ballistic(d2 ? Dimension.D2 : Dimension.D3, template));
    }
}
