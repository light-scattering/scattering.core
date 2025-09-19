package eu.scattering.core.design.util.container;

import org.json.JSONObject;

public interface FMetaData {

    //--- Immutable

    int getLayerIndex();

    //--- Mutable

    String getMeta();
    void setMeta(String meta);

    JSONObject toJSON();
}
