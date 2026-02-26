package eu.scattering.core.design.storage.buffer;

import eu.scattering.core.design.storage.buffer.transfer.FBufferTransferFactory;

public interface FBufferFactory extends FBufferTransferFactory {

    <T> FBuffer<T> getFBuffer(int capacity);
}
