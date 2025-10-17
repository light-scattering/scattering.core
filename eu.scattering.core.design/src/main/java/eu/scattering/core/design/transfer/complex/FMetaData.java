package eu.scattering.core.design.transfer.complex;

import eu.scattering.core.design.transfer.Transfer;
import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import eu.scattering.core.design.transfer.box.FBoxString;
import org.json.JSONObject;

public class FMetaData implements Transfer {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "meta";
    private static final String JSON_LAYER = "layer";
    private static final String JSON_META = "tag";

    private final int layer;

    private final FBoxString meta = factoryExt.getFBoxString();

    private FMetaData(String meta, int layerIndex) {

        this.layer = layerIndex;
        this.meta.setValue(meta);
    }

    public static FMetaData crete(String tag, int layer) {

        return new FMetaData(tag, layer);
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
