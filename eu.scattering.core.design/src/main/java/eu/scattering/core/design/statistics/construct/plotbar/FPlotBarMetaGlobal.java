package eu.scattering.core.design.statistics.construct.plotbar;

import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public interface FPlotBarMetaGlobal {

    int getFontSize();
    FPlotBarMetaGlobal setFontSize(int fontSize);

    String getName();
    FPlotBarMetaGlobal setName(String name);

    String getAnnotation();
    FPlotBarMetaGlobal setAnnotation(String annotation);

    String getNameX();
    FPlotBarMetaGlobal setNameX(String nameX);

    String getNameY();
    FPlotBarMetaGlobal setNameY(String nameY);

    FPos2D getRangeX();
    FPlotBarMetaGlobal setRangeX(double min, double max);

    FPos2D getRangeY();
    FPlotBarMetaGlobal setRangeY(double min, double max);

    Position getPositionLegend();
    FPlotBarMetaGlobal setPositionLegend(Position position);

    Position getPositionAnnotation();
    FPlotBarMetaGlobal setPositionAnnotation(Position position);

    String getCoreLineColor();
    FPlotBarMetaGlobal setCoreLineColor(String color);

    int getCoreLineWidth();
    FPlotBarMetaGlobal setCoreLineWidth(int width);

    String getRangeLineColor();
    FPlotBarMetaGlobal setRangeLineColor(String color);

    int getRangeLineWidth();
    FPlotBarMetaGlobal setRangeLineWidth(int width);

    String getErrorLineColor();
    FPlotBarMetaGlobal setErrorLineColor(String color);

    int getErrorLineWidth();
    FPlotBarMetaGlobal setErrorLineWidth(int width);

    boolean getRangeShow();
    FPlotBarMetaGlobal setRangeShow(boolean show);

    boolean getErrorShow();
    FPlotBarMetaGlobal setErrorShow(boolean show);

    enum Position { LEFT, RIGHT }
}
