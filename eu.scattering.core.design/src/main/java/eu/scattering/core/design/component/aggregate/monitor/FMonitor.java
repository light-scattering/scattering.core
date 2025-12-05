package eu.scattering.core.design.component.aggregate.monitor;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.function.BiConsumer;

public interface FMonitor extends BiConsumer<FAggregate, Shape> {

    void setSkip(int skip);
}
