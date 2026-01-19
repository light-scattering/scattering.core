package eu.scattering.core.design.component.aggregate.model.cc;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface FModelCC extends FModel {

    boolean getSymmetry();
    void setSymmetry(boolean asymmetry);

    // -------------------------------------------------------------------------------------------------

    void addStepMonitor(BiConsumer<FAggregate, FAggregate> monitor);
    void addStepAcceptor(BiFunction<FAggregate, FAggregate, Boolean> acceptor);

    // -------------------------------------------------------------------------------------------------

    default void addStepMonitor(Collection<BiConsumer<FAggregate, FAggregate>> monitors) {

        for (var monitor : monitors) {
            addStepMonitor(monitor);
        }
    }

    default void addStepAcceptor(Collection<BiFunction<FAggregate, FAggregate, Boolean>> acceptors) {

        for (var acceptor : acceptors) {
            addStepAcceptor(acceptor);
        }
    }
}
