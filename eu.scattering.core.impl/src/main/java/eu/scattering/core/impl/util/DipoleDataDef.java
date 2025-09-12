package eu.scattering.core.impl.util;

import eu.scattering.core.design.util.container.DipoleData;

public class DipoleDataDef implements DipoleData {
    private final int layer;
    private final String tag;

    private DipoleDataDef(int layer, String tag) {

        this.layer = layer;
        this.tag = tag;
    }

    public static DipoleData crete(int layer, String tag) {

        return new DipoleDataDef(layer, tag);
    }

    @Override
    public int getLayer() {

        return this.layer;
    }

    @Override
    public String getTag() {

        return this.tag;
    }
}
