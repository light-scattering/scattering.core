package eu.scattering.cli.command.generate.sub.model.pc;

import eu.scattering.cli.command.generate.sub.model.pc.variant.GenerateModelPCBallistic;
import eu.scattering.cli.command.generate.sub.model.pc.variant.GenerateModelPCDLA;
import eu.scattering.cli.command.generate.sub.model.pc.variant.GenerateModelPCRLA;
import eu.scattering.cli.command.generate.sub.model.pc.variant.GenerateModelPCTunable;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "pc",
        description = "Generates synthetic PC models.",
        usageHelpAutoWidth = true,
        subcommands = {
                GenerateModelPCBallistic.class,
                GenerateModelPCTunable.class,
                GenerateModelPCRLA.class,
                GenerateModelPCDLA.class
        }
)
public class GenerateModelPC implements Callable<Integer> {

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);

        return 0;
    }
}
