package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContext;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContextGeometry;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContextBase;

public class FAggregateFactoryContextDef implements FAggregateFactoryContext {
    private final ScatFactory factory;

    private FAggregateFactoryContextDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FAggregateFactoryContext create(ScatFactory factory) {

        return new FAggregateFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FAggregateFactoryContextBase base() {

        return FAggregateFactoryContextBaseDef.create(this.factory);
    }

    @Override
    public FAggregateFactoryContextGeometry geometry() {

        return FAggregateFactoryContextGeometryDef.create(this.factory);
    }
}
