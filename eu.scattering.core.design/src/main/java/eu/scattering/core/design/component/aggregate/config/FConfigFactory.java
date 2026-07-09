package eu.scattering.core.design.component.aggregate.config;

import eu.scattering.core.design.component.aggregate.config.df.FConfigBC;
import eu.scattering.core.design.component.aggregate.config.df.FConfigDC;
import eu.scattering.core.design.component.aggregate.config.df.FConfigMR;

public interface FConfigFactory {

    FConfigBC getFConfigBC();
    FConfigBC getFConfigBC(FConfigBC.Preset preset);

    FConfigDC getFConfigDC();
    FConfigDC getFConfigDC(FConfigDC.Preset preset);

    FConfigMR getFConfigMR();
    FConfigMR getFConfigMR(FConfigMR.Preset preset);
}
