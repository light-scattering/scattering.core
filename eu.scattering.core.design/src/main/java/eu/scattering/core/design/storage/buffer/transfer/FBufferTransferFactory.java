package eu.scattering.core.design.storage.buffer.transfer;

import eu.scattering.core.design.storage.StorageFactory;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;

public interface FBufferTransferFactory {

    FBufferData getFBufferData(StorageFactory factory, String tag, int layer);
}
