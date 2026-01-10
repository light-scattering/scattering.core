package eu.scattering.core.design.component.aggregate.validator.common.module;

import eu.scattering.core.design.type.FractalDimension;

public interface FValidatorCommonFractalDimensionFactory {

    FValidatorCommonFractalDimension fractalDimension(FractalDimension type, double expected, double error);
}
