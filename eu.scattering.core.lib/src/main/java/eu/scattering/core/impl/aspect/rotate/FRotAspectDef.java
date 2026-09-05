package eu.scattering.core.impl.aspect.rotate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.rotate.FRotAspect;
import eu.scattering.core.design.aspect.rotate.mutation.FRotMutate;
import eu.scattering.core.design.aspect.rotate.state.FRotStateFactory;
import eu.scattering.core.impl.aspect.rotate.mutation.FRotMutateDef;
import eu.scattering.core.impl.aspect.rotate.state.FRotStateFactoryDef;

public class FRotAspectDef implements FRotAspect {
    private final FRotMutate mutate;
    private final FRotStateFactory state;

    private FRotAspectDef(ScatterFactory factory) {

        this.state = FRotStateFactoryDef.create(factory);

        this.mutate = FRotMutateDef.create(this.state);
    }

    public static FRotAspect create(ScatterFactory factory) {

        return new FRotAspectDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FRotStateFactory state() {

        return this.state;
    }

    @Override
    public FRotMutate mutate() {

        return this.mutate;
    }
}
