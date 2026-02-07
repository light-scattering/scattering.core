package eu.scattering.core.design.transfer.complex;

import eu.scattering.core.design.storage.StorageFactory;
import eu.scattering.core.design.storage.box.variant.FBoxString;
import eu.scattering.core.design.transfer.Transfer;
import org.json.JSONObject;

public class FBufferData implements Transfer {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "meta";
    private static final String JSON_LAYER = "layer";
    private static final String JSON_META = "tag";

    private final int layer;

    private final FBoxString meta;

    private FBufferData(StorageFactory factory, String meta, int layerIndex) {

        this.layer = layerIndex;

        this.meta = factory.getFBoxString();
        this.meta.setValue(meta);
    }

    public static FBufferData create(StorageFactory factory, String tag, int layer) {

        return new FBufferData(factory, tag, layer);
    }

    public int getLayerIndex() {

        return this.layer;
    }

    public String getMeta() {

        return this.meta.getValue();
    }

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
