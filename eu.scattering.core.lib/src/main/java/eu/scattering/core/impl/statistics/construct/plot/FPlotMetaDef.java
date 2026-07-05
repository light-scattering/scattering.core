package eu.scattering.core.impl.statistics.construct.plot;

import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;

public class FPlotMetaDef implements FPlotMeta {
    private boolean linesShow = true;
    private int linesWidth = 2;
    private String linesColor = "black";

    private boolean markersShow = true;
    private int markersSize = 4;
    private String markersColor = "black";

    private FPlotMetaDef() {}

    public static FPlotMeta create() {

        return new FPlotMetaDef();
    }

    @Override
    public boolean getMarkersShow() {

        return this.markersShow;
    }

    @Override
    public FPlotMeta setMarkersShow(boolean show) {

        this.markersShow = show;

        return this;
    }

    @Override
    public int getMarkersSize() {

        return this.markersSize;
    }

    @Override
    public FPlotMeta setMarkersSize(int size) {

        if (size < 1) {
            throw new IllegalArgumentException("The marker size must be greater than zero");
        }

        this.markersSize = size;

        return this;
    }

    @Override
    public String getMarkersColor() {

        return this.markersColor;
    }

    @Override
    public FPlotMeta setMarkersColor(String color) {

        this.markersColor = color;

        return this;
    }

    @Override
    public boolean getLinesShow() {

        return this.linesShow;
    }

    @Override
    public FPlotMeta setLinesShow(boolean show) {

        this.linesShow = show;

        return this;
    }

    @Override
    public int getLinesWidth() {

        return this.linesWidth;
    }

    @Override
    public FPlotMeta setLinesWidth(int width) {

        if (width < 1) {
            throw new IllegalArgumentException("The line width must be greater than zero");
        }

        this.linesWidth = width;

        return this;
    }

    @Override
    public String getLinesColor() {

        return this.linesColor;
    }

    @Override
    public FPlotMeta setLinesColor(String color) {

        this.linesColor = color;

        return this;
    }
}
