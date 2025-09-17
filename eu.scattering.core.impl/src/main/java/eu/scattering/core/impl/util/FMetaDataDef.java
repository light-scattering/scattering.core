package eu.scattering.core.impl.util;

import eu.scattering.core.design.util.container.FMetaData;
import eu.scattering.core.transfer.container.box.FBoxString.FBoxString;

public class FMetaDataDef implements FMetaData {
    private final int layer;
    private final FBoxString tag;

    private FMetaDataDef(FBoxString tag, int layer) {

        this.layer = layer;
        this.tag = tag;
    }

    public static FMetaData crete(FBoxString tag, int layer) {

        return new FMetaDataDef(tag, layer);
    }

    @Override
    public String getTag() {

        return this.tag.getValue();
    }

    @Override
    public int getLayer() {

        return this.layer;
    }
}
