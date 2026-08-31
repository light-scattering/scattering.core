package eu.scattering.core.design.component.aggregate.monitor.cc;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.FMonitor;
import eu.scattering.core.design.utility.lambda.TriConsumer;

public interface FMonitorCC extends FMonitor, TriConsumer<FAggregate, FAggregate, Integer> {
}
