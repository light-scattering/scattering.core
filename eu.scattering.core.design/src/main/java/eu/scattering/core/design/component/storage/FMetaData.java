package eu.scattering.core.design.component.storage;

import org.json.JSONObject;

public interface FMetaData {

    int getLayerIndex();

    String getMeta();
    void setMeta(String meta);

    JSONObject toJSON();
}
