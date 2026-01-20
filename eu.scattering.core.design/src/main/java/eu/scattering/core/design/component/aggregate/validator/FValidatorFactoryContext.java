package eu.scattering.core.design.component.aggregate.validator;

import eu.scattering.core.design.component.aggregate.validator.module.FValidatorFractalDimensionFactory;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorNoOverlapFactory;

public interface FValidatorFactoryContext extends FValidatorNoOverlapFactory, FValidatorFractalDimensionFactory {
}
