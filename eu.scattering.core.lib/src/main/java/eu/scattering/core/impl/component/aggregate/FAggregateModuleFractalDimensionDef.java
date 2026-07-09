package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.FConfigBC;
import eu.scattering.core.design.component.aggregate.config.df.FConfigDC;
import eu.scattering.core.design.component.aggregate.meta.df.FMetaDF;
import eu.scattering.core.design.component.aggregate.meta.df.FMetaBC;
import eu.scattering.core.design.component.aggregate.meta.df.FMetaDC;
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

    protected FPlot getDensityCorrelationFunction(FConfigDC config) {

        return this.dc.getResults(config);
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
            case DC_RESTRICTED -> {
                FConfigDC configDC = factory.getFConfigDC(FConfigDC.Preset.RESTRICTED);
                FMetaDC metaDC = meta != null ? factory.getFMetaDC() : null;

                double res = this.dc.analyze(this.dc.getResults(configDC, metaDC), configDC, metaDC);

                if (meta != null) {
                    meta.setExecutionTimeMillis(metaDC.getExecutionTimeMillis());
                    meta.setPythonRenderScript(metaDC.getPythonRenderScript());
                    meta.setRefData(metaDC.getRefData());
                }

                yield res;
            }
            case DC_FULL -> {
                FConfigDC configDC = factory.getFConfigDC(FConfigDC.Preset.FULL);
                FMetaDC metaDC = meta != null ? factory.getFMetaDC() : null;

                double res = this.dc.analyze(this.dc.getResults(configDC, metaDC), configDC, metaDC);

                if (meta != null) {
                    meta.setExecutionTimeMillis(metaDC.getExecutionTimeMillis());
                    meta.setPythonRenderScript(metaDC.getPythonRenderScript());
                    meta.setRefData(metaDC.getRefData());
                }

                yield res;
            }
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

    protected double getFractalDimensionDensityCorrelation(FConfigDC config) {

        return this.dc.analyze(this.dc.getResults(config, null), config, null);
    }

    protected double getFractalDimensionDensityCorrelation(FConfigDC config, FMetaDC meta) {

        return this.dc.analyze(this.dc.getResults(config, meta), config, meta);
    }

    protected double getFractalDimensionMassRadius(double window, RadiusOfGyration method, double stepFactor, boolean rangeLimit) {

        return this.mr.analyze(this.mr.getResults(method, stepFactor, rangeLimit), window, null);
    }
}
