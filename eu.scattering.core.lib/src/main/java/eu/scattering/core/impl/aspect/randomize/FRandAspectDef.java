package eu.scattering.core.impl.aspect.randomize;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1DFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FRandDist2DFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3DFactory;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.randomize.mutation.FRandMutation;
import eu.scattering.core.impl.aspect.randomize.distribution.dist1D.FRandDist1DFactoryDef;
import eu.scattering.core.impl.aspect.randomize.distribution.dist2D.FRandDist2DFactoryDef;
import eu.scattering.core.impl.aspect.randomize.distribution.dist3D.FRandDist3DFactoryDef;
import eu.scattering.core.impl.aspect.randomize.mutation.FRandMutationDef;

public class FRandAspectDef implements FRandAspect {
    private final FRandEngine engine;
    private final FRandMutation mutate;

    private final FRandDist1DFactory dist1D;
    private final FRandDist2DFactory dist2D;
    private final FRandDist3DFactory dist3D;

    private FRandAspectDef(FRandEngine engine, ScatterFactory factory) {

        this.engine = engine;

        this.mutate = FRandMutationDef.create(engine, factory);

        this.dist1D = FRandDist1DFactoryDef.create(engine);
        this.dist2D = FRandDist2DFactoryDef.create(engine, factory);
        this.dist3D = FRandDist3DFactoryDef.create(engine, factory);
    }

    public static FRandAspect create(FRandEngine engine, ScatterFactory factory) {

        return new FRandAspectDef(engine, factory);
    }

    //--------------------------------------------------

    @Override
    public FRandEngine engine() {

        return this.engine;
    }

    @Override
    public FRandMutation mutate() {

        return this.mutate;
    }

    @Override
    public FRandDist1DFactory dist1D() {

        return this.dist1D;
    }

    @Override
    public FRandDist2DFactory dist2D() {

        return this.dist2D;
    }

    @Override
    public FRandDist3DFactory dist3D() {

        return this.dist3D;
    }
}
