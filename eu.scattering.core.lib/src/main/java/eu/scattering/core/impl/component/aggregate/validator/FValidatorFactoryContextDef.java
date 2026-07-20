package eu.scattering.core.impl.component.aggregate.validator;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.validator.FValidatorFactoryContext;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorFractalDimension;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorNoOverlap;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import eu.scattering.core.impl.component.aggregate.validator.module.FValidatorFractalDimensionDef;
import eu.scattering.core.impl.component.aggregate.validator.module.FValidatorNoOverlapDef;

public class FValidatorFactoryContextDef implements FValidatorFactoryContext {
    private final ScatterFactory factory;

    private FValidatorFactoryContextDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FValidatorFactoryContext create(ScatterFactory factory) {

        return new FValidatorFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FValidatorFractalDimension fractalDimension(FractalDimension type, double expected, double error) {

        return FValidatorFractalDimensionDef.create(this.factory, type, expected, error);
    }

    @Override
    public FValidatorNoOverlap noOverlap() {

        return FValidatorNoOverlapDef.create();
    }
}
