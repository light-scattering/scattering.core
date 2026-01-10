package eu.scattering.core.impl.component.aggregate.validator.common.module;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.validator.common.module.FValidatorCommonFractalDimension;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.type.FractalDimension;

public class FValidatorCommonFractalDimensionDef implements FValidatorCommonFractalDimension {
    private final FractalDimension type;
    private final double expected;
    private final double error;
    private final FStat stat;

    private FValidatorCommonFractalDimensionDef(ScatFactory factory, FractalDimension type, double expected, double error) {

        this.type = type;
        this.expected = expected;
        this.error = error;
        this.stat = factory.getFStat();

        this.stat.setName("Fractal dimension");
    }

    public static FValidatorCommonFractalDimension create(ScatFactory factory, FractalDimension type, double expected, double error) {

        return new FValidatorCommonFractalDimensionDef(factory, type, expected, error);
    }

    @Override
    public Boolean apply(FAggregate fAggregate, Integer iteration) {

        if (iteration == 0) {
            this.stat.clear();
        }

        double df = fAggregate.getFractalDimension(this.type);

        this.stat.add(df);

        return Math.abs(df - this.expected) <= this.error;
    }

    @Override
    public FStat getRefFStat() {

        return this.stat;
    }
}
