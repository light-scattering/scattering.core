package eu.scattering.core.design.storage;

import eu.scattering.core.transfer.container.box.BoxFactory;
import eu.scattering.core.transfer.container.buffer.BufferFactory;
import eu.scattering.core.transfer.container.storage.StorageFactory;

public interface ContainerFactory extends StorageFactory, BufferFactory, BoxFactory {
}
