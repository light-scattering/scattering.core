package eu.scattering.core.design.storage;

import eu.scattering.core.design.storage.mutable.box.BoxFactory;
import eu.scattering.core.design.storage.mutable.buffer.BufferFactory;

public interface StorageFactory extends BufferFactory, BoxFactory {
}
