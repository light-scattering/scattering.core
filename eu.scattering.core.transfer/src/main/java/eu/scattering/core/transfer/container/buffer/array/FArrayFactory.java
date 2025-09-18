package eu.scattering.core.transfer.container.buffer.array;

import eu.scattering.core.transfer.container.buffer.array.concrete.FArrayDef;

public interface FArrayFactory {

    default <T> FArray<T> getFArray() {

        return FArrayDef.create();
    }

    default <T> FArray<T> getFArray(int length) {

        return FArrayDef.create(length);
    }
}
