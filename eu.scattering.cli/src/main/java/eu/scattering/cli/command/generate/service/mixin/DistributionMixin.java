package eu.scattering.cli.command.generate.service.mixin;

import picocli.CommandLine;

import java.util.List;

public class DistributionMixin {

    @CommandLine.Option(
            names = {"-rf"},
            converter = DistributionMixinConverter.class,
            description = {
                    "Radius distribution - Fixed.",
                    "Format: -rf <count,radius>",
                    "Example: -rf 1000,1.5"
            }
    )
    public List<double[]> fixed;

    @CommandLine.Option(
            names = {"-rn"},
            converter = DistributionMixinConverter.class,
            description = {
                    "Radius distribution - Normal.",
                    "Format: -rn <count,avg,std>",
                    "Example: -rn 1000,2.0,0.1"
            }
    )
    public List<double[]> normal;

    @CommandLine.Option(
            names = {"-ru"},
            converter = DistributionMixinConverter.class,
            description = {
                    "Radius distribution - Uniform.",
                    "Format: -ru <count,min,max>",
                    "Example: -ru 1000,1.0,3.0"
            }
    )
    public List<double[]> uniform;
}

class DistributionMixinConverter implements CommandLine.ITypeConverter<double[]> {

    @Override
    public double[] convert(String value) {
        String[] tokens = value.split(",");
        double[] result = new double[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            result[i] = Double.parseDouble(tokens[i].trim());
        }

        return result;
    }
}
