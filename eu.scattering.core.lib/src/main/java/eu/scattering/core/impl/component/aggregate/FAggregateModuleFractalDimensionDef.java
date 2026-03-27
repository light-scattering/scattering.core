package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointHelper;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.*;

public class FAggregateModuleFractalDimensionDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    private final FAggregateModuleFractalDimensionBCDef bc;
    private final FAggregateModuleFractalDimensionDCDef dc;

    protected FAggregateModuleFractalDimensionDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;

        this.bc = new FAggregateModuleFractalDimensionBCDef(factory, aggregate);
        this.dc = new FAggregateModuleFractalDimensionDCDef(factory, aggregate);
    }

    // -------------------------------------------------------------------------------------------------

    protected FPlot getMassRadiusFunction(RadiusOfGyration type, double stepFactor, boolean rangeLimit) {
        FPoint massCenter = this.factory.getFPoint();

        List<Double> massFragments = new ArrayList<>(this.aggregate.size());
        List<FPos3D> centerFragments = new ArrayList<>(this.aggregate.size());

        double radiusOfGyration = this.aggregate.getRadiusOfGyration(type, massCenter, massFragments, centerFragments);

        double range = rangeLimit ? radiusOfGyration * 0.5 : this.aggregate.getRadiusFrom(massCenter) * 2.1;

        FPlot results = createMassRadiusData(stepFactor, range);

        int count = setMassRadiusData(results, massCenter.toFPos3D(), massFragments, centerFragments, range);

        results.mutateY(a -> a.mutate(b -> b / count));
        results.mutateX(a -> a.mutate(Math::sqrt));

        return results;
    }

    private FPlot createMassRadiusData(double stepFactor, double limit) {
        FPlot results = this.factory.getFPlot();

        FStat particles = this.aggregate.getFStatParticleRadius();

        double min = particles.mean() * 2;

        double step = min * stepFactor;
        while (step <= limit) {
            results.add(step * step, 0);
            step = step * stepFactor;
        }

        return results;
    }

    private int setMassRadiusData(FPlot results, FPos3D massCenter, List<Double> massFragments, List<FPos3D> centerFragments, double limit) {
        int count = 0;
        int size = this.aggregate.size();
        double limitP2 = limit * limit;

        FPointHelper helper = this.factory.getFPointHelper();

        for (int i = 0 ; i < size ; i++) {
            FPos3D cA = centerFragments.get(i);

            if (helper.getDistanceP2(massCenter, cA) > limitP2) {
                continue;
            }

            count ++;

            for (int j = 0 ; j < size ; j++) {
                FPos3D cB = centerFragments.get(j);

                double distP2 = helper.getDistanceP2(cA, cB);

                if (distP2 > limitP2) {
                    continue;
                }

                for (int k = 0 ; k < results.size() ; k++) {
                    if (distP2 <= results.getX(k)) {
                        results.setY(k, results.getY(k) + massFragments.get(j));

                        break;
                    }
                }
            }
        }

        for (int k = 1 ; k < results.size() ; k++) {
            results.setY(k, results.getY(k) + results.getY(k - 1));
        }

        return count;
    }

    // -------------------------------------------------------------------------------------------------

    protected FPlot getBoxCoverageFunction(double step, int shift, boolean reposition, boolean pca) {

        return this.bc.getResultsOptimized(step, shift, reposition, pca);
    }

    // -------------------------------------------------------------------------------------------------

    protected FPlot getDensityCorrelationFunction(double step) {

        return this.dc.getResults(RadiusOfGyration.SIMPLE_POLY, step, false);
    }

    // -------------------------------------------------------------------------------------------------

    protected double getFractalDimension(FractalDimension type) {

        return switch (type) {
            case BC_REFERENCE -> this.bc.analyze(
                    this.bc.getResultsBruteForce(),
                    1
            );
            case BC_SIMPLIFIED -> this.bc.analyze(
                    this.bc.getResultsOptimized(2, 1, true, false),
                    1
            );
            case BC_OPTIMIZED -> this.bc.analyze(
                    this.bc.getResultsOptimized(2, 3, false, false),
                    0.9
            );
            case CORRELATION -> this.dc.analyze(
                    this.dc.getResults(RadiusOfGyration.SIMPLE_POLY, 1.1, true),
                    0.9
            );
            case CORRELATION_FULL -> this.dc.analyze(
                    this.dc.getResults(RadiusOfGyration.SIMPLE_POLY, 1.1, false),
                    0.9
            );
            case MASS -> getMassRadiusAnalyze(
                    getMassRadiusFunction(RadiusOfGyration.SIMPLE_POLY, 1.1, true),
                    0.9
            );
            case MASS_FULL -> getMassRadiusAnalyze(
                    getMassRadiusFunction(RadiusOfGyration.SIMPLE_POLY, 1.1, false),
                    0.9
            );
        };
    }

    protected double getFractalDimensionMassRadius(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return getMassRadiusAnalyze(getMassRadiusFunction(method, stepFactor, rangeLimit), window);
    }

    protected double getFractalDimensionBoxCounting(double window, double step, int shift, boolean reposition, boolean pca) {

        return this.bc.analyze(this.bc.getResultsOptimized(step, shift, reposition, pca), window);
    }

    protected double getFractalDimensionDensityCorrelation(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return this.dc.analyze(this.dc.getResults(method, stepFactor, rangeLimit), window);
    }

    private double getMassRadiusAnalyze(FPlot data, double window) {

        if (window <= 0 || window > 1) {
            throw new IllegalArgumentException("The window must be in range 0-1");
        }

        for (int i = 1 ; i < data.size() ; i++) {
            if (data.getY(i) == data.getY(i - 1)) {
                data.setX(i - 1, Double.NaN);
            } else {

                break;
            }
        }

        for (int i = data.size() - 2 ; i >= 0 ; i--) {
            if (data.getY(i) == data.getY(i + 1)) {
                data.setX(i + 1 , Double.NaN);
            } else {

                break;
            }
        }

        data = data.removeNaN();

        data.mutateY((x, y) -> Math.log(y));
        data.mutateX((x, y) -> Math.log(x));

        data.filter((x, y) -> y > 0);

        FPoly regression = window == 1 ? data.reg().poly(1) : data.reg().fitSlope((int) (data.size() * window));

        FPlot fit = data.copy();
        fit.setY(regression);

        FPlotMeta plotConfig = factory.getFPlotMeta()
                .setAnnotation("Test data");

        String plot = factory.getSaveAspect().getStatisticsContext()
                .toPythonPlotly(plotConfig, data, fit);

        return regression.at(1);
    }


}
