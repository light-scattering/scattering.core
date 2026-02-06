package eu.scattering.core.impl.statistics.base;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.base.FStatMeta;
import eu.scattering.core.design.transfer.primitive.FPos2D;

public class FStatMetaDef implements FStatMeta {
    private final ScatFactory factory;

    private String name = "";
    private String nameX = "";
    private String nameY = "";
    private String annotation = "";
    private FPos2D rangeX = null;
    private FPos2D rangeY = null;

    private FStatMetaDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FStatMeta create(ScatFactory factory) {

        return new FStatMetaDef(factory);
    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public FStatMeta setName(String name) {

        this.name = name;

        return this;
    }

    @Override
    public String getNameX() {

        return this.nameX;
    }

    @Override
    public FStatMeta setNameX(String nameX) {

        this.nameX = nameX;

        return this;
    }

    @Override
    public String getNameY() {

        return this.nameY;
    }

    @Override
    public FStatMeta setNameY(String nameY) {

        this.nameY = nameY;

        return this;
    }

    @Override
    public String getAnnotation() {

        return this.annotation;
    }

    @Override
    public FStatMeta setAnnotation(String annotation) {

        this.annotation = annotation;

        return this;
    }

    @Override
    public FPos2D getRangeX() {

        return this.rangeX;
    }

    @Override
    public FStatMeta setRangeX(double min, double max) {

        this.rangeX = factory.getFPos2D(min, max);

        return this;
    }

    @Override
    public FPos2D getRangeY() {

        return this.rangeY;
    }

    @Override
    public FStatMeta setRangeY(double min, double max) {

        this.rangeY = factory.getFPos2D(min, max);

        return this;
    }
}
