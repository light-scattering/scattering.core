package eu.scattering.core.design.component.aggregate.validator;

import eu.scattering.core.design.component.aggregate.FAggregate;

import java.util.function.BiFunction;

public interface FValidator extends BiFunction<FAggregate, Integer, Boolean> {
}
