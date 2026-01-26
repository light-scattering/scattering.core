package eu.scattering.core.design.statistics.base;

import eu.scattering.core.design.transfer.primitive.FPos2D;

public interface FStatMeta {

    String getName();
    FStatMeta setName(String name);

    String getNameX();
    FStatMeta setNameX(String nameX);

    String getNameY();
    FStatMeta setNameY(String nameY);

    FPos2D getRangeX();
    FStatMeta setRangeX(double min, double max);

    FPos2D getRangeY();
    FStatMeta setRangeY(double min, double max);

    String getAnnotation();
    FStatMeta setAnnotation(String annotation);
}
