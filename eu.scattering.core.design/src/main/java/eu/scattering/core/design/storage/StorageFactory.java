package eu.scattering.core.design.storage;

import eu.scattering.core.design.storage.box.FBoxFactory;
import eu.scattering.core.design.storage.mesh.FMeshFactory;
import eu.scattering.core.design.storage.buffer.FBufferFactory;
import eu.scattering.core.design.storage.cache.FCacheFactory;
import eu.scattering.core.design.storage.layer.FLayerFactory;

public interface StorageFactory extends FBoxFactory, FBufferFactory, FMeshFactory, FCacheFactory, FLayerFactory {
}
