package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateLoader;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.impl.component.aggregate.load.ImBasicDef;

public class FAggregateLoaderDef implements FAggregateLoader {
    private final ScatterFactory factory;

    private FAggregateLoaderDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FAggregateLoaderDef create(ScatterFactory factory) {

        return new FAggregateLoaderDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FAggregate fromJSON(String data) {

        return this.factory.getFAggregate(data);
    }

    @Override
    public FAggregate fromBasic(String data, ExBasic preset) {

        return ImBasicDef.core(this.factory, data, preset);
    }
}
