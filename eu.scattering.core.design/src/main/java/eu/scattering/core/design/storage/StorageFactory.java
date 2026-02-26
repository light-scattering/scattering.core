package eu.scattering.core.design.storage;

import eu.scattering.core.design.storage.buffer.FBufferFactory;
import eu.scattering.core.design.storage.cache.FCacheFactory;
import eu.scattering.core.design.storage.layer.FLayerFactory;
import eu.scattering.core.design.storage.mesh.FMeshFactory;
import eu.scattering.core.design.storage.transfer.TransferFactory;

public interface StorageFactory extends
        TransferFactory, FBufferFactory, FMeshFactory, FCacheFactory, FLayerFactory {
}
