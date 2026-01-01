package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryModule;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryModuleGeometry;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryModuleTemplate;

public class FAggregateFactoryModuleDef implements FAggregateFactoryModule {
    private final FAggregateFactoryModuleGeometry geometry;
    private final FAggregateFactoryModuleTemplate template;

    private FAggregateFactoryModuleDef(ScatFactory factory) {

        this.geometry = FAggregateFactoryModuleGeometryDef.create(factory);
        this.template = FAggregateFactoryModuleTemplateDef.create(factory);
    }

    public static FAggregateFactoryModule create(ScatFactory factory) {

        return new FAggregateFactoryModuleDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FAggregateFactoryModuleGeometry geometry() {

        return this.geometry;
    }

    @Override
    public FAggregateFactoryModuleTemplate template() {

        return this.template;
    }
}
