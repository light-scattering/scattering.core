package eu.scattering.core.design.component.aggregate.validator.module;

import eu.scattering.core.design.type.FractalDimension;

public interface FValidatorFractalDimensionFactory {

    FValidatorFractalDimension fractalDimension(FractalDimension type, double expected, double error);
}
