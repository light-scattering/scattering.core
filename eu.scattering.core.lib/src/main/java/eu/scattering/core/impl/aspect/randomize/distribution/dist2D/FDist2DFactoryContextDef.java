package eu.scattering.core.impl.aspect.randomize.distribution.dist2D;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FDist1D;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FDist2DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.custom.FDist2DCustom;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.fixed.FDist2DFixed;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.joint.FDist2DJoint;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.normal.FDist2DNormal;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.uniform.FDist2DUniform;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;

import java.util.function.BiConsumer;

public class FDist2DFactoryContextDef implements FDist2DFactoryContext {
    private final ScatterFactory factory;
    private final FRandGenerator generator;

    private FDist2DFactoryContextDef(FRandGenerator generator, ScatterFactory factory) {

        this.factory = factory;
        this.generator = generator;
    }

    public static FDist2DFactoryContext create(FRandGenerator generator, ScatterFactory factory) {

        return new FDist2DFactoryContextDef(generator, factory);
    }

    //--------------------------------------------------

    @Override
    public FDist2DCustom custom(BiConsumer<FRandGenerator, Double[]> consumer) {

        return FDist2DManualDef.create(this.factory, this.generator, consumer);
    }

    @Override
    public FDist2DJoint joint(FDist1D d0, FDist1D d1) {

        return FDist2DJointDef.create(this.factory, d0, d1);
    }

    @Override
    public FDist2DFixed fixed(double d0, double d1) {

        return FDist2DFixedDef.create(this.factory, d0, d1);
    }

    @Override
    public FDist2DFixed fixed(FPos2D val) {

        return FDist2DFixedDef.create(this.factory, val);
    }

    @Override
    public FDist2DUniform uniform(double d0min, double d0max, double d1min, double d1max) {

        return FDist2DUniformDef.create(this.factory, this.generator, d0min, d0max, d1min, d1max);
    }

    @Override
    public FDist2DUniform uniform(FPairPos2D range) {

        return FDist2DUniformDef.create(this.factory, this.generator, range);
    }

    @Override
    public FDist2DNormal normal(double avg, double std) {

        return FDist2DNormalDef.create(this.factory, this.generator, avg, std);
    }
}
