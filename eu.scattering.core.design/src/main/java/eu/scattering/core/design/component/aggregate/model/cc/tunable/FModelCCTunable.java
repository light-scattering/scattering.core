package eu.scattering.core.design.component.aggregate.model.cc.tunable;

import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;

public interface FModelCCTunable extends FModelCC {

    boolean getCorrection();
    FModelCCTunable setCorrection(boolean correction);

    boolean getEarlyStageCorrection();
    FModelCCTunable setEarlyStageCorrection(boolean correction);
}
