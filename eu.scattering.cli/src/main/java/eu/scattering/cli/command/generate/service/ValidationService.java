package eu.scattering.cli.command.generate.service;

import eu.scattering.cli.command.generate.service.mixin.ValidationMixin;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.utility.type.variant.FractalDimension;

public class ValidationService {

    public static void addValidators(ScatterFactory factory, ValidationMixin valMixin, FModel model) {

            addDfBc(factory, valMixin, model);
            addDfDc(factory, valMixin, model);
            addDfMr(factory, valMixin, model);
    }

    //---------------------------------------------------------------------

    private static void addDfBc(ScatterFactory factory, ValidationMixin valMixin, FModel model) {

        if (valMixin.dfBc != null) {

            if (valMixin.dfBc.target() <= 0) {
                throw new IllegalArgumentException("The expected value must be greater than zero.");
            }

            if (valMixin.dfBc.error() < 0) {
                throw new IllegalArgumentException("The error value must be greater than zero.");
            }

            model.addCompletionValidator(factory.validators().fractalDimension(FractalDimension.BC_OPTIMIZED, valMixin.dfBc.target(), valMixin.dfBc.error()));
        }
    }

    private static void addDfDc(ScatterFactory factory, ValidationMixin valMixin, FModel model) {

        if (valMixin.dfDc != null) {

            if (valMixin.dfDc.target() <= 0) {
                throw new IllegalArgumentException("The expected value must be greater than zero.");
            }

            if (valMixin.dfDc.error() < 0) {
                throw new IllegalArgumentException("The error value must be greater than zero.");
            }

            model.addCompletionValidator(factory.validators().fractalDimension(FractalDimension.DC_RESTRICTED, valMixin.dfDc.target(), valMixin.dfDc.error()));
        }
    }

    private static void addDfMr(ScatterFactory factory, ValidationMixin valMixin, FModel model) {

        if (valMixin.dfMr != null) {

            if (valMixin.dfMr.target() <= 0) {
                throw new IllegalArgumentException("The expected value must be greater than zero.");
            }

            if (valMixin.dfMr.error() < 0) {
                throw new IllegalArgumentException("The error value must be greater than zero.");
            }

            model.addCompletionValidator(factory.validators().fractalDimension(FractalDimension.MR_RESTRICTED, valMixin.dfMr.target(), valMixin.dfMr.error()));
        }
    }
}
