package eu.scattering.core.design.storage;

import eu.scattering.core.design.storage.mesh.FMeshFactory;
import eu.scattering.core.design.storage.buffer.FBufferFactory;
import eu.scattering.core.design.storage.cache.concurrent.FCacheThreadFactory;
import eu.scattering.core.design.storage.cache.serial.FCacheFactory;
import eu.scattering.core.design.storage.layer.FLayerFactory;

public interface StorageFactory extends FBufferFactory, FMeshFactory,
        FCacheFactory, FCacheThreadFactory,
        FLayerFactory {
}
