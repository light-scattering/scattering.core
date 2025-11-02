package eu.scattering.core.design.component.aggregate.monitor;

import eu.scattering.core.design.component.aggregate.monitor.base.FMonitorBaseFactory;
import eu.scattering.core.design.component.aggregate.monitor.construct.FMonitorConstructFactory;
import eu.scattering.core.design.component.aggregate.monitor.primitive.FMonitorPrimitiveFactory;

public interface FMonitorFactory extends FMonitorBaseFactory, FMonitorConstructFactory, FMonitorPrimitiveFactory {
}
