package eu.scattering.core.design.component.aggregate.model.pc;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface FModelPC extends FModel {

    void addStepMonitor(BiConsumer<FAggregate, Shape> monitor);
    void addStepAcceptor(BiFunction<FAggregate, Shape, Boolean> acceptor);

    void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator);

    // -------------------------------------------------------------------------------------------------

    default void addStepMonitor(Collection<BiConsumer<FAggregate, Shape>> monitors) {

        for (var monitor : monitors) {
            addStepMonitor(monitor);
        }
    }

    default void addStepAcceptor(Collection<BiFunction<FAggregate, Shape, Boolean>> acceptors) {

        for (var acceptor : acceptors) {
            addStepAcceptor(acceptor);
        }
    }

    default void addCompletionValidator(Collection<BiFunction<FAggregate, Integer, Boolean>> validators) {

        for (var validator : validators) {
            addCompletionValidator(validator);
        }
    }
}
