package eu.scattering.core.design.transfer.complex;

import org.json.JSONObject;

public interface FMetaData {

    int getLayerIndex();

    String getMeta();
    void setMeta(String meta);

    JSONObject toJSON();
}
