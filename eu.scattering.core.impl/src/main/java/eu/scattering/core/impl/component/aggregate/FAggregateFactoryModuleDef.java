package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryModule;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryModuleGeometry;
import eu.scattering.core.design.component.aggregate.FAggregateFactoryModuleTemplate;

public class FAggregateFactoryModuleDef implements FAggregateFactoryModule {
    private static FAggregateFactoryModule self;
    private final ScatFactory factory;

    private FAggregateFactoryModuleDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FAggregateFactoryModule get(ScatFactory factory) {

        if (FAggregateFactoryModuleDef.self == null) {
            FAggregateFactoryModuleDef.self = new FAggregateFactoryModuleDef(factory);
        }

        return FAggregateFactoryModuleDef.self;
    }

    //--------------------------------------------------

    @Override
    public FAggregateFactoryModuleGeometry geometry() {

        return FAggregateFactoryModuleGeometryDef.get(this.factory);
    }

    @Override
    public FAggregateFactoryModuleTemplate template() {

        return FAggregateFactoryModuleTemplateDef.get(this.factory);
    }
}
