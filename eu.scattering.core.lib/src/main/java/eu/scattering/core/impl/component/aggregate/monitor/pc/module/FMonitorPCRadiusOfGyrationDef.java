package eu.scattering.core.impl.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.kinetic.pc.FConfigPCPL;
import eu.scattering.core.design.component.aggregate.meta.df.kinetic.pc.FMetaPCPL;
import eu.scattering.core.design.component.aggregate.monitor.pc.module.FMonitorPCRadiusOfGyration;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.Locale;

public class FMonitorPCRadiusOfGyrationDef implements FMonitorPCRadiusOfGyration {
    private final ScatFactory factory;

    private final RadiusOfGyration radiusOfGyration;
    private final FPlot fPlot;

    private double skip = -1;

    private FMonitorPCRadiusOfGyrationDef(ScatFactory factory, RadiusOfGyration radiusOfGyration) {
        this.factory = factory;

        this.radiusOfGyration = radiusOfGyration;
        this.fPlot = factory.getFPlot();

        this.fPlot.setName("Radius of gyration");
    }

    public static FMonitorPCRadiusOfGyration create(ScatFactory factory, int skip, RadiusOfGyration radiusOfGyration) {
        FMonitorPCRadiusOfGyration results = new FMonitorPCRadiusOfGyrationDef(factory, radiusOfGyration);

        results.setSkip(skip);

        return results;
    }

    @Override
    public void setSkip(int skip) {

        this.skip = skip;
    }

    @Override
    public FPlot getRefFPlot() {

        return this.fPlot;
    }

    @Override
    public void accept(FAggregate fAggregate, Shape shape) {

        if (fAggregate == null || fAggregate.getRefParticles().size() == 0) {
            this.fPlot.clear();
        } else {
            if (fAggregate.getRefParticles().size() > this.skip) {
                this.fPlot.add(fAggregate.getRefParticles().size(), fAggregate.getRadiusOfGyration(radiusOfGyration));
            }
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public double getPowerLawDimension(FConfigPCPL.Preset preset, FMetaPCPL meta) {

        return getPowerLawDimension(this.factory.getFConfigPCPL(preset), meta);
    }

    @Override
    public double getPowerLawDimension(FConfigPCPL config, FMetaPCPL meta) {

        FPlot results = getResults(config);
        FPoly regression = getRegression(results, config);

        if (meta != null) {
            meta.setPythonRenderScript(plot(results, regression));
        }

        return regression.at(1);
    }

    private FPlot getResults(FConfigPCPL config) {
        FPlot results = this.fPlot.copy();

        results.sortX(true);

        int drop = (int) (results.size() * config.getDropRatio());

        for (int i = 0 ; i < drop ; i++) {
            results.setX(i, Double.NaN);
        }

        results.removeNaN();

        results.swapXY();

        results.mutateX(FStat::ln);
        results.mutateY(FStat::ln);

        return results;
    }

    private FPoly getRegression(FPlot results, FConfigPCPL config) {

        if (config.getWindowRatio() < 1) {
            return results.reg().fitSlope((int) (results.size() * config.getWindowRatio()));
        }

        return results.reg().fitLinear();
    }

    private String plot(FPlot results, FPoly regression) {
        FPlot approximation = results.copy();
        approximation.setY(regression);

        String dimFormat = String.format(Locale.US, "%.2f", regression.at(1));
        String r2Format = String.format(Locale.US, "%.4f", results.r2(regression));

        FPlotMetaGlobal metaGlobal = factory.getFPlotMetaGlobal()
                .setPositionLegend(FPlotMetaGlobal.Position.LEFT)
                .setPositionAnnotation(FPlotMetaGlobal.Position.RIGHT)
                .setAnnotation("R<sup>2</sup> ≈ " + r2Format)
                .setFontSize(32)
                .setNameX("ln R<sub>g</sub>")
                .setNameY("ln N<sub>p</sub>");

        FPlotMeta metaPlotFit = factory.getFPlotMeta()
                .setLinesColor("black")
                .setLinesWidth(4)
                .setLinesShow(true)
                .setMarkersShow(false);

        FPlotMeta metaPlotResults = factory.getFPlotMeta()
                .setMarkersColor("black")
                .setLinesWidth(4)
                .setMarkersSize(14)
                .setLinesShow(false)
                .setMarkersShow(true);

        approximation.setName("Linear fit (D<sub>PL</sub> ≈ " + dimFormat + ")")
                .setRefMeta(metaPlotFit);

        results.setName("Averaged data")
                .setRefMeta(metaPlotResults);

        return  factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(metaGlobal, approximation, results);
    }
}
