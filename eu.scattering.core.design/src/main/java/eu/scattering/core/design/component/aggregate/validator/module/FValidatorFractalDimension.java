package eu.scattering.core.design.component.aggregate.validator.module;

import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.validator.FValidator;
import eu.scattering.core.design.statistics.base.FStat;

public interface FValidatorFractalDimension extends FValidator {

    @Modificator
    FStat getRefFStat();
}
