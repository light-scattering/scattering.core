package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.utility.type.preset.ExBasic;

public interface FAggregateAspectLoad {

    FAggregate fromJSON(String data);

    FAggregate fromBasic(String data, ExBasic preset);
}
