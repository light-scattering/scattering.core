package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

public class FAggregateModuleFractalDimensionDef {
    private final FAggregateModuleFractalDimensionBCDef bc;
    private final FAggregateModuleFractalDimensionDCDef dc;
    private final FAggregateModuleFractalDimensionMRDef mr;

    protected FAggregateModuleFractalDimensionDef(ScatFactory factory, FAggregate aggregate) {

        this.bc = new FAggregateModuleFractalDimensionBCDef(factory, aggregate);
        this.dc = new FAggregateModuleFractalDimensionDCDef(factory, aggregate);
        this.mr = new FAggregateModuleFractalDimensionMRDef(factory, aggregate);
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
            case MASS -> this.mr.analyze(
                    this.mr.getResults(RadiusOfGyration.SIMPLE_POLY, 1.1, true),
                    0.9
            );
            case MASS_FULL -> this.mr.analyze(
                    this.mr.getResults(RadiusOfGyration.SIMPLE_POLY, 1.1, false),
                    0.9
            );
        };
    }

    protected double getFractalDimensionMassRadius(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return this.mr.analyze(this.mr.getResults(method, stepFactor, rangeLimit), window);
    }

    protected double getFractalDimensionBoxCounting(double window, double step, int shift, boolean reposition, boolean pca) {

        return this.bc.analyze(this.bc.getResultsOptimized(step, shift, reposition, pca), window);
    }

    protected double getFractalDimensionDensityCorrelation(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return this.dc.analyze(this.dc.getResults(method, stepFactor, rangeLimit), window);
    }
}
