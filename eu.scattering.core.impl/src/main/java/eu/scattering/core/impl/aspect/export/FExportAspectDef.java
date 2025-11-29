package eu.scattering.core.impl.aspect.export;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.export.FExportAspect;
import eu.scattering.core.design.component.ComponentAspectExport;
import eu.scattering.core.design.statistics.StatisticsAspectExport;
import eu.scattering.core.impl.component.ComponentAspectExportDef;
import eu.scattering.core.impl.statistics.StatisticsAspectExportDef;

public class FExportAspectDef implements FExportAspect {
    private static FExportAspect SELF;

    private final ScatFactory factory;

    private FExportAspectDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FExportAspect get(ScatFactory factory) {

        if (FExportAspectDef.SELF == null) {
            FExportAspectDef.SELF = new FExportAspectDef(factory);
        }

        return FExportAspectDef.SELF;
    }

    //--------------------------------------------------

    @Override
    public StatisticsAspectExport getStatisticsContext() {

        return StatisticsAspectExportDef.create(this.factory);
    }

    @Override
    public ComponentAspectExport getComponentContext() {

        return ComponentAspectExportDef.create(this.factory);
    }
}
