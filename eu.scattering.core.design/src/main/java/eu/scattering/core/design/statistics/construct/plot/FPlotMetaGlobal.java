package eu.scattering.core.design.statistics.construct.plot;

import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public interface FPlotMetaGlobal {

    int getFontSize();
    FPlotMetaGlobal setFontSize(int fontSize);

    String getName();
    FPlotMetaGlobal setName(String name);

    String getNameX();
    FPlotMetaGlobal setNameX(String nameX);

    String getNameY();
    FPlotMetaGlobal setNameY(String nameY);

    FPos2D getRangeX();
    FPlotMetaGlobal setRangeX(double min, double max);

    FPos2D getRangeY();
    FPlotMetaGlobal setRangeY(double min, double max);
}
