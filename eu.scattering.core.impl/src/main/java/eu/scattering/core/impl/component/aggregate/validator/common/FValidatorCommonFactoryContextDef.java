package eu.scattering.core.impl.component.aggregate.validator.common;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.validator.common.FValidatorCommonFactoryContext;
import eu.scattering.core.design.component.aggregate.validator.common.module.FValidatorCommonFractalDimension;
import eu.scattering.core.design.type.FractalDimension;
import eu.scattering.core.impl.component.aggregate.validator.common.module.FValidatorCommonFractalDimensionDef;

public class FValidatorCommonFactoryContextDef implements FValidatorCommonFactoryContext {
    private final ScatFactory factory;

    private FValidatorCommonFactoryContextDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FValidatorCommonFactoryContext create(ScatFactory factory) {

        return new FValidatorCommonFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FValidatorCommonFractalDimension fractalDimension(FractalDimension type, double expected, double error) {

        return FValidatorCommonFractalDimensionDef.create(this.factory, type, expected, error);
    }
}
