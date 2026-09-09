package eu.scattering.cli.command.generate.sub.model.pc.variant;

import eu.scattering.cli.command.generate.GenerateHelper;
import eu.scattering.cli.command.generate.service.mixin.DistributionMixin;
import eu.scattering.cli.service.mixin.ExportMixin;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "dla",
        description = "Generates a Diffusion-Limited Aggregation (DLA) model.",
        usageHelpAutoWidth = true,
        footer = {
                "%nExample Configuration:%n  scatter-cli generate model pc dla -rf 500,1.0 -rn 1000,2.0,0.1 -e povray"
        }
)
public class GenerateModelPCDLA implements Callable<Integer> {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Mixin
    private DistributionMixin dist;

    @CommandLine.Mixin
    private ExportMixin export;

    @Override
    public Integer call() throws Exception {

        return GenerateHelper.assemble(spec, dist, export, (models, template) ->
                models.pc().dla(template).build());
    }
}
