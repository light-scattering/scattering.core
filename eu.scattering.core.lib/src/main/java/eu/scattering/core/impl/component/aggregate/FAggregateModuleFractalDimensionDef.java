package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.bc.FConfigBC;
import eu.scattering.core.design.component.aggregate.meta.bc.FMetaBC;
import eu.scattering.core.design.component.aggregate.meta.dc.FMetaDC;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxString;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

public class FAggregateModuleFractalDimensionDef {
    private final FAggregateModuleFractalDimensionBCDef bc;
    private final FAggregateModuleFractalDimensionDCDef dc;
    private final FAggregateModuleFractalDimensionMRDef mr;
    private final ScatFactory factory;

    protected FAggregateModuleFractalDimensionDef(ScatFactory factory, FAggregate aggregate) {

        this.bc = new FAggregateModuleFractalDimensionBCDef(factory, aggregate);
        this.dc = new FAggregateModuleFractalDimensionDCDef(factory, aggregate);
        this.mr = new FAggregateModuleFractalDimensionMRDef(factory, aggregate);

        this.factory = factory;
    }

    // -------------------------------------------------------------------------------------------------

    protected FPlot getBoxCoverageFunction(FConfigBC config) {

        return this.bc.getResultsOptimized(config);
    }

    // -------------------------------------------------------------------------------------------------

    protected FPlot getDensityCorrelationFunction(double step) {

        return this.dc.getResults(RadiusOfGyration.SIMPLE_POLY, step, false);
    }

    // -------------------------------------------------------------------------------------------------

    protected double getFractalDimension(FractalDimension type, FBoxString plot) {

        return switch (type) {
            case BC_RAW -> {
                FConfigBC config = factory.getFConfigBC(FConfigBC.Preset.RAW);

                yield this.bc.analyze(this.bc.getResultsRaw(), config);
            }
            case BC_BASELINE -> {
                FConfigBC config = factory.getFConfigBC(FConfigBC.Preset.BASELINE);

                yield this.bc.analyze(this.bc.getResultsOptimized(config), config);
            }
            case BC_OPTIMIZED -> {
                FConfigBC config = factory.getFConfigBC(FConfigBC.Preset.OPTIMIZED);

                yield this.bc.analyze(this.bc.getResultsOptimized(config),config);
            }
            case DC_RESTRICTED -> this.dc.analyze(
                    this.dc.getResults(RadiusOfGyration.SIMPLE_POLY, 1.1, true),
                    0.9
            );
            case DC_FULL -> this.dc.analyze(
                    this.dc.getResults(RadiusOfGyration.SIMPLE_POLY, 1.1, false),
                    0.9
            );
            case MR_RESTRICTED -> this.mr.analyze(
                    this.mr.getResults(RadiusOfGyration.SIMPLE_POLY, 1.1, true),
                    0.9, plot
            );
            case MR_FULL -> this.mr.analyze(
                    this.mr.getResults(RadiusOfGyration.SIMPLE_POLY, 1.1, false),
                    0.9, plot
            );

            // -------------------------------------------------------------------------------------------------

            case BC_MANUSCRIPT_BASELINE -> {
                FConfigBC config = factory.getFConfigBC()
                        .setShiftsPerAxis(1)
                        .setAlignedPCA(false)
                        .setWindowRatio(0.9);

                yield this.bc.analyze(this.bc.getResultsOptimized(config), config);
            }
            case BC_MANUSCRIPT_PCA -> {
                FConfigBC config = factory.getFConfigBC()
                        .setScalingFactor(2.0)
                        .setShiftsPerAxis(1)
                        .setAlignedPCA(true)
                        .setWindowRatio(0.9);

                yield this.bc.analyze(this.bc.getResultsOptimized(config),config);
            }
            case BC_MANUSCRIPT_SHIFT -> {
                FConfigBC config = factory.getFConfigBC()
                        .setScalingFactor(2.0)
                        .setShiftsPerAxis(3)
                        .setAlignedPCA(false)
                        .setWindowRatio(0.9);

                yield this.bc.analyze(this.bc.getResultsOptimized(config),config);
            }
            case BC_MANUSCRIPT_SHIFT_PCA -> {
                FConfigBC config = factory.getFConfigBC()
                        .setScalingFactor(2.0)
                        .setShiftsPerAxis(3)
                        .setAlignedPCA(true)
                        .setWindowRatio(0.9);

                yield this.bc.analyze(this.bc.getResultsOptimized(config), config);
            }
            case BC_MANUSCRIPT_SHIFT_FACTOR -> {
                FConfigBC config = factory.getFConfigBC()
                        .setScalingFactor(1.25)
                        .setShiftsPerAxis(3)
                        .setAlignedPCA(false)
                        .setWindowRatio(0.9);

                yield this.bc.analyze(this.bc.getResultsOptimized(config),config);
            }
            case BC_MANUSCRIPT_SHIFT_PCA_FACTOR -> {
                FConfigBC config = factory.getFConfigBC()
                        .setScalingFactor(1.25)
                        .setShiftsPerAxis(3)
                        .setAlignedPCA(true)
                        .setWindowRatio(0.9);

                yield this.bc.analyze(this.bc.getResultsOptimized(config),config);
            }
        };
    }

    protected double getFractalDimensionBoxCounting(FConfigBC config) {

        return this.bc.analyze(this.bc.getResultsOptimized(config), config);
    }

    protected double getFractalDimensionBoxCounting(FConfigBC config, FMetaBC meta) {

        return this.bc.analyze(this.bc.getResultsOptimized(config), config, meta);
    }

    protected double getFractalDimensionMassRadius(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return this.mr.analyze(this.mr.getResults(method, stepFactor, rangeLimit), window, null);
    }

    protected double getFractalDimensionDensityCorrelation(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit, FMetaDC meta) {

        return this.dc.analyze(this.dc.getResults(method, stepFactor, rangeLimit, meta), window, meta);
    }
}
