package eu.scattering.core.impl.statistics.construct.plot;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public class FPlotMetaGlobalDef implements FPlotMetaGlobal {
    private final ScatFactory factory;

    private int fontSize = 18;

    private String name = "";
    private String nameX = "";
    private String nameY = "";
    private FPos2D rangeX = null;
    private FPos2D rangeY = null;

    private String annotation = "";

    private Position positionLegend = Position.RIGHT;
    private Position positionAnnotation = Position.LEFT;

    private double gridSize = 3;

    private FPlotMetaGlobalDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FPlotMetaGlobal create(ScatFactory factory) {

        return new FPlotMetaGlobalDef(factory);
    }

    @Override
    public int getFontSize() {

        return this.fontSize;
    }

    @Override
    public FPlotMetaGlobal setFontSize(int fontSize) {

        if (fontSize < 1) {
            throw new IllegalArgumentException("The font size must be greater than zero");
        }

        this.fontSize = fontSize;

        return this;
    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public FPlotMetaGlobal setName(String name) {

        this.name = name;

        return this;
    }

    @Override
    public String getAnnotation() {

        return this.annotation;
    }

    @Override
    public FPlotMetaGlobal setAnnotation(String annotation) {

        this.annotation = annotation;

        return this;
    }

    @Override
    public String getNameX() {

        return this.nameX;
    }

    @Override
    public FPlotMetaGlobal setNameX(String nameX) {

        this.nameX = nameX;

        return this;
    }

    @Override
    public String getNameY() {

        return this.nameY;
    }

    @Override
    public FPlotMetaGlobal setNameY(String nameY) {

        this.nameY = nameY;

        return this;
    }

    @Override
    public FPos2D getRangeX() {

        return this.rangeX;
    }

    @Override
    public FPlotMetaGlobal setRangeX(double min, double max) {

        this.rangeX = factory.getFPos2D(min, max);

        return this;
    }

    @Override
    public FPos2D getRangeY() {

        return this.rangeY;
    }

    @Override
    public FPlotMetaGlobal setRangeY(double min, double max) {

        this.rangeY = factory.getFPos2D(min, max);

        return this;
    }

    @Override
    public Position getPositionLegend() {

        return this.positionLegend;
    }

    @Override
    public FPlotMetaGlobal setPositionLegend(Position position) {

        this.positionLegend = position;

        return this;
    }

    @Override
    public Position getPositionAnnotation() {

        return this.positionAnnotation;
    }

    @Override
    public FPlotMetaGlobal setPositionAnnotation(Position position) {

        this.positionAnnotation = position;

        return this;
    }

    @Override
    public double getGridSize() {

        return this.gridSize;
    }

    @Override
    public FPlotMetaGlobal setGridSize(double size) {

        this.gridSize = size;

        return this;
    }
}
