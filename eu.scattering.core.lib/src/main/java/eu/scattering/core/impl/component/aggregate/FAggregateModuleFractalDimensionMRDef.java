package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigMR;
import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaMR;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointHelper;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FAggregateModuleFractalDimensionMRDef {
    private final ScatterFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleFractalDimensionMRDef(ScatterFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    protected FPlot getResults(FConfigMR config, FMetaMR meta) {
        long millis = System.currentTimeMillis();

        Process process = new Process(this.factory, this.aggregate, config, meta);

        FPlot results = process.process();

        if (meta != null) {
            meta.setExecutionTimeMillis(System.currentTimeMillis() - millis);
            meta.setRefData(results);
        }

        return results;
    }

    protected double analyze(FPlot results, FConfigMR config, FMetaMR meta) {
        double windowRatio = config.getWindowRatio();

        if (windowRatio <= 0 || windowRatio > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        for (int i = 1 ; i < results.size() ; i++) {
            if (results.getY(i) == results.getY(i - 1)) {
                results.setX(i - 1, Double.NaN);
            } else {

                break;
            }
        }

        for (int i = results.size() - 2 ; i >= 0 ; i--) {
            if (results.getY(i) == results.getY(i + 1)) {
                results.setX(i + 1 , Double.NaN);
            } else {

                break;
            }
        }

        results = results.removeNaN();

        results.mutateY((x, y) -> Math.log(y));
        results.mutateX((x, y) -> Math.log(x));

        results.filter((x, y) -> y > 0);

        FPoly regression = windowRatio == 1 ?
                results.reg().poly(1) : results.reg().fitSlope((int) (results.size() * windowRatio));

        FPlot approximation = results.copy();
        approximation.setY(regression);

        double dimension = regression.at(1);

        if (meta != null) {
            meta.setPythonRenderScript(plot(results, approximation, regression, dimension));
        }

        return dimension;
    }

    private String plot(FPlot results, FPlot approximation, FPoly regression, double dimension) {
        double r2 = results.r2(regression);

        String dimFormat = String.format(Locale.US, "%.2f", dimension);
        String r2Format = String.format(Locale.US, "%.4f", r2);

        FPlotMetaGlobal metaGlobal = factory.getFPlotMetaGlobal()
                .setAnnotation("R<sup>2</sup> ≈ " + r2Format)
                .setPositionAnnotation(FPlotMetaGlobal.Position.RIGHT)
                .setPositionLegend(FPlotMetaGlobal.Position.LEFT)
                .setFontSize(48)
                .setGridSize(3)
                .setNameX("ln ρ")
                .setNameY("ln M(ρ)");

        FPlotMeta metaPlotFit = factory.getFPlotMeta()
                .setLinesColor("black")
                .setLinesWidth(6)
                .setMarkersSize(21)
                .setLinesShow(true)
                .setMarkersShow(false);

        FPlotMeta metaPlotResults = factory.getFPlotMeta()
                .setMarkersColor("black")
                .setLinesWidth(6)
                .setMarkersSize(21)
                .setLinesShow(false)
                .setMarkersShow(true);

        approximation.setName("Linear fit (D<sub>MR</sub> ≈ " + dimFormat + ")")
                .setRefMeta(metaPlotFit);

        results.setName("Mass")
                .setRefMeta(metaPlotResults);

        return  factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(metaGlobal, approximation, results);
    }

    // -------------------------------------------------------------------------------------------------

    static class Process {
        private final ScatterFactory factory;

        private final FConfigMR config;
        private final FMetaMR meta;

        private final FAggregate aggregate;

        private final FPlot results;
        private final FPoint massCenter;

        private final List<Double> massFragments;
        private final List<FPos3D> centerFragments;

        private double min;
        private double max;

        private Process(ScatterFactory factory, FAggregate aggregate, FConfigMR config, FMetaMR meta) {
            this.factory = factory;

            this.config = config;
            this.meta = meta;

            this.aggregate = aggregate;

            this.results = factory.getFPlot();
            this.massCenter = factory.getFPoint();

            this.massFragments = new ArrayList<>(this.aggregate.size());
            this.centerFragments = new ArrayList<>(this.aggregate.size());

            this.min = Double.POSITIVE_INFINITY;
            this.max = Double.NEGATIVE_INFINITY;
        }

        FPlot process() {

            run();

            return this.results;
        }

        protected void run() {
            double rog = this.aggregate.getRadiusOfGyration(this.config.getRadiusOfGyration(), this.massCenter, this.massFragments, this.centerFragments);
            double range = this.config.isRestricted() ? rog * 0.5 : this.aggregate.getRadiusFrom(this.massCenter) * 2.1;

            FStat rp = this.aggregate.getFStatParticleRadius();

            double delta = rp.mean();
            double start = delta * 2 * this.config.getScalingFactor();

            initResults(range, start);

            int count = setData(range);

            limitResults();

            this.results.mutateY(a -> a.mutate(b -> b / count));
            this.results.mutateX(a -> a.mutate(Math::sqrt));

            if (this.meta != null) {
                this.meta.setRefParticlesCount(count);
            }
        }

        private void initResults(double range, double start) {
            double step = start;

            while (step <= range) {
                this.results.add(step * step, 0);
                step = step * this.config.getScalingFactor();
            }
        }

        private void limitResults() {
            double cutoff = this.max;

            this.results.filter((x, y) -> x <= cutoff);
        }

        private int setData(double limit) {
            int count = 0;
            int size = this.aggregate.size();
            double limitP2 = limit * limit;

            FPointHelper helper = this.factory.getFPointHelper();

            for (int i = 0 ; i < size ; i++) {
                FPos3D cA = this.centerFragments.get(i);

                if (this.massCenter.getDistanceP2(cA) > limitP2) {
                    continue;
                }

                count ++;

                for (int j = 0 ; j < size ; j++) {
                    FPos3D cB = this.centerFragments.get(j);

                    double distP2 = helper.getDistanceP2(cA, cB);

                    if (distP2 > limitP2) {
                        continue;
                    }

                    setExtremum(distP2);

                    for (int k = 0 ; k < this.results.size() ; k++) {
                        if (distP2 <= this.results.getX(k)) {
                            this.results.setY(k, this.results.getY(k) + this.massFragments.get(j));

                            break;
                        }
                    }
                }
            }

            for (int k = 1 ; k < this.results.size() ; k++) {
                this.results.setY(k, this.results.getY(k) + this.results.getY(k - 1));
            }

            return count;
        }

        private void setExtremum(double distance) {

            if (distance < this.min) {
                this.min = distance;
            }

            if (distance > this.max) {
                this.max = distance;
            }
        }
    }
}
