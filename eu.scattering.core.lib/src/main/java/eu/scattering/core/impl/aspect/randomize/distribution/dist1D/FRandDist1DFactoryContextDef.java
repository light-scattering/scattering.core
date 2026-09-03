package eu.scattering.core.impl.aspect.randomize.distribution.dist1D;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.custom.FRandDist1DCustom;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.fixed.FRandDist1DFixed;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.normal.FRandDist1DNormal;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.uniform.FRandDist1DUniform;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;

import java.util.function.BiConsumer;

public class FRandDist1DFactoryContextDef implements FRandDist1DFactoryContext {
    private final FRandEngine generator;

    private FRandDist1DFactoryContextDef(FRandEngine generator) {

        this.generator = generator;
    }

    public static FRandDist1DFactoryContext create(FRandEngine generator) {

        return new FRandDist1DFactoryContextDef(generator);
    }

    //--------------------------------------------------

    @Override
    public FRandDist1DCustom custom(BiConsumer<FRandEngine, Double[]> consumer) {

        return FRandDist1DManualDef.get(this.generator, consumer);
    }

    @Override
    public FRandDist1DFixed fixed(double d0) {

        return FRandDist1DFixedDef.get(d0);
    }

    @Override
    public FRandDist1DNormal normal(double mean, double std) {

        return FRandDist1DNormalDef.get(this.generator, mean, std);
    }

    @Override
    public FRandDist1DUniform uniform(double d0min, double d0max) {

        return FRandDist1DUniformDef.get(this.generator, d0min, d0max);
    }
}
