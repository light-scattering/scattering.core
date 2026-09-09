package eu.scattering.cli.command.generate.sub.model;

import eu.scattering.cli.command.generate.sub.model.cc.GenerateModelCC;
import eu.scattering.cli.command.generate.sub.model.pc.GenerateModelPC;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "model",
        description = "Generates synthetic fractal-like aggregate models.",
        usageHelpAutoWidth = true,
        subcommands = {
                GenerateModelPC.class,
                GenerateModelCC.class
        }
)
public class GenerateModel implements Callable<Integer> {

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);

        return 0;
    }
}
