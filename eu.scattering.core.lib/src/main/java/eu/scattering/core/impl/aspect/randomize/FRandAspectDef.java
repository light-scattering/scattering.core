package eu.scattering.core.impl.aspect.randomize;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FRandDist2DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3DFactoryContext;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.randomize.mutation.FRandMutation;
import eu.scattering.core.impl.aspect.randomize.distribution.dist1D.FRandDist1DFactoryContextDef;
import eu.scattering.core.impl.aspect.randomize.distribution.dist2D.FRandDist2DFactoryContextDef;
import eu.scattering.core.impl.aspect.randomize.distribution.dist3D.FRandDist3DFactoryContextDef;
import eu.scattering.core.impl.aspect.randomize.mutation.FRandMutationDef;

public class FRandAspectDef implements FRandAspect {
    private final FRandEngine engine;
    private final FRandMutation mutate;

    private final FRandDist1DFactoryContext dist1D;
    private final FRandDist2DFactoryContext dist2D;
    private final FRandDist3DFactoryContext dist3D;

    private FRandAspectDef(FRandEngine engine, ScatterFactory factory) {

        this.engine = engine;

        this.mutate = FRandMutationDef.create(engine, factory);

        this.dist1D = FRandDist1DFactoryContextDef.create(engine);
        this.dist2D = FRandDist2DFactoryContextDef.create(engine, factory);
        this.dist3D = FRandDist3DFactoryContextDef.create(engine, factory);
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
    public FRandDist1DFactoryContext dist1D() {

        return this.dist1D;
    }

    @Override
    public FRandDist2DFactoryContext dist2D() {

        return this.dist2D;
    }

    @Override
    public FRandDist3DFactoryContext dist3D() {

        return this.dist3D;
    }
}
