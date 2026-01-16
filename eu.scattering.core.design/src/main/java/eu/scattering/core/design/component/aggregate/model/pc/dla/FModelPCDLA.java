package eu.scattering.core.design.component.aggregate.model.pc.dla;

import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.lambda.TriConsumer;

public interface FModelPCDLA extends FModelPC {

    boolean getInternalSpawn();
    void setInternalSpawn(boolean internal);

    double getStepFactor();
    void setStepFactor(double factor);

    double getSpawnFactor();
    void setSpawnFactor(double factor);

    double getExileFactor();
    void setExileFactor(double factor);

    // -------------------------------------------------------------------------------------------------

    TriConsumer<Shape, FRandAspect, FPoint> getMovement();
    void setMovement(TriConsumer<Shape, FRandAspect, FPoint> movement);
}
