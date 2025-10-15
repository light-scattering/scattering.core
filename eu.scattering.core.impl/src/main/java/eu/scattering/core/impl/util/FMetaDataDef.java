package eu.scattering.core.impl.util;

import eu.scattering.core.design.component.storage.FMetaData;
import eu.scattering.core.design.storage.StorageFactoryConcrete;
import eu.scattering.core.design.storage.mutable.box.item.FBoxString;
import org.json.JSONObject;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FMetaDataDef implements FMetaData {
    private static final StorageFactoryConcrete factoryExt = StorageFactoryConcrete.create();
    private static final String JSON_MAIN = "meta";
    private static final String JSON_LAYER = "layer";
    private static final String JSON_META = "tag";

    private final int layer;

    private final FBoxString meta = factoryExt.getFBoxString();

    private FMetaDataDef(String meta, int layerIndex) {

        this.layer = layerIndex;
        this.meta.setValue(meta);
    }

    public static FMetaData crete(String tag, int layer) {

        return new FMetaDataDef(tag, layer);
    }

    @Override
    public int getLayerIndex() {

        return this.layer;
    }

    @Override
    public String getMeta() {

        return this.meta.getValue();
    }

    @Override
    public void setMeta(String meta) {

        this.meta.setValue(meta);
    }

    //--------------------------------------------------

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_LAYER, this.layer);
        json.put(JSON_META, this.meta.toJSON());

        return json;
    }

    //--------------------------------------------------

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
