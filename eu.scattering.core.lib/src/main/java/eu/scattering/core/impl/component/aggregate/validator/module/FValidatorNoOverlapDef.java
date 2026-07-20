package eu.scattering.core.impl.component.aggregate.validator.module;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorNoOverlap;
import eu.scattering.core.design.utility.type.variant.OverlapFactor;

import static eu.scattering.core.impl.ScatterCoreConfig.EPSILON;

public class FValidatorNoOverlapDef implements FValidatorNoOverlap {

    private FValidatorNoOverlapDef() {
    }

    public static FValidatorNoOverlap create() {

        return new FValidatorNoOverlapDef();
    }

    @Override
    public Boolean apply(FAggregate fAggregate, Integer integer) {

        return fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max() <= EPSILON;
    }
}
