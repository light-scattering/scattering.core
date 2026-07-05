package eu.scattering.core.design.component;

import eu.scattering.core.design.component.aggregate.FAggregateAspectSave;
import eu.scattering.core.design.component.aggregate.monitor.FMonitorAspectSave;
import eu.scattering.core.design.component.geometry.GeometryAspectSave;

public interface ComponentAspectSave extends FAggregateAspectSave, FMonitorAspectSave, GeometryAspectSave {
}
