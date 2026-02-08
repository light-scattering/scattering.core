package eu.scattering.core.impl.component;

import eu.scattering.core.design.component.ComponentAspectSave;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import eu.scattering.core.impl.component.aggregate.save.ExBasicDef;
import eu.scattering.core.impl.component.aggregate.save.ExFlageDef;
import eu.scattering.core.impl.component.aggregate.save.ExNetGenDef;
import eu.scattering.core.impl.component.aggregate.save.ExPovRayDef;

public class ComponentAspectSaveDef implements ComponentAspectSave {

    private ComponentAspectSaveDef() {
    }

    public static ComponentAspectSaveDef create() {

       return new ComponentAspectSaveDef();
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
