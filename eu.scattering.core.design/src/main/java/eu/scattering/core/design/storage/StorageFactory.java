package eu.scattering.core.design.storage;

import eu.scattering.core.design.storage.buffer.mesh.FArrayMeshFactory;
import eu.scattering.core.design.storage.buffer.universal.FArrayFactory;
import eu.scattering.core.design.storage.cache.concurrent.FCacheThreadFactory;
import eu.scattering.core.design.storage.cache.serial.FCacheFactory;
import eu.scattering.core.design.storage.layer.FLayerCounterFactory;

public interface StorageFactory extends FArrayFactory, FArrayMeshFactory,
        FCacheFactory, FCacheThreadFactory,
        FLayerCounterFactory {
}
