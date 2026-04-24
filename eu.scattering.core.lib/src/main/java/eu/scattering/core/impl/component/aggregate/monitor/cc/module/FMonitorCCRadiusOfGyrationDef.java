package eu.scattering.core.impl.component.aggregate.monitor.cc.module;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.Locale;
import java.util.function.Function;

public class FMonitorCCRadiusOfGyrationDef implements FMonitorCCRadiusOfGyration {
    private final ScatFactory factory;

    private final RadiusOfGyration type;
    private final FPlotBar fPlotBar;

    private FMonitorCCRadiusOfGyrationDef(ScatFactory factory, RadiusOfGyration type) {

        this.factory = factory;

        this.type = type;
        this.fPlotBar = this.factory.getFPlotBar();

        this.fPlotBar.setName("Radius of gyration");
    }

    public static FMonitorCCRadiusOfGyration create(ScatFactory factory, RadiusOfGyration type) {

        return new FMonitorCCRadiusOfGyrationDef(factory, type);
    }

    @Override
    public FPlotBar getRefFPlotBar() {

        this.fPlotBar.sortX(true);

        return this.fPlotBar;
    }

    @Override
    public void accept(FAggregate aggA, FAggregate aggB) {

        if (aggA == null || aggA.getRefParticles().size() == 0) {
            this.fPlotBar.clear();
        } else {
            this.fPlotBar.add(aggA.getRefParticles().size(), aggA.getRadiusOfGyration(type));
        }
    }

    @Override
    public double getR2() {

        this.fPlotBar.sortX(true);

        FPlot results = getResults();
        FPoly regression = getRegression(results);

        return results.r2(regression);
    }

    @Override
    public double getPowerLawDimension() {

        this.fPlotBar.sortX(true);

        FPlot results = getResults();
        FPoly regression = getRegression(results);

        return regression.at(1);
    }

    @Override
    public String getFPlotVisual(Function<FStat, Double> function) {

        this.fPlotBar.sortX(true);

        FPlot results = getResults();
        FPoly regression = getRegression(results);

        FPlot approximation = results.copy();
        approximation.setY(regression);

        String dimFormat = String.format(Locale.US, "%.2f", regression.at(1));
        String r2Format = String.format(Locale.US, "%.4f", results.r2(regression));

        FPlotMetaGlobal metaGlobal = factory.getFPlotMetaGlobal()
                .setPositionLegend(FPlotMetaGlobal.Position.LEFT)
                .setPositionAnnotation(FPlotMetaGlobal.Position.RIGHT)
                .setAnnotation("R<sup>2</sup> ≈ " + r2Format)
                .setFontSize(32)
                .setNameX("ln N<sub>p</sub>")
                .setNameY("ln R<sub>g</sub>");

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

        results.setName("Raw data")
                .setRefMeta(metaPlotResults);

        return  factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(metaGlobal, approximation, results);
    }

    @Override
    public String getFPlotBarVisual() {
        return "";
    }

    private FPlot getResults() {
        FPlot results = this.fPlotBar.toFPlot(FStat::mean);

        results.swapXY();
        results.mutateX(FStat::ln);
        results.mutateY(FStat::ln);

        return results;
    }

    private FPoly getRegression(FPlot results) {

        return results.reg().fitSlope((int) (results.size() * 0.9));
    }
}
