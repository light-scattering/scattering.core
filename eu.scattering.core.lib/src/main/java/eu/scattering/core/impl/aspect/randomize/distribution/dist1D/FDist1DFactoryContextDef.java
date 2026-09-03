package eu.scattering.core.impl.aspect.randomize.distribution.dist1D;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FDist1DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.custom.FDist1DCustom;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.fixed.FDist1DFixed;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.normal.FDist1DNormal;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.uniform.FDist1DUniform;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;

import java.util.function.BiConsumer;

public class FDist1DFactoryContextDef implements FDist1DFactoryContext {
    private final FRandGenerator generator;

    private FDist1DFactoryContextDef(FRandGenerator generator) {

        this.generator = generator;
    }

    public static FDist1DFactoryContext create(FRandGenerator generator) {

        return new FDist1DFactoryContextDef(generator);
    }

    //--------------------------------------------------

    @Override
    public FDist1DCustom custom(BiConsumer<FRandGenerator, Double[]> consumer) {

        return FDist1DManualDef.get(this.generator, consumer);
    }

    @Override
    public FDist1DFixed fixed(double d0) {

        return FDist1DFixedDef.get(d0);
    }

    @Override
    public FDist1DNormal normal(double mean, double std) {

        return FDist1DNormalDef.get(this.generator, mean, std);
    }

    @Override
    public FDist1DUniform uniform(double d0min, double d0max) {

        return FDist1DUniformDef.get(this.generator, d0min, d0max);
    }
}
