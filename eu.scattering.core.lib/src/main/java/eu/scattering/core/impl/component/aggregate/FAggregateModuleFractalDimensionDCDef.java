package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigDC;
import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaDC;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FAggregateModuleFractalDimensionDCDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleFractalDimensionDCDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    protected FPlot getResults(FConfigDC config, FMetaDC meta) {
        long millis = System.currentTimeMillis();

        Process process = new Process(this.factory, this.aggregate, config, meta);

        FPlot results = process.process();

        if (meta != null) {
            meta.setExecutionTimeMillis(System.currentTimeMillis() - millis);
            meta.setRefData(results);
        }

        return results;
    }

    // -------------------------------------------------------------------------------------------------

    protected double analyze(FPlot results, FConfigDC config, FMetaDC meta) {
        double windowRatio = config.getWindowRatio();

        if (windowRatio <= 0 || windowRatio > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        results.filter((x, y) -> y > 0);

        results.mutate((a, b) -> {
            a.ln();
            b.ln();
        });

        FPoly regression = windowRatio == 1 ?
                results.reg().poly(1) : results.reg().fitSlope((int) (results.size() * windowRatio));

        FPlot approximation = results.copy();
        approximation.setY(regression);

        double dimension = 3 + regression.at(1);

        if (meta != null) {
            switch (meta.getScriptType()) {
                case DEFAULT -> meta.setPythonRenderScript(plotDefault(results, approximation, regression, dimension));
                case DERIVATIVE -> meta.setPythonRenderScript(plotDerivative(results));
            }
        }

        return dimension;
    }

    private String plotDefault(FPlot results, FPlot approximation, FPoly regression, double dimension) {
        double r2 = results.r2(regression);

        String dimFormat = String.format(Locale.US, "%.2f", dimension);
        String r2Format = String.format(Locale.US, "%.4f", r2);

        FPlotMetaGlobal metaGlobal = factory.getFPlotMetaGlobal()
                .setAnnotation("R<sup>2</sup> ≈ " + r2Format)
                .setFontSize(32)
                .setNameX("ln ρ")
                .setNameY("ln C(ρ)");

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

        approximation.setName("Linear fit (D<sub>DC</sub> ≈ " + dimFormat + ")")
                .setRefMeta(metaPlotFit);

        results.setName("Density correlation")
                .setRefMeta(metaPlotResults);

        return  factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(metaGlobal, approximation, results);
    }

    private String plotDerivative(FPlot results) {
        double range = 0.15;

        FPlot dataRaw = results.copy();

        dataRaw.derivate();

        double median = dataRaw.getRefCoreY().median();
        double min = median - range;
        double max = median + range;

        FPlot dataMedian = factory.getFPlot();
        dataMedian.add(dataRaw.getX(0), median);
        dataMedian.add(dataRaw.getX(dataRaw.size() - 1), median);

        FPlot dataMin = factory.getFPlot();
        dataMin.add(dataRaw.getX(0), min);
        dataMin.add(dataRaw.getX(dataRaw.size() - 1), min);

        FPlot dataMax = factory.getFPlot();
        dataMax.add(dataRaw.getX(0), max);
        dataMax.add(dataRaw.getX(dataRaw.size() - 1), max);

        dataMedian.mutateY((x, y) -> median);

        String initialMedianFormat = String.format(Locale.US, "%.2f", median);

        FPlotMetaGlobal metaGlobal = factory.getFPlotMetaGlobal()
                .setFontSize(32)
                .setNameX("ln ρ")
                .setNameY("Local dimension");

        FPlotMeta metaPlotMin = factory.getFPlotMeta()
                .setLinesColor("lightgray")
                .setLinesWidth(4)
                .setLinesShow(true)
                .setMarkersShow(false);

        FPlotMeta metaPlotMedian = factory.getFPlotMeta()
                .setLinesColor("black")
                .setLinesWidth(4)
                .setLinesShow(true)
                .setMarkersShow(false);

        FPlotMeta metaPlotMax = factory.getFPlotMeta()
                .setLinesColor("lightgray")
                .setLinesWidth(4)
                .setLinesShow(true)
                .setMarkersShow(false);

        FPlotMeta metaPlotDerivative = factory.getFPlotMeta()
                .setMarkersColor("black")
                .setLinesWidth(4)
                .setMarkersSize(14)
                .setLinesShow(false)
                .setMarkersShow(true);

        dataMin.setName("Min")
                .setRefMeta(metaPlotMin);

        dataMedian.setName("Median ≈ " + initialMedianFormat)
                .setRefMeta(metaPlotMedian);

        dataMax.setName("Max")
                .setRefMeta(metaPlotMax);

        dataRaw.setName("Raw data")
                .setRefMeta(metaPlotDerivative);

        return factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(metaGlobal, dataMin, dataMedian, dataMax, dataRaw);
    }

    // -------------------------------------------------------------------------------------------------

    static class Process {
        private final ScatFactory factory;

        private final FConfigDC config;
        private final FMetaDC meta;

        private final FAggregate aggregate;

        private final FPlot results;
        private final FPoint massCenter;

        private final List<Shape> internal;
        private final List<Shape> external;

        private double min;
        private double max;

        private Process(ScatFactory factory, FAggregate aggregate, FConfigDC config, FMetaDC meta) {
            this.factory = factory;

            this.config = config;
            this.meta = meta;

            this.aggregate = aggregate;

            this.results = factory.getFPlot();
            this.massCenter = factory.getFPoint();

            this.internal = new ArrayList<>();
            this.external = new ArrayList<>();

            this.min = Double.POSITIVE_INFINITY;
            this.max = Double.NEGATIVE_INFINITY;
        }

        FPlot process() {

            run();

            return this.results;
        }

        private void run() {
            double rog = this.aggregate.getRadiusOfGyration(this.config.getRadiusOfGyration(), this.massCenter, null, null);
            double range = this.config.isRestricted() ? rog * 0.5 : this.aggregate.getRadiusFrom(this.massCenter) * 2.1;

            FStat rp = this.aggregate.getFStatParticleRadius();

            double delta = rp.mean();
            double start = delta * 2 * this.config.getScalingFactor();

            initResults(range, start);

            getCorePairDistance(delta, range);

            limitResults(delta);

            processDensity(delta);

            normalize();
            sanitize();

            if (this.meta != null) {
                this.meta.setRefParticlesCount(this.internal.size());
            }
        }

        private void getCorePairDistance(double delta, double range) {

            splitByRange(range);

            addInternalPairDistance(delta, range);
            addExternalPairDistance(delta, range);
        }

        private void splitByRange(double range) {

            List<Shape> particles = this.aggregate.getRefParticles().asList();

            if (range <= 0) {
                this.external.addAll(particles);
                return;
            }

            double rangeP2 = range * range;
            double cutoffP2 = (range * 2) * (range * 2);

            for (Shape particle : particles) {
                double distance = particle.getDistCenterP2(this.massCenter);

                if (distance <= rangeP2) {
                    this.internal.add(particle);
                } else if (distance <= cutoffP2) {
                    this.external.add(particle);
                }
            }
        }

        private void addInternalPairDistance(double delta, double range) {

            for (int i = 0 ; i < this.internal.size() - 1 ; i++) {
                for (int j = i + 1 ; j < this.internal.size() ; j++) {
                    double distance = this.internal.get(i).getDistCenter(this.internal.get(j));

                    if (distance <= range) {

                        setExtremum(distance);

                        setDistance(distance, delta, 2);
                    }
                }
            }
        }

        private void addExternalPairDistance(double delta, double range) {

            for (Shape inner : this.internal) {
                for (Shape outer : this.external) {
                    double distance = inner.getDistCenter(outer);

                    if (distance <= range) {

                        setExtremum(distance);

                        setDistance(distance, delta, 1);
                    }
                }
            }
        }

        private void setExtremum(double distance) {

            if (distance < this.min) {
                this.min = distance;
            }

            if (distance > this.max) {
                this.max = distance;
            }
        }

        private void initResults(double range, double start) {
            double step = start;

            while (step <= range) {
                this.results.add(step, 0);
                step = step * this.config.getScalingFactor();
            }
        }

        private void limitResults(double delta) {
            double cutoff = this.max - delta;

            this.results.filter((x, y) -> x <= cutoff);
        }

        private void setDistance(double distance, double delta, int quantity) {

            for (int i = 0 ; i < this.results.size() ; i++) {

                if (Math.abs(distance - this.results.getX(i)) < delta) {
                    this.results.setY(i, this.results.getY(i) + quantity);
                }

                if (distance + delta < this.results.getX(i)) {
                    break;
                }
            }
        }

        private void processDensity(double delta) {
            FSphereHelper helper = this.factory.getFSphereHelper();

            this.results.mutateY((x, y) -> y / helper.getVolumeRing(x - delta, x + delta));
        }

        private void normalize() {
            int refs = this.internal.size();

            this.results.mutateY((x, y) -> y / refs);
        }

        private void sanitize() {

            this.results.filter((x, y) -> x > 0 && y > 0);
        }
    }
}
