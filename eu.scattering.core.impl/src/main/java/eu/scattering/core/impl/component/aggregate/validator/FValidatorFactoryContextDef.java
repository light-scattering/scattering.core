package eu.scattering.core.impl.component.aggregate.validator;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.validator.FValidatorFactoryContext;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorFractalDimension;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorNoOverlap;
import eu.scattering.core.design.type.FractalDimension;
import eu.scattering.core.impl.component.aggregate.validator.module.FValidatorFractalDimensionDef;
import eu.scattering.core.impl.component.aggregate.validator.module.FValidatorNoOverlapDef;

public class FValidatorFactoryContextDef implements FValidatorFactoryContext {
    private final ScatFactory factory;

    private FValidatorFactoryContextDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FValidatorFactoryContext create(ScatFactory factory) {

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
