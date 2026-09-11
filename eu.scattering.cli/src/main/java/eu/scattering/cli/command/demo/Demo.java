package eu.scattering.cli.command.demo;

import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "demo",
        description = "Generates a DLCA aggregate with predefined parameters."
)
public class Demo implements Callable<Integer> {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @Override
    public Integer call() {

        String[] args = {
                "generate", "model", "cc", "tunable",
                "-df", "1.8", "-kf", "1.3", "-rf", "2048,1.0",
                "-e", "multisphere"
        };

        try {
            return spec.root().commandLine().execute(args);
        } catch (Exception e) {
            System.err.println("Unexpected error during demo execution: " + e.getMessage());

            return 2;
        }
    }
}