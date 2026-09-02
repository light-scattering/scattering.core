package eu.scattering.core.impl.aspect.save;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.save.FSaveAspect;
import eu.scattering.core.design.component.ComponentAspectSave;
import eu.scattering.core.design.statistics.StatisticsAspectSave;
import eu.scattering.core.design.storage.StorageAspectSave;
import eu.scattering.core.impl.component.ComponentAspectSaveDef;
import eu.scattering.core.impl.statistics.StatisticsAspectSaveDef;
import eu.scattering.core.impl.storage.StorageAspectSaveDef;

public class FSaveAspectDef implements FSaveAspect {
    private final ScatterFactory factory;

    private FSaveAspectDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FSaveAspect create(ScatterFactory factory) {

        return new FSaveAspectDef(factory);
    }

    //--------------------------------------------------

    @Override
    public StatisticsAspectSave statistics() {

        return StatisticsAspectSaveDef.create(this.factory);
    }

    @Override
    public ComponentAspectSave components() {

        return ComponentAspectSaveDef.create(this.factory);
    }

    @Override
    public StorageAspectSave storage() {

        return StorageAspectSaveDef.create();
    }
}
