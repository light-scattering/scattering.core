package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateAspectLoad;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.impl.component.aggregate.load.ImBasicDef;

public class FAggregateAspectLoadDef implements FAggregateAspectLoad {
    private final ScatFactory factory;

    private FAggregateAspectLoadDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FAggregateAspectLoadDef create(ScatFactory factory) {

        return new FAggregateAspectLoadDef(factory);
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
