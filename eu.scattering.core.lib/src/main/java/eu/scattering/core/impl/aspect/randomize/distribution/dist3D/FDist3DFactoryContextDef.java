package eu.scattering.core.impl.aspect.randomize.distribution.dist3D;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FDist1D;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FDist3DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.custom.FDist3DCustom;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.fixed.FDist3DFixed;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.joint.FDist3DJoint;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.normal.FDist3DNormal;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.uniform.FDist3DUniform;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;

import java.util.function.BiConsumer;

public class FDist3DFactoryContextDef implements FDist3DFactoryContext {
    private final ScatterFactory factory;
    private final FRandGenerator generator;

    private FDist3DFactoryContextDef(FRandGenerator generator, ScatterFactory factory) {

        this.factory = factory;
        this.generator = generator;
    }

    public static FDist3DFactoryContext create(FRandGenerator generator, ScatterFactory factory) {

        return new FDist3DFactoryContextDef(generator, factory);
    }

    //--------------------------------------------------

    @Override
    public FDist3DCustom custom(BiConsumer<FRandGenerator, Double[]> consumer) {

        return FDist3DManualDef.create(this.factory, this.generator, consumer);
    }

    @Override
    public FDist3DJoint joint(FDist1D d0, FDist1D d1, FDist1D d2) {

        return FDist3DJointDef.create(this.factory, d0, d1, d2);
    }

    @Override
    public FDist3DFixed fixed(double d0, double d1, double d2) {

        return FDist3DFixedDef.create(this.factory, d0, d1, d2);
    }

    @Override
    public FDist3DFixed fixed(FPos3D val) {

        return FDist3DFixedDef.create(this.factory, val);
    }

    @Override
    public FDist3DUniform uniform(double d0min, double d0max, double d1min, double d1max, double d2min, double d2max) {

        return FDist3DUniformDef.create(this.factory, this.generator, d0min, d0max, d1min, d1max, d2min, d2max);
    }

    @Override
    public FDist3DUniform uniform(FPairPos3D range) {

        return FDist3DUniformDef.create(this.factory, this.generator, range);
    }

    @Override
    public FDist3DNormal normal(double avg, double std) {

        return FDist3DNormalDef.create(this.factory, this.generator, avg, std);
    }
}
