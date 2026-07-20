package eu.scattering.core.design.component.aggregate.config.df.kinetic.pc;

public interface FConfigPCPL {

    double getWindowRatio();
    FConfigPCPL setWindowRatio(double ratio);

    double getDropRatio();
    FConfigPCPL setDropRatio(double ratio);

    // -------------------------------------------------------------------------------------------------

    enum Preset {
        WINDOW, DROP
    }
}
