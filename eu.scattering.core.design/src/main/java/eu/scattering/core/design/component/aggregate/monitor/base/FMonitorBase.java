package eu.scattering.core.design.component.aggregate.monitor.base;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.base.FStat1D;

import java.util.function.BiConsumer;

public interface FMonitorBase extends BiConsumer<FAggregate, Shape> {

    FStat1D getResults();
}
