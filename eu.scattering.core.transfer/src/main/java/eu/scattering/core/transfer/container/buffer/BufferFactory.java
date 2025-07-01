package eu.scattering.core.transfer.container.buffer;

import eu.scattering.core.transfer.container.buffer.cache.FCacheFactory;
import eu.scattering.core.transfer.container.buffer.cache.FCacheThreadFactory;
import eu.scattering.core.transfer.container.buffer.layer.FLayerFactory;
import eu.scattering.core.transfer.container.buffer.array.FArrayFactory;
import eu.scattering.core.transfer.container.buffer.array.FArrayMeshFactory;

public interface BufferFactory extends
        FCacheFactory, FCacheThreadFactory,
        FLayerFactory,
        FArrayFactory, FArrayMeshFactory {
}
