package eu.scattering.core.design.component.aggregate.model.cc;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.utility.lambda.TriConsumer;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface FModelCC extends FModel {

    boolean getSymmetry();
    void setSymmetry(boolean asymmetry);

    // -------------------------------------------------------------------------------------------------

    void addStepMonitor(TriConsumer<FAggregate, FAggregate, Integer> monitor);
    void addStepAcceptor(BiFunction<FAggregate, FAggregate, Boolean> acceptor);

    void addFragmentViewer(Consumer<FAggregate> monitor);

    // -------------------------------------------------------------------------------------------------

    default void addStepMonitor(Collection<TriConsumer<FAggregate, FAggregate, Integer>> monitors) {

        for (var monitor : monitors) {
            addStepMonitor(monitor);
        }
    }

    default void addStepAcceptor(Collection<BiFunction<FAggregate, FAggregate, Boolean>> acceptors) {

        for (var acceptor : acceptors) {
            addStepAcceptor(acceptor);
        }
    }

    default void addFragmentViewer(Collection<Consumer<FAggregate>> viewers) {

        for (var viewer : viewers) {
            addFragmentViewer(viewer);
        }
    }
}
