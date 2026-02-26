package eu.scattering.core.design.component.aggregate.monitor.pc;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.FMonitor;
import eu.scattering.core.design.component.geometry.shape.Shape;

import java.util.function.BiConsumer;

public interface FMonitorPC extends FMonitor, BiConsumer<FAggregate, Shape> {

    void setSkip(int skip);
}
