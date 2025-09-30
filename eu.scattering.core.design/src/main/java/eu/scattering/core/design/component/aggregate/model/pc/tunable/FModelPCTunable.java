package eu.scattering.core.design.component.aggregate.model.pc.tunable;

import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;

public interface FModelPCTunable extends FModelPC {

    double getDf();
    void setDf(double df);

    double getKf();
    void setKf(double kf);

    boolean getEarlyStateCorrection();
    void setEarlyStageCorrection(boolean correction);
}
