package eu.scattering.cli.command.generate;

import eu.scattering.cli.command.generate.sub.geometry.GenerateGeometry;
import eu.scattering.cli.command.generate.sub.model.GenerateModel;
import eu.scattering.cli.service.mixin.HelpMixin;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "generate",
        description = "Generates sphere assemblies.",
        usageHelpAutoWidth = true,
        abbreviateSynopsis = true,
        subcommands = {
                GenerateGeometry.class,
                GenerateModel.class
        }
)
public class Generate implements Callable<Integer> {

    @CommandLine.Mixin
    private HelpMixin helpMixin;

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);

        return 0;
    }
}
