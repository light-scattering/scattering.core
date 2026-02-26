package eu.scattering.core.design.component.aggregate.model.cc.tunable;

import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;

public interface FModelCCTunable extends FModelCC {

    boolean getCorrection();
    void setCorrection(boolean correction);

    boolean getEarlyStageCorrection();
    void setEarlyStageCorrection(boolean correction);
}
