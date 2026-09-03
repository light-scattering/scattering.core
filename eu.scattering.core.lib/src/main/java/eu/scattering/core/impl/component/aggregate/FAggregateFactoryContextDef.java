package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContext;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContextGeometries;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryContextTemplates;

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
    public FAggregateFactoryContextTemplates templates() {

        return FAggregateFactoryContextTemplatesDef.create(this.factory);
    }

    @Override
    public FAggregateFactoryContextGeometries geometries() {

        return FAggregateFactoryContextGeometriesDef.create(this.factory);
    }
}
