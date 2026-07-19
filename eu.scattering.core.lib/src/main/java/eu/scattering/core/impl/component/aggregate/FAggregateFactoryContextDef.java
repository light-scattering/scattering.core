package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContext;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContextGeometry;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContextBase;

public class FAggregateFactoryContextDef implements FAggregateFactoryContext {
    private final ScatterFactory factory;

    private FAggregateFactoryContextDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FAggregateFactoryContext create(ScatterFactory factory) {

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
