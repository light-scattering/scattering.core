package eu.scattering.core.design.statistics.construct.plot;

import eu.scattering.core.design.transfer.primitive.FPos2D;

public interface FPlotMeta {

    String getName();
    FPlotMeta setName(String name);

    String getNameX();
    FPlotMeta setNameX(String nameX);

    String getNameY();
    FPlotMeta setNameY(String nameY);

    FPos2D getRangeX();
    FPlotMeta setRangeX(double min, double max);

    FPos2D getRangeY();
    FPlotMeta setRangeY(double min, double max);

    String getAnnotation();
    FPlotMeta setAnnotation(String annotation);
}
