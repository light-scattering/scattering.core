package eu.scattering.core.impl.aspect.export;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.export.FExportAspect;
import eu.scattering.core.design.component.aggregate.FAggregateAspectExport;
import eu.scattering.core.design.statistics.StatisticsAspectExport;
import eu.scattering.core.impl.component.aggregate.FAggregateAspectExportDef;
import eu.scattering.core.impl.statistics.StatisticsAspectExportDef;

public class FExportAspectDef implements FExportAspect {
    private final ScatFactory factory;

    private FExportAspectDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FExportAspect create(ScatFactory factory) {

        return new FExportAspectDef(factory);
    }

    //--------------------------------------------------

    @Override
    public StatisticsAspectExport getStatisticsContext() {

        return StatisticsAspectExportDef.create(this.factory);
    }

    @Override
    public FAggregateAspectExport getFAggregateContext() {

        return FAggregateAspectExportDef.create();
    }
}
