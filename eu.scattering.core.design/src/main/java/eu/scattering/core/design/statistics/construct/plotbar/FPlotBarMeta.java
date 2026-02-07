package eu.scattering.core.design.statistics.construct.plotbar;

import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public interface FPlotBarMeta {

    String getName();
    FPlotBarMeta setName(String name);

    String getNameX();
    FPlotBarMeta setNameX(String nameX);

    String getNameY();
    FPlotBarMeta setNameY(String nameY);

    FPos2D getRangeX();
    FPlotBarMeta setRangeX(double min, double max);

    FPos2D getRangeY();
    FPlotBarMeta setRangeY(double min, double max);

    String getAnnotation();
    FPlotBarMeta setAnnotation(String annotation);
}
