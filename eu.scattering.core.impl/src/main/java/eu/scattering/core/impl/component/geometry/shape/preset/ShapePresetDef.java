package eu.scattering.core.impl.component.geometry.shape.preset;

import eu.scattering.core.design.component.geometry.shape.Shape;

public abstract class ShapePresetDef implements Shape {

    private int index = -1;
    private String tag = "";

    @Override
    public boolean setIndex(int index) {

        if (index == this.index) {
            return false;
        }

        this.index = index;

        return true;
    }

    @Override
    public int getIndex() {

        return this.index;
    }

    @Override
    public boolean setTag(String tag) {

        if (tag.equals(this.tag)) {
            return false;
        }

        this.tag = tag;

        return true;
    }

    @Override
    public String getTag() {

        return this.tag;
    }
}
