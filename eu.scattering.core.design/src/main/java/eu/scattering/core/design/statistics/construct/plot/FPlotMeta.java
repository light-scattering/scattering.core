package eu.scattering.core.design.statistics.construct.plot;

public interface FPlotMeta {

    boolean getMarkersShow();
    FPlotMeta setMarkersShow(boolean show);

    int getMarkersSize();
    FPlotMeta setMarkersSize(int size);

    String getMarkersColor();
    FPlotMeta setMarkersColor(String color);

    boolean getLinesShow();
    FPlotMeta setLinesShow(boolean show);

    int getLinesWidth();
    FPlotMeta setLinesWidth(int width);

    String getLinesColor();
    FPlotMeta setLinesColor(String color);
}
