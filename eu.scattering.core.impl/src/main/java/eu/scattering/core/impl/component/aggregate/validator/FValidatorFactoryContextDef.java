package eu.scattering.core.impl.component.aggregate.validator;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.validator.FValidatorFactoryContext;
import eu.scattering.core.design.component.aggregate.validator.common.FValidatorCommonFactoryContext;
import eu.scattering.core.impl.component.aggregate.validator.common.FValidatorCommonFactoryContextDef;

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
    public FValidatorCommonFactoryContext pc() {

        return FValidatorCommonFactoryContextDef.create(this.factory);
    }
}
