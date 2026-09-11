package eu.scattering.cli.command.generate;

import eu.scattering.cli.command.generate.service.TransformationService;
import eu.scattering.cli.command.generate.service.ValidationService;
import eu.scattering.cli.command.generate.service.mixin.TransformationMixin;
import eu.scattering.cli.command.generate.service.mixin.ValidationMixin;
import eu.scattering.cli.service.ExportService;
import eu.scattering.cli.command.generate.service.DistributionService;
import eu.scattering.cli.command.generate.service.mixin.DistributionMixin;
import eu.scattering.cli.service.mixin.ExportMixin;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.aggregate.model.FModelFactoryContext;
import eu.scattering.core.impl.ScatterCore;
import picocli.CommandLine;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class GenerateHelper {

    public static int assemble(
            CommandLine.Model.CommandSpec spec,
            TransformationMixin transMixin,
            DistributionMixin disMixin,
            ValidationMixin valMixin,
            ExportMixin expMixin,
            BiFunction<FModelFactoryContext, FAggregate, FModel> builder)
    {
        ScatterFactory factory = ScatterCore.createFactory();
        String results = "";

        try {
            FAggregate template = DistributionService.assemble(factory, disMixin);

            FModel model = builder.apply(factory.models(), template);

            ValidationService.addValidators(factory, valMixin, model);

            model.build();

            TransformationService.transform(template, transMixin);

            if (!template.isPointConnected()) {
                System.out.println("Particles are not in point contact.");

                return 2;
            }

            results = ExportService.export(factory, template, expMixin);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage() + "\n");
            spec.commandLine().usage(System.err);

            return 1;
        } catch (IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());

            return 2;
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.getMessage());

            return 2;
        }

        System.out.println(results);

        return 0;
    }
}
