package eu.scattering.core.design.component.aggregate.monitor.cc;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.FMonitor;

import java.util.function.BiConsumer;

public interface FMonitorCC extends FMonitor, BiConsumer<FAggregate, FAggregate> {
}
