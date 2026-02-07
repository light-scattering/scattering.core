package eu.scattering.core.design;

import eu.scattering.core.design.component.ComponentFactory;
import eu.scattering.core.design.aspect.AspectFactory;
import eu.scattering.core.design.helper.HelperFactory;
import eu.scattering.core.design.physics.PhysicsFactory;
import eu.scattering.core.design.statistics.StatisticsFactory;
import eu.scattering.core.design.storage.StorageFactory;

public interface ScatFactory extends ComponentFactory, AspectFactory, HelperFactory, StatisticsFactory, StorageFactory, PhysicsFactory {
}
