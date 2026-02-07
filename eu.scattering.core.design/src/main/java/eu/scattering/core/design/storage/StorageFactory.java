package eu.scattering.core.design.storage;

import eu.scattering.core.design.storage.box.FBoxFactory;
import eu.scattering.core.design.storage.mesh.FMeshFactory;
import eu.scattering.core.design.storage.buffer.FBufferFactory;
import eu.scattering.core.design.storage.cache.FCacheFactory;
import eu.scattering.core.design.storage.layer.FLayerFactory;
import eu.scattering.core.design.storage.polynomial.FPolynomialFactory;
import eu.scattering.core.design.storage.transfer.FPositionFactory;
import eu.scattering.core.design.transfer.TransferFactory;

public interface StorageFactory extends
        TransferFactory,
        FBoxFactory, FPositionFactory,
        FPolynomialFactory, FBufferFactory, FMeshFactory, FCacheFactory, FLayerFactory {
}
