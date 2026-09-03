package eu.scattering.core.impl.aspect.randomize.distribution;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.distribution.FDistFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FDist1DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FDist2DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FDist3DFactoryContext;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.aspect.randomize.distribution.dist1D.*;
import eu.scattering.core.impl.aspect.randomize.distribution.dist2D.*;
import eu.scattering.core.impl.aspect.randomize.distribution.dist3D.*;

public class FDistFactoryContextDef implements FDistFactoryContext {
    private final FDist1DFactoryContext d1;
    private final FDist2DFactoryContext d2;
    private final FDist3DFactoryContext d3;

    private FDistFactoryContextDef(FRandGenerator generator, ScatterFactory factory) {

        this.d1 = FDist1DFactoryContextDef.create(generator);
        this.d2 = FDist2DFactoryContextDef.create(generator, factory);
        this.d3 = FDist3DFactoryContextDef.create(generator, factory);
    }

    public static FDistFactoryContext create(FRandGenerator generator, ScatterFactory factory) {

        return new FDistFactoryContextDef(generator, factory);
    }

    //--------------------------------------------------

    @Override
    public FDist1DFactoryContext d1() {

        return this.d1;
    }

    @Override
    public FDist2DFactoryContext d2() {

        return this.d2;
    }

    @Override
    public FDist3DFactoryContext d3() {

        return this.d3;
    }
}
