package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.FAggregateAspectExport;
import eu.scattering.core.design.utility.type.PovRay;
import eu.scattering.core.impl.component.aggregate.export.FlageDef;
import eu.scattering.core.impl.component.aggregate.export.NetGenDef;
import eu.scattering.core.impl.component.aggregate.export.PovRayDef;

public class FAggregateAspectExportDef implements FAggregateAspectExport {

    private FAggregateAspectExportDef() {
    }

    public static FAggregateAspectExportDef create() {

       return new FAggregateAspectExportDef();
    }

    //--------------------------------------------------

    @Override
    public void toFLAGE(FAggregate aggregate, StringBuilder builder) {

        FlageDef.core(aggregate, builder);
    }

    @Override
    public void toNGSolve(FAggregate aggregate, StringBuilder builder) {

        NetGenDef.core(aggregate, builder);
    }

    @Override
    public void toPovRay(FAggregate aggregate, PovRay preset, StringBuilder builder) {

        PovRayDef.core(aggregate, preset, builder);
    }
}
