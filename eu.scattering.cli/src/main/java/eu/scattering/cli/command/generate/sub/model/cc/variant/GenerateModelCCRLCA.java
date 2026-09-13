package eu.scattering.cli.command.generate.sub.model.cc.variant;

import eu.scattering.cli.command.generate.GenerateHelper;
import eu.scattering.cli.command.generate.service.mixin.*;
import eu.scattering.cli.service.mixin.ExportMixin;
import eu.scattering.cli.service.mixin.HelpMixin;
import eu.scattering.core.design.utility.type.option.Dimension;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "rlca",
        description = "Generates a Reaction-Limited Cluster-Aggregation (RLCA) model.",
        usageHelpAutoWidth = true,
        footer = {
                "%nExample Configuration:%n  scatter-cli generate model cc rlca --rf 500,1.0 --rn 1000,2.0,0.1 -e povray"
        }
)
public class GenerateModelCCRLCA implements Callable<Integer> {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Mixin
    private HelpMixin helpMixin;

    @CommandLine.Mixin
    private TransformationMixin transMixin;

    @CommandLine.Mixin
    private DistributionMixin distMixin;

    @CommandLine.Mixin
    private ValidationMixin valMixin;

    @CommandLine.Mixin
    private DimensionMixin dimMixin;

    @CommandLine.Mixin
    private SymmetryMixin symMixin;

    @CommandLine.Mixin
    private ExportMixin expMixin;

    @Override
    public Integer call() throws Exception {

        return GenerateHelper.assemble(spec, transMixin, distMixin, valMixin, expMixin, (models, template) ->
                models.cc().rlca(dimMixin.d2 ? Dimension.D2 : Dimension.D3, template).setSymmetry(!symMixin.asymmetric));
    }
}
