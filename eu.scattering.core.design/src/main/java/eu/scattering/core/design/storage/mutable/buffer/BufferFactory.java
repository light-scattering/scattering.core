package eu.scattering.core.design.storage.mutable.buffer;

import eu.scattering.core.design.storage.mutable.buffer.array.FArrayFactory;
import eu.scattering.core.design.storage.mutable.buffer.array.FArrayMeshFactory;
import eu.scattering.core.design.storage.mutable.buffer.cache.FCacheFactory;
import eu.scattering.core.design.storage.mutable.buffer.cache.FCacheThreadFactory;
import eu.scattering.core.design.storage.mutable.buffer.layer.FLayerCounterFactory;

public interface BufferFactory extends
        FCacheFactory, FCacheThreadFactory,
        FLayerCounterFactory,
        FArrayFactory, FArrayMeshFactory {
}
