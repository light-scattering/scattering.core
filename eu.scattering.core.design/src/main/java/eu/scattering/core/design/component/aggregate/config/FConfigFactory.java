package eu.scattering.core.design.component.aggregate.config;

import eu.scattering.core.design.component.aggregate.config.df.kinetic.cc.FConfigCCPL;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigBC;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigDC;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigMR;
import eu.scattering.core.design.component.aggregate.config.df.kinetic.pc.FConfigPCPL;

public interface FConfigFactory {

    FConfigBC getFConfigBC();
    FConfigBC getFConfigBC(FConfigBC.Preset preset);

    FConfigDC getFConfigDC();
    FConfigDC getFConfigDC(FConfigDC.Preset preset);

    FConfigMR getFConfigMR();
    FConfigMR getFConfigMR(FConfigMR.Preset preset);

    // -------------------------------------------------------------------------------------------------

    FConfigPCPL getFConfigPCPL();
    FConfigPCPL getFConfigPCPL(FConfigPCPL.Preset preset);

    FConfigCCPL getFConfigCCPL();
    FConfigCCPL getFConfigCCPL(FConfigCCPL.Preset preset);
}
