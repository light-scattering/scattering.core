package eu.scattering.core.design.component.aggregate.config;

import eu.scattering.core.design.component.aggregate.config.df.bc.FConfigBC;

public interface FConfigFactory {

    FConfigBC getFConfigBC();
    FConfigBC getFConfigBC(FConfigBC.Preset preset);
}
