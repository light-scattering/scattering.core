package eu.scattering.cli.command.transform;

import eu.scattering.cli.command.transform.service.TransformService;
import eu.scattering.cli.command.transform.service.mixin.TransformMixin;
import eu.scattering.cli.service.ExportService;
import eu.scattering.cli.service.ImportService;
import eu.scattering.cli.service.mixin.ExportMixin;
import eu.scattering.cli.service.mixin.ImportMixin;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.impl.factory.ScatterFactoryDef;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "transform",
        description = "Applies sequential transformations to an existing aggregate model.",
        usageHelpAutoWidth = true,
        abbreviateSynopsis = true,
        footer = {
                "%nExample Configuration:%n  scatter-cli transform input.xyz --pca --rotate 90,0,0 --translate 10.5,0,0 -e povray"
        }
)
public class Transform implements Callable<Integer> {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Mixin
    private TransformMixin transMixin;

    @CommandLine.Mixin
    private ImportMixin importMixin;

    @CommandLine.Mixin
    private ExportMixin exportMixin;

    @CommandLine.Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = {"", "Show this help message and exit."}
    )
    private boolean helpRequested;

    @CommandLine.Option(
            names = {"-V", "--version"},
            versionHelp = true,
            description = {"", "Print version information and exit."}
    )
    private boolean versionRequested;

    @Override
    public Integer call() throws Exception {
        ScatterFactory factory = ScatterFactoryDef.create();
        String results = "";

        try {
            FAggregate fAggregate = ImportService.load(factory, importMixin)
                    .orElseThrow(() -> new IllegalArgumentException("The geometry could not be imported."));

            TransformService.transform(factory, fAggregate, transMixin);

            results = ExportService.export(factory, fAggregate, exportMixin);

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage() + "\n");
            spec.commandLine().usage(System.err);

            return 1;
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.getMessage());

            return 2;
        }

        System.out.println(results);

        return 0;
    }
}
