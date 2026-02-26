package eu.scattering.core.impl.storage.buffer;

import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxString;
import org.json.JSONObject;

public class FBufferDataDef implements FBufferData {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "meta";
    private static final String JSON_LAYER = "layer";
    private static final String JSON_META = "tag";

    private final int layer;

    private final FBoxString meta;

    private FBufferDataDef(TransferFactory factoryExt, String meta, int layerIndex) {

        this.layer = layerIndex;

        this.meta = factoryExt.getFBoxString();
        this.meta.setValue(meta);
    }

    public static FBufferData create(TransferFactory factoryExt, String tag, int layer) {

        return new FBufferDataDef(factoryExt, tag, layer);
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
