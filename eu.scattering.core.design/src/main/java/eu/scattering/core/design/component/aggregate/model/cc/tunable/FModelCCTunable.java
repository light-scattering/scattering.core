package eu.scattering.core.design.component.aggregate.model.cc.tunable;

import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;

public interface FModelCCTunable extends FModelCC {

    void setCorrection(boolean correction);
    void setEarlyStageCorrection(boolean correction);
}
