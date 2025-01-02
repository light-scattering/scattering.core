package eu.scattering.core.transfer.containers;

import eu.scattering.core.transfer.containers.grid.GridFactory;
import eu.scattering.core.transfer.containers.engine.EngineFactory;
import eu.scattering.core.transfer.containers.position.*;

public interface ContainerFactory extends EngineFactory, PositionFactory, GridFactory {
}
