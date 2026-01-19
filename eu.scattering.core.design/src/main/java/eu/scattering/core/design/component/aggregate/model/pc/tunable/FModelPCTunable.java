package eu.scattering.core.design.component.aggregate.model.pc.tunable;

import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;

public interface FModelPCTunable extends FModelPC {

    boolean getEarlyStageCorrection();
    void setEarlyStageCorrection(boolean correction);
}
