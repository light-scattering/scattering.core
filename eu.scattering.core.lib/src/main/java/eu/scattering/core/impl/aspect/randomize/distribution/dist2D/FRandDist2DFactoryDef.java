package eu.scattering.core.impl.aspect.randomize.distribution.dist2D;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1D;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FRandDist2DFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.custom.FRandDist2DCustom;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.fixed.FRandDist2DFixed;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.joint.FRandDist2DJoint;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.normal.FRandDist2DNormal;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.uniform.FRandDist2DUniform;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;

import java.util.function.BiConsumer;

public class FRandDist2DFactoryDef implements FRandDist2DFactory {
    private final ScatterFactory factory;
    private final FRandEngine generator;

    private FRandDist2DFactoryDef(FRandEngine generator, ScatterFactory factory) {

        this.factory = factory;
        this.generator = generator;
    }

    public static FRandDist2DFactory create(FRandEngine generator, ScatterFactory factory) {

        return new FRandDist2DFactoryDef(generator, factory);
    }

    //--------------------------------------------------

    @Override
    public FRandDist2DCustom custom(BiConsumer<FRandEngine, Double[]> consumer) {

        return FRandDist2DManualDef.create(this.factory, this.generator, consumer);
    }

    @Override
    public FRandDist2DJoint joint(FRandDist1D d0, FRandDist1D d1) {

        return FRandDist2DJointDef.create(this.factory, d0, d1);
    }

    @Override
    public FRandDist2DFixed fixed(double d0, double d1) {

        return FRandDist2DFixedDef.create(this.factory, d0, d1);
    }

    @Override
    public FRandDist2DFixed fixed(FPos2D val) {

        return FRandDist2DFixedDef.create(this.factory, val);
    }

    @Override
    public FRandDist2DUniform uniform(double d0min, double d0max, double d1min, double d1max) {

        return FRandDist2DUniformDef.create(this.factory, this.generator, d0min, d0max, d1min, d1max);
    }

    @Override
    public FRandDist2DUniform uniform(FPairPos2D range) {

        return FRandDist2DUniformDef.create(this.factory, this.generator, range);
    }

    @Override
    public FRandDist2DNormal normal(double avg, double std) {

        return FRandDist2DNormalDef.create(this.factory, this.generator, avg, std);
    }
}
