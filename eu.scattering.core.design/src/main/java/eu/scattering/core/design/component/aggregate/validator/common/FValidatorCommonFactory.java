package eu.scattering.core.design.component.aggregate.validator.common;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.validator.common.module.FValidatorFractalDimension;

public interface FValidatorCommonFactory {

    FValidatorFractalDimension getFValidatorFractalDimension(FAggregate.Dimension type, double expected, double error);
}
