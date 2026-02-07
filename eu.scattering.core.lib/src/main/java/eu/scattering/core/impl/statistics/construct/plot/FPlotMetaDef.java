package eu.scattering.core.impl.statistics.construct.plot;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos2D;

public class FPlotMetaDef implements FPlotMeta {
    private final ScatFactory factory;

    private String name = "";
    private String nameX = "";
    private String nameY = "";
    private String annotation = "";
    private FPos2D rangeX = null;
    private FPos2D rangeY = null;

    private FPlotMetaDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FPlotMeta create(ScatFactory factory) {

        return new FPlotMetaDef(factory);
    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public FPlotMeta setName(String name) {

        this.name = name;

        return this;
    }

    @Override
    public String getNameX() {

        return this.nameX;
    }

    @Override
    public FPlotMeta setNameX(String nameX) {

        this.nameX = nameX;

        return this;
    }

    @Override
    public String getNameY() {

        return this.nameY;
    }

    @Override
    public FPlotMeta setNameY(String nameY) {

        this.nameY = nameY;

        return this;
    }

    @Override
    public String getAnnotation() {

        return this.annotation;
    }

    @Override
    public FPlotMeta setAnnotation(String annotation) {

        this.annotation = annotation;

        return this;
    }

    @Override
    public FPos2D getRangeX() {

        return this.rangeX;
    }

    @Override
    public FPlotMeta setRangeX(double min, double max) {

        this.rangeX = factory.getFPos2D(min, max);

        return this;
    }

    @Override
    public FPos2D getRangeY() {

        return this.rangeY;
    }

    @Override
    public FPlotMeta setRangeY(double min, double max) {

        this.rangeY = factory.getFPos2D(min, max);

        return this;
    }
}
