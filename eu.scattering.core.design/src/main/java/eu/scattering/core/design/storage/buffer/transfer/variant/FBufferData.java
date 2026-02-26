package eu.scattering.core.design.storage.buffer.transfer.variant;

import eu.scattering.core.design.storage.transfer.Transfer;

public interface FBufferData extends Transfer {

    int getLayerIndex();

    String getMeta();

    void setMeta(String meta);
}
