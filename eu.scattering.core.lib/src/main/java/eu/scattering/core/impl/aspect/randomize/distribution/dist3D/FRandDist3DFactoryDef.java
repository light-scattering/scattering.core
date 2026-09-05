package eu.scattering.core.impl.aspect.randomize.distribution.dist3D;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1D;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3DFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.custom.FRandDist3DCustom;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.fixed.FRandDist3DFixed;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.joint.FRandDist3DJoint;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.normal.FRandDist3DNormal;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.uniform.FRandDist3DUniform;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;

import java.util.function.BiConsumer;

public class FRandDist3DFactoryDef implements FRandDist3DFactory {
    private final ScatterFactory factory;
    private final FRandEngine generator;

    private FRandDist3DFactoryDef(FRandEngine generator, ScatterFactory factory) {

        this.factory = factory;
        this.generator = generator;
    }

    public static FRandDist3DFactory create(FRandEngine generator, ScatterFactory factory) {

        return new FRandDist3DFactoryDef(generator, factory);
    }

    //--------------------------------------------------

    @Override
    public FRandDist3DCustom custom(BiConsumer<FRandEngine, Double[]> consumer) {

        return FRandDist3DManualDef.create(this.factory, this.generator, consumer);
    }

    @Override
    public FRandDist3DJoint joint(FRandDist1D d0, FRandDist1D d1, FRandDist1D d2) {

        return FRandDist3DJointDef.create(this.factory, d0, d1, d2);
    }

    @Override
    public FRandDist3DFixed fixed(double d0, double d1, double d2) {

        return FRandDist3DFixedDef.create(this.factory, d0, d1, d2);
    }

    @Override
    public FRandDist3DFixed fixed(FPos3D val) {

        return FRandDist3DFixedDef.create(this.factory, val);
    }

    @Override
    public FRandDist3DUniform uniform(double d0min, double d0max, double d1min, double d1max, double d2min, double d2max) {

        return FRandDist3DUniformDef.create(this.factory, this.generator, d0min, d0max, d1min, d1max, d2min, d2max);
    }

    @Override
    public FRandDist3DUniform uniform(FPairPos3D range) {

        return FRandDist3DUniformDef.create(this.factory, this.generator, range);
    }

    @Override
    public FRandDist3DNormal normal(double avg, double std) {

        return FRandDist3DNormalDef.create(this.factory, this.generator, avg, std);
    }
}
