package eu.scattering.core.design.component.aggregate.model.pc.tunable;

import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;

public interface FModelPCTunable extends FModelPC {

    void setEarlyStageCorrection(boolean correction);

    void setDf(double df);
    void setKf(double kf);
}
