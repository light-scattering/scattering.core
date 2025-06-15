package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;

public interface Shape extends Geometry {

    String getTag();
    boolean setTag(String tag);

    int getIndex();
    boolean setIndex(int index);

    double getVolume();

    double getSurface();

    double getOuterRadius();

    double getInnerRadius();
}
