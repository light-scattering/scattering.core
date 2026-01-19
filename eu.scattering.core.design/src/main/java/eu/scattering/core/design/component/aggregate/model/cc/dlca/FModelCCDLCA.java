package eu.scattering.core.design.component.aggregate.model.cc.dlca;

import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.lambda.TriConsumer;

public interface FModelCCDLCA extends FModelCC {

    boolean getInternalSpawn();
    void setInternalSpawn(boolean internal);

    // -------------------------------------------------------------------------------------------------

    double getStepFactor();
    void setStepFactor(double factor);

    double getSpawnFactor();
    void setSpawnFactor(double factor);

    double getExileFactor();
    void setExileFactor(double factor);

    // -------------------------------------------------------------------------------------------------

    TriConsumer<FAggregate, FRandAspect, FPoint> getMovement();
    void setMovement(TriConsumer<FAggregate, FRandAspect, FPoint> movement);
}
