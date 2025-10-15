package eu.scattering.core.design;

import eu.scattering.core.design.engine.EngineFactory;
import eu.scattering.core.design.component.ComponentFactory;
import eu.scattering.core.design.helper.HelperFactory;
import eu.scattering.core.design.statistics.StatisticsFactory;
import eu.scattering.core.design.storage.StorageFactory;
import eu.scattering.core.transfer.container.storage.XXXFactory;
import eu.scattering.core.transfer.helper.transfer.FPositionHelperFactory;

public interface ScatFactory extends XXXFactory, FPositionHelperFactory, ComponentFactory, EngineFactory, HelperFactory, StatisticsFactory, StorageFactory {
}
