package eu.scattering.core.impl.aspect.rotate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.rotate.FRotAspect;
import eu.scattering.core.design.aspect.rotate.mutation.FRotMutation;
import eu.scattering.core.design.aspect.rotate.state.FRotStateFactoryContext;
import eu.scattering.core.impl.aspect.rotate.mutation.FRotMutationDef;
import eu.scattering.core.impl.aspect.rotate.state.FRotStateQtFactoryContextDef;

public class FRotAspectDef implements FRotAspect {
    private final FRotMutation mutate;

    private final FRotStateFactoryContext state;


    private FRotAspectDef(ScatterFactory factory) {

        this.state = FRotStateQtFactoryContextDef.create(factory);

        this.mutate = FRotMutationDef.create(this.state);
    }

    public static FRotAspect create(ScatterFactory factory) {

        return new FRotAspectDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FRotStateFactoryContext state() {

        return this.state;
    }

    @Override
    public FRotMutation mutate() {

        return this.mutate;
    }
}
