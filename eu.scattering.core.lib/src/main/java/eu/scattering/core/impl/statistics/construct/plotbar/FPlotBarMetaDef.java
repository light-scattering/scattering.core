package eu.scattering.core.impl.statistics.construct.plotbar;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarMeta;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public class FPlotBarMetaDef implements FPlotBarMeta {
    private final ScatFactory factory;

    private String name = "";
    private String nameX = "";
    private String nameY = "";
    private String annotation = "";
    private FPos2D rangeX = null;
    private FPos2D rangeY = null;

    private FPlotBarMetaDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FPlotBarMeta create(ScatFactory factory) {

        return new FPlotBarMetaDef(factory);
    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public FPlotBarMeta setName(String name) {

        this.name = name;

        return this;
    }

    @Override
    public String getNameX() {

        return this.nameX;
    }

    @Override
    public FPlotBarMeta setNameX(String nameX) {

        this.nameX = nameX;

        return this;
    }

    @Override
    public String getNameY() {

        return this.nameY;
    }

    @Override
    public FPlotBarMeta setNameY(String nameY) {

        this.nameY = nameY;

        return this;
    }

    @Override
    public String getAnnotation() {

        return this.annotation;
    }

    @Override
    public FPlotBarMeta setAnnotation(String annotation) {

        this.annotation = annotation;

        return this;
    }

    @Override
    public FPos2D getRangeX() {

        return this.rangeX;
    }

    @Override
    public FPlotBarMeta setRangeX(double min, double max) {

        this.rangeX = factory.getFPos2D(min, max);

        return this;
    }

    @Override
    public FPos2D getRangeY() {

        return this.rangeY;
    }

    @Override
    public FPlotBarMeta setRangeY(double min, double max) {

        this.rangeY = factory.getFPos2D(min, max);

        return this;
    }
}
