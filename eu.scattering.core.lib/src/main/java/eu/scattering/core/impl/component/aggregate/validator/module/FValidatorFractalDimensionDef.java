package eu.scattering.core.impl.component.aggregate.validator.module;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorFractalDimension;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.utility.type.FractalDimension;

public class FValidatorFractalDimensionDef implements FValidatorFractalDimension {
    private final FractalDimension type;
    private final double expected;
    private final double error;
    private final FStat stat;

    private FValidatorFractalDimensionDef(ScatFactory factory, FractalDimension type, double expected, double error) {

        this.type = type;
        this.expected = expected;
        this.error = error;
        this.stat = factory.getFStat();

        this.stat.setName("Fractal dimension");
    }

    public static FValidatorFractalDimension create(ScatFactory factory, FractalDimension type, double expected, double error) {

        return new FValidatorFractalDimensionDef(factory, type, expected, error);
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
