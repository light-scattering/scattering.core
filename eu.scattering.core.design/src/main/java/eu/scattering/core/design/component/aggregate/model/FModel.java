package eu.scattering.core.design.component.aggregate.model;

import eu.scattering.core.design.component.aggregate.FAggregate;

import java.util.Collection;
import java.util.function.BiFunction;

public interface FModel {

    void build();

    void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator);

    // -------------------------------------------------------------------------------------------------

    default void addCompletionValidator(Collection<BiFunction<FAggregate, Integer, Boolean>> validators) {

        for (var validator : validators) {
            addCompletionValidator(validator);
        }
    }
}
