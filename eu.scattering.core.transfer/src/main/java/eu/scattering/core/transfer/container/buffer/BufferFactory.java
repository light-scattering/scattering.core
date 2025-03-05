package eu.scattering.core.transfer.container.buffer;

import eu.scattering.core.transfer.container.buffer.FCache.FCacheFactory;
import eu.scattering.core.transfer.container.buffer.FLayer.FLayerFactory;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3DFactory;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DIFactory;

public interface BufferFactory extends FCacheFactory, FLayerFactory, FStream3DFactory, FStream3DIFactory {
}
