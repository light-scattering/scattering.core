package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateAspectExport;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import eu.scattering.core.impl.component.aggregate.export.ExBasicDef;
import eu.scattering.core.impl.component.aggregate.export.ExFlageDef;
import eu.scattering.core.impl.component.aggregate.export.ExNetGenDef;
import eu.scattering.core.impl.component.aggregate.export.ExPovRayDef;

public class FAggregateAspectExportDef implements FAggregateAspectExport {

    private FAggregateAspectExportDef() {
    }

    public static FAggregateAspectExportDef create() {

       return new FAggregateAspectExportDef();
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
