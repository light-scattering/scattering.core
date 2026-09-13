package eu.scattering.cli.command.generate.sub.model.cc;

import eu.scattering.cli.command.generate.sub.model.cc.variant.GenerateModelCCBallistic;
import eu.scattering.cli.command.generate.sub.model.cc.variant.GenerateModelCCDLCA;
import eu.scattering.cli.command.generate.sub.model.cc.variant.GenerateModelCCRLCA;
import eu.scattering.cli.command.generate.sub.model.cc.variant.GenerateModelCCTunable;
import eu.scattering.cli.service.mixin.HelpMixin;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "cc",
        description = "Generates synthetic CC models.",
        usageHelpAutoWidth = true,
        subcommands = {
                GenerateModelCCBallistic.class,
                GenerateModelCCTunable.class,
                GenerateModelCCRLCA.class,
                GenerateModelCCDLCA.class
        }
)
public class GenerateModelCC implements Callable<Integer> {

    @CommandLine.Mixin
    private HelpMixin helpMixin;

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);

        return 0;
    }
}
