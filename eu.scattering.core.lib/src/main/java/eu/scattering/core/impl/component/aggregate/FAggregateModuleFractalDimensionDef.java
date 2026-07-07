package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.bc.FConfigBC;
import eu.scattering.core.design.component.aggregate.meta.df.FMetaDF;
import eu.scattering.core.design.component.aggregate.meta.df.bc.FMetaBC;
import eu.scattering.core.design.component.aggregate.meta.df.dc.FMetaDC;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
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

    protected double getFractalDimension(FractalDimension type) {

        return getFractalDimension(type, null);
    }

    protected double getFractalDimension(FractalDimension type, FMetaDF meta) {

        return switch (type) {
            case BC_RAW -> {
                FConfigBC configBC = factory.getFConfigBC(FConfigBC.Preset.RAW);
                FMetaBC metaBC = meta != null ? factory.getFMetaBC() : null;

                double res = this.bc.analyze(this.bc.getResultsRaw(metaBC), configBC, metaBC);

                if (meta != null) {
                    meta.setExecutionTimeMillis(metaBC.getExecutionTimeMillis());
                    meta.setPythonRenderScript(metaBC.getPythonRenderScript());
                    meta.setRefData(metaBC.getRefData());
                }

                yield res;
            }
            case BC_BASELINE -> {
                FConfigBC configBC = factory.getFConfigBC(FConfigBC.Preset.BASELINE);
                FMetaBC metaBC = meta != null ? factory.getFMetaBC() : null;

                double res = this.bc.analyze(this.bc.getResultsOptimized(configBC, metaBC), configBC, metaBC);

                if (meta != null) {
                    meta.setExecutionTimeMillis(metaBC.getExecutionTimeMillis());
                    meta.setPythonRenderScript(metaBC.getPythonRenderScript());
                    meta.setRefData(metaBC.getRefData());
                }

                yield res;
            }
            case BC_OPTIMIZED -> {
                FConfigBC configBC = factory.getFConfigBC(FConfigBC.Preset.OPTIMIZED);
                FMetaBC metaBC = meta != null ? factory.getFMetaBC() : null;

                double res = this.bc.analyze(this.bc.getResultsOptimized(configBC, metaBC), configBC, metaBC);

                if (meta != null) {
                    meta.setExecutionTimeMillis(metaBC.getExecutionTimeMillis());
                    meta.setPythonRenderScript(metaBC.getPythonRenderScript());
                    meta.setRefData(metaBC.getRefData());
                }

                yield res;
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
                    0.9, null
            );
            case MR_FULL -> this.mr.analyze(
                    this.mr.getResults(RadiusOfGyration.SIMPLE_POLY, 1.1, false),
                    0.9, null
            );
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
