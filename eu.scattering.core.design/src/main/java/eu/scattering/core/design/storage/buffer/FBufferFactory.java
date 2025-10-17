package eu.scattering.core.design.storage.buffer;

public interface FBufferFactory {

    <T> FBuffer<T> getFBuffer();

    <T> FBuffer<T> getFBuffer(int length);
}
