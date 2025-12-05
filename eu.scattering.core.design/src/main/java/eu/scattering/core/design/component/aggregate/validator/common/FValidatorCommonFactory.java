package eu.scattering.core.design.component.aggregate.validator.common;

import eu.scattering.core.design.component.aggregate.validator.common.module.FValidatorFractalDimension;
import eu.scattering.core.design.type.FractalDimension;

public interface FValidatorCommonFactory {

    FValidatorFractalDimension getFValidatorFractalDimension(FractalDimension type, double expected, double error);
}
