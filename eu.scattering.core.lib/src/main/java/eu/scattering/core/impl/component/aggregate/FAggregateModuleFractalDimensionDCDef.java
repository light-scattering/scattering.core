package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.meta.dc.FMetaDC;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxDouble;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

// TODO - Refactor (limit method parameters).
public class FAggregateModuleFractalDimensionDCDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleFractalDimensionDCDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    protected FPlot getResults(RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return getResults(method, stepFactor, rangeLimit, null);
    }

    protected FPlot getResults(RadiusOfGyration method, double stepFactor, boolean rangeLimit, FMetaDC meta) {
        long executionTime = System.currentTimeMillis();

        FPlot results = this.factory.getFPlot();
        FPoint massCenter = this.factory.getFPoint();

        double radiusOfGyration = this.aggregate.getRadiusOfGyration(method, massCenter, null, null);

        double range = rangeLimit ? radiusOfGyration * 0.5 : this.aggregate.getRadiusFrom(massCenter) * 2.1;

        FStat particles = this.aggregate.getFStatParticleRadius();

        double delta = particles.mean();
        double start = delta * 2 * stepFactor;

        initResults(results, range, stepFactor, start);

        FBoxDouble min = factory.getFBoxDouble();
        FBoxDouble max = factory.getFBoxDouble();

        int refs = getCorePairDistance(results, massCenter.toFPos3D(), min, max, delta, range);

        limitResults(results, range, delta);

        processDensity(results, delta);

        normalize(results, refs);
        sanitize(results);

        if (meta != null) {
            meta.setExecutionTime(System.currentTimeMillis() - executionTime);
            meta.setNumberOfRefs(refs);
            meta.setData(results);
        }

        return results;
    }

    protected double analyze(FPlot results, double window) {

        return analyze(results, window, null);
    }

    protected double analyze(FPlot results, double window, FMetaDC meta) {

        if (window <= 0 || window > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        results.filter((x, y) -> y > 0);

        results.mutate((a, b) -> {
            a.ln();
            b.ln();
        });

        FPoly regression = window == 1 ? results.reg().poly(1) : results.reg().fitSlope((int) (results.size() * window));

        FPlot approximation = results.copy();
        approximation.setY(regression);

        double dim = 3 + regression.at(1);

        if (meta != null) {
            meta.setPlotDerivative(plotDerivative(results));
            meta.setPlotApproximation(plotApproximation(results, approximation, regression, dim));
        }

        return dim;
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

        return  factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(metaGlobal, dataMin, dataMedian, dataMax, dataRaw);
    }

    private String plotApproximation(FPlot results, FPlot approximation, FPoly regression, double dim) {
        double r2 = results.r2(regression);

        String dimFormat = String.format(Locale.US, "%.2f", dim);
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

    private int getCorePairDistance(FPlot results, FPos3D center, FBoxDouble min, FBoxDouble max, double delta, double range) {

        min.setValue(Double.POSITIVE_INFINITY);
        max.setValue(Double.NEGATIVE_INFINITY);

        List<Shape> internal = new ArrayList<>();
        List<Shape> external = new ArrayList<>();

        splitByRange(internal, external, center, range);

        addInternalPairDistance(results, internal, min, max, delta, range);
        addExternalPairDistance(results, internal, external, min, max, delta, range);

        return internal.size();
    }

    private void splitByRange(Collection<Shape> internal, Collection<Shape> external, FPos3D center, double range) {
        List<Shape> particles = this.aggregate.getRefParticles().asList();

        if (range <= 0) {
            external.addAll(particles);
            return;
        }

        double rangeP2 = range * range;
        double cutoffP2 = (range * 2) * (range * 2);

        for (Shape particle : particles) {
            double distance = particle.getDistCenterP2(center);

            if (distance <= rangeP2) {
                internal.add(particle);
            } else if (distance <= cutoffP2) {
                external.add(particle);
            }
        }
    }

    private void addInternalPairDistance(FPlot results, List<Shape> internal, FBoxDouble min, FBoxDouble max, double delta, double range) {

        for (int i = 0 ; i < internal.size() - 1 ; i++) {
            for (int j = i + 1 ; j < internal.size() ; j++) {
                double distance = internal.get(i).getDistCenter(internal.get(j));

                if (distance <= range) {

                    setExtremum(distance, min, max);

                    setDistance(results, distance, delta, 2);
                }
            }
        }
    }

    private void addExternalPairDistance(FPlot results, List<Shape> internal, List<Shape> external, FBoxDouble min, FBoxDouble max, double delta, double range) {

        for (Shape inner : internal) {
            for (Shape outer : external) {
                double distance = inner.getDistCenter(outer);

                if (distance <= range) {

                    setExtremum(distance, min, max);

                    setDistance(results, distance, delta, 1);
                }
            }
        }
    }

    private void setExtremum(double distance, FBoxDouble min, FBoxDouble max) {

        if (distance < min.getValue()) {
            min.setValue(distance);
        }

        if (distance > max.getValue()) {
            max.setValue(distance);
        }
    }

    private void initResults(FPlot results, double range, double factor, double start) {
        double step = start;

        while (step <= range) {
            results.add(step, 0);
            step = step * factor;
        }
    }

    private void limitResults(FPlot results, double max, double delta) {
        double cutoff = max - delta;

        results.filter((x, y) -> x <= cutoff);
    }

    private void setDistance(FPlot results, double distance, double delta, int quantity) {

        for (int i = 0 ; i < results.size() ; i++) {

            if (Math.abs(distance - results.getX(i)) < delta) {
                results.setY(i, results.getY(i) + quantity);
            }

            if (distance + delta < results.getX(i)) {
                break;
            }
        }
    }

    private void processDensity(FPlot results, double delta) {
        FSphereHelper helper = this.factory.getFSphereHelper();

        results.mutateY((x, y) -> y / helper.getVolumeRing(x - delta, x + delta));
    }

    private void normalize(FPlot results, int refs) {

        results.mutateY((x, y) -> y / refs);
    }

    private void sanitize(FPlot results) {

        results.filter((x, y) -> x > 0 && y > 0);
    }
}
