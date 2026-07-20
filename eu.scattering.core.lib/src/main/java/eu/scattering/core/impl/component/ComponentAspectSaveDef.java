package eu.scattering.core.impl.component;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.ComponentAspectSave;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import eu.scattering.core.impl.component.aggregate.save.ExBasicDef;
import eu.scattering.core.impl.component.aggregate.save.ExFlageDef;
import eu.scattering.core.impl.component.aggregate.save.ExNetGenDef;
import eu.scattering.core.impl.component.aggregate.save.ExPovRayDef;

public class ComponentAspectSaveDef implements ComponentAspectSave {

    private ComponentAspectSaveDef(ScatterFactory factory) {}

    public static ComponentAspectSave create(ScatterFactory factory) {

       return new ComponentAspectSaveDef(factory);
    }

    //--------------------------------------------------

    @Override
    public String toCLI(FPoint fPoint) {

        return "[" + fPoint.getX() + "," + fPoint.getY() + "," + fPoint.getZ() + "]";
    }

    @Override
    public String toCLI(FVector fVector) {

        return "[" + toCLI(fVector.getRefBase()) + "," + toCLI(fVector.getRefHead()) + "]";
    }

    //--------------------------------------------------

    @Override
    public void toJSON(FAggregate aggregate, StringBuilder builder) {

        builder.append(aggregate.toJSON().toString());
    }

    @Override
    public void toBasic(FAggregate aggregate, ExBasic preset, StringBuilder builder) {

        ExBasicDef.core(aggregate, preset, builder);
    }

    @Override
    public void toFLAGE(FAggregate aggregate, StringBuilder builder) {

        ExFlageDef.core(aggregate, builder);
    }

    @Override
    public void toNGSolve(FAggregate aggregate, StringBuilder builder) {

        ExNetGenDef.core(aggregate, builder);
    }

    @Override
    public void toPovRay(FAggregate aggregate, ExPovRay preset, StringBuilder builder) {

        ExPovRayDef.core(aggregate, preset, builder);
    }
}
