package eu.scattering.core.impl.component.aggregate.monitor.cc.module;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.kinetic.cc.FConfigCCPL;
import eu.scattering.core.design.component.aggregate.meta.df.kinetic.cc.FMetaCCPL;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBarMetaGlobal;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.Locale;

public class FMonitorCCRadiusOfGyrationDef implements FMonitorCCRadiusOfGyration {
    private final ScatFactory factory;

    private final RadiusOfGyration radiusOfGyration;
    private final FPlotBar fPlotBar;

    private FMonitorCCRadiusOfGyrationDef(ScatFactory factory, RadiusOfGyration radiusOfGyration) {
        this.factory = factory;

        this.radiusOfGyration = radiusOfGyration;
        this.fPlotBar = factory.getFPlotBar();

        this.fPlotBar.setName("Radius of gyration");
    }

    public static FMonitorCCRadiusOfGyration create(ScatFactory factory, RadiusOfGyration radiusOfGyration) {

        return new FMonitorCCRadiusOfGyrationDef(factory, radiusOfGyration);
    }

    @Override
    public FPlotBar getRefFPlotBar() {

        return this.fPlotBar;
    }

    @Override
    public void accept(FAggregate aggA, FAggregate aggB) {

        if (aggA == null || aggA.getRefParticles().size() == 0) {
            this.fPlotBar.clear();
        } else {
            this.fPlotBar.add(aggA.getRefParticles().size(), aggA.getRadiusOfGyration(radiusOfGyration));
        }
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public double getPowerLawDimension(FConfigCCPL.Preset preset, FMetaCCPL meta) {

        return getPowerLawDimension(this.factory.getFConfigCCPL(preset), meta);
    }

    @Override
    public double getPowerLawDimension(FConfigCCPL config, FMetaCCPL meta) {

        FPlot results = getResults(config);
        FPoly regression = getRegression(results, config);

        if (meta != null) {
            meta.setPythonRenderScript(FMetaCCPL.Plot.PARSED, plotParsed(results, regression));
            meta.setPythonRenderScript(FMetaCCPL.Plot.RAW, plotRaw());
        }

        return regression.at(1);
    }

    private FPlot getResults(FConfigCCPL config) {
        FPlotBar results = this.fPlotBar.copy();

        results.sortX(true);

        FPlot resultsReduced = this.fPlotBar.toFPlot(config.getReducer());

        int drop = (int) (resultsReduced.size() * config.getDropRatio());

        for (int i = 0 ; i < drop ; i++) {
            resultsReduced.setX(i, Double.NaN);
        }

        resultsReduced.removeNaN();

        resultsReduced.swapXY();

        resultsReduced.mutateX(FStat::ln);
        resultsReduced.mutateY(FStat::ln);

        return resultsReduced;
    }

    private FPoly getRegression(FPlot results, FConfigCCPL config) {

        if (config.getWindowRatio() < 1) {
            return results.reg().fitSlope((int) (results.size() * config.getWindowRatio()));
        }

        return results.reg().fitLinear();
    }

    private String plotParsed(FPlot results, FPoly regression) {
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

    private String plotRaw() {
        FPlotBar results = getRefFPlotBar().copy();

        results.mutateX(FStat::ln);
        results.mutateY((statY) -> statY.forEach(FStat::ln));

        results.setName("average");

        FPlotBarMetaGlobal metaGlobal = factory.getFPlotBarMetaGlobal()
                .setPositionLegend(FPlotBarMetaGlobal.Position.LEFT)
                .setFontSize(32)
                .setNameX("ln N<sub>p</sub>")
                .setNameY("ln R<sub>g</sub>")
                .setCoreLineColor("black")
                .setCoreLineWidth(4)
                .setRangeLineColor("darkgray")
                .setRangeLineWidth(3)
                .setRangeShow(true)
                .setErrorShow(false);

        return  factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(metaGlobal, results);
    }
}
