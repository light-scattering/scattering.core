package eu.scattering.core.impl.aspect.save;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.save.FSaveAspect;
import eu.scattering.core.design.component.ComponentAspectSave;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.statistics.StatisticsAspectSave;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.base.FStatMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarMetaGlobal;
import eu.scattering.core.design.storage.StorageAspectSave;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import eu.scattering.core.impl.component.ComponentAspectSaveDef;
import eu.scattering.core.impl.statistics.StatisticsAspectSaveDef;
import eu.scattering.core.impl.storage.StorageAspectSaveDef;

public class FSaveAspectDef implements FSaveAspect {
    private final StorageAspectSave storage;
    private final ComponentAspectSave component;
    private final StatisticsAspectSave statistics;

    private FSaveAspectDef(ScatterFactory factory) {

        this.storage = StorageAspectSaveDef.create();
        this.component = ComponentAspectSaveDef.create(factory);
        this.statistics = StatisticsAspectSaveDef.create(factory);
    }

    public static FSaveAspect create(ScatterFactory factory) {

        return new FSaveAspectDef(factory);
    }

    @Override
    public void toJSON(FAggregate aggregate, StringBuilder builder) {

        this.component.toJSON(aggregate, builder);
    }

    @Override
    public void toBasic(FAggregate aggregate, ExBasic preset, StringBuilder builder) {

        this.component.toBasic(aggregate, preset, builder);
    }

    @Override
    public void toFLAGE(FAggregate aggregate, StringBuilder builder) {

        this.component.toFLAGE(aggregate, builder);

    }

    @Override
    public void toPovRay(FAggregate aggregate, ExPovRay preset, StringBuilder builder) {

        this.component.toPovRay(aggregate, preset, builder);
    }

    @Override
    public void toNGSolve(FAggregate aggregate, StringBuilder builder) {

        this.component.toNGSolve(aggregate, builder);
    }

    @Override
    public String toCLI(FPoint fPoint) {

        return this.component.toCLI(fPoint);
    }

    @Override
    public String toCLI(FVector fVector) {

        return this.component.toCLI(fVector);
    }

    @Override
    public String toCLI(FStat stat) {

        return this.statistics.toCLI(stat);
    }

    @Override
    public String toPythonPlotlyHistogram(FStatMeta config, FStat... stat) {

        return this.statistics.toPythonPlotlyHistogram(config, stat);
    }

    @Override
    public String toCLI(FPlot plot) {

        return this.statistics.toCLI(plot);
    }

    @Override
    public String toPythonPlotly(FPlotMetaGlobal config, FPlot... plot) {

        return this.statistics.toPythonPlotly(config, plot);
    }

    @Override
    public String toPythonPlotlyHistogram(FPlotMetaGlobal config, FPlot... plot) {

        return this.statistics.toPythonPlotlyHistogram(config, plot);
    }

    @Override
    public String toPythonPlotly(FPlotBarMetaGlobal config, FPlotBar plotBar) {

        return this.statistics.toPythonPlotly(config, plotBar);
    }

    @Override
    public String toCLI(FPos3D fPos3D) {

        return this.storage.toCLI(fPos3D);
    }

    @Override
    public String toCLI(FPairPos3D fPairPos3D) {

        return this.storage.toCLI(fPairPos3D);
    }
}
