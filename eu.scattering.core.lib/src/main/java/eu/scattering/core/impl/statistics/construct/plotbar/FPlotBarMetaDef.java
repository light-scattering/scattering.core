package eu.scattering.core.impl.statistics.construct.plotbar;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarMetaGlobal;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public class FPlotBarMetaDef implements FPlotBarMetaGlobal {
    private final ScatFactory factory;

    private int fontSize = 18;

    private String name = "";
    private String nameX = "";
    private String nameY = "";
    private FPos2D rangeX = null;
    private FPos2D rangeY = null;

    private String annotation = "";

    private int coreLineWidth = 2;
    private String coreLineColor = "darkgray";

    private int rangeLineWidth = 2;
    private String rangeLineColor = "darkgray";

    private int errorLineWidth = 2;
    private String errorLineColor = "darkgray";

    private Position positionLegend = Position.RIGHT;
    private Position positionAnnotation = Position.LEFT;

    private boolean rangeShow = true;
    private boolean errorShow = true;

    private FPlotBarMetaDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FPlotBarMetaGlobal create(ScatFactory factory) {

        return new FPlotBarMetaDef(factory);
    }

    @Override
    public int getFontSize() {

        return this.fontSize;
    }

    @Override
    public FPlotBarMetaGlobal setFontSize(int fontSize) {

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
    public FPlotBarMetaGlobal setName(String name) {

        this.name = name;

        return this;
    }

    @Override
    public String getNameX() {

        return this.nameX;
    }

    @Override
    public FPlotBarMetaGlobal setNameX(String nameX) {

        this.nameX = nameX;

        return this;
    }

    @Override
    public String getNameY() {

        return this.nameY;
    }

    @Override
    public FPlotBarMetaGlobal setNameY(String nameY) {

        this.nameY = nameY;

        return this;
    }

    @Override
    public String getAnnotation() {

        return this.annotation;
    }

    @Override
    public FPlotBarMetaGlobal setAnnotation(String annotation) {

        this.annotation = annotation;

        return this;
    }

    @Override
    public FPos2D getRangeX() {

        return this.rangeX;
    }

    @Override
    public FPlotBarMetaGlobal setRangeX(double min, double max) {

        this.rangeX = factory.getFPos2D(min, max);

        return this;
    }

    @Override
    public FPos2D getRangeY() {

        return this.rangeY;
    }

    @Override
    public FPlotBarMetaGlobal setRangeY(double min, double max) {

        this.rangeY = factory.getFPos2D(min, max);

        return this;
    }

    @Override
    public Position getPositionLegend() {

        return this.positionLegend;
    }

    @Override
    public FPlotBarMetaGlobal setPositionLegend(Position position) {

        this.positionLegend = position;

        return this;
    }

    @Override
    public Position getPositionAnnotation() {

        return this.positionAnnotation;
    }

    @Override
    public FPlotBarMetaGlobal setPositionAnnotation(Position position) {

        this.positionAnnotation = position;

        return this;
    }

    @Override
    public String getCoreLineColor() {

        return this.coreLineColor;
    }

    @Override
    public FPlotBarMetaGlobal setCoreLineColor(String color) {

        this.coreLineColor = color;

        return this;
    }

    @Override
    public int getCoreLineWidth() {

        return this.coreLineWidth;
    }

    @Override
    public FPlotBarMetaGlobal setCoreLineWidth(int width) {

        if (width < 1) {
            throw new IllegalArgumentException("The line width must be greater than zero");
        }

        this.coreLineWidth = width;

        return this;
    }

    @Override
    public String getRangeLineColor() {

        return this.rangeLineColor;
    }

    @Override
    public FPlotBarMetaGlobal setRangeLineColor(String color) {

        this.rangeLineColor = color;

        return this;
    }

    @Override
    public int getRangeLineWidth() {

        return this.rangeLineWidth;
    }

    @Override
    public FPlotBarMetaGlobal setRangeLineWidth(int width) {

        if (width < 1) {
            throw new IllegalArgumentException("The line width must be greater than zero");
        }

        this.rangeLineWidth = width;

        return this;
    }

    @Override
    public String getErrorLineColor() {

        return this.errorLineColor;
    }

    @Override
    public FPlotBarMetaGlobal setErrorLineColor(String color) {

        this.errorLineColor = color;

        return this;
    }

    @Override
    public int getErrorLineWidth() {

        return this.errorLineWidth;
    }

    @Override
    public FPlotBarMetaGlobal setErrorLineWidth(int width) {

        if (width < 1) {
            throw new IllegalArgumentException("The line width must be greater than zero");
        }

        this.errorLineWidth = width;

        return this;
    }

    @Override
    public boolean getErrorShow() {

        return this.errorShow;
    }

    @Override
    public FPlotBarMetaGlobal setErrorShow(boolean show) {

        this.errorShow = show;

        return this;
    }

    @Override
    public boolean getRangeShow() {

        return this.rangeShow;
    }

    @Override
    public FPlotBarMetaGlobal setRangeShow(boolean show) {

        this.rangeShow = show;

        return this;
    }
}
