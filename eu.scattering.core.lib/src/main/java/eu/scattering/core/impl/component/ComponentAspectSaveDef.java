package eu.scattering.core.impl.component;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.ComponentAspectSave;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.component.aggregate.monitor.pc.module.FMonitorPCRadiusOfGyration;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.FPlotMeta;
import eu.scattering.core.design.statistics.construct.plot.FPlotMetaGlobal;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import eu.scattering.core.impl.component.aggregate.save.ExBasicDef;
import eu.scattering.core.impl.component.aggregate.save.ExFlageDef;
import eu.scattering.core.impl.component.aggregate.save.ExNetGenDef;
import eu.scattering.core.impl.component.aggregate.save.ExPovRayDef;

import java.util.Locale;

public class ComponentAspectSaveDef implements ComponentAspectSave {
    private final ScatFactory factory;

    private ComponentAspectSaveDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static ComponentAspectSave create(ScatFactory factory) {

       return new ComponentAspectSaveDef(factory);
    }

    //--------------------------------------------------

    @Override
    public String toCLI(FPoint fPoint) {

        return "[" + fPoint.getX() + "," + fPoint.getY() + "," + fPoint.getZ() + "]";
    }

    @Override
    public String toCLI(FVector fVector) {

        return "[" + toCLI(fVector.getRefBase()) + "," + toCLI(fVector.getRefHead()) + "]";
    }

    //--------------------------------------------------

    @Override
    public void toJSON(FAggregate aggregate, StringBuilder builder) {

        builder.append(aggregate.toJSON().toString());
    }

    @Override
    public void toBasic(FAggregate aggregate, ExBasic preset, StringBuilder builder) {

        ExBasicDef.core(aggregate, preset, builder);
    }

    @Override
    public void toFLAGE(FAggregate aggregate, StringBuilder builder) {

        ExFlageDef.core(aggregate, builder);
    }

    @Override
    public void toNGSolve(FAggregate aggregate, StringBuilder builder) {

        ExNetGenDef.core(aggregate, builder);
    }

    @Override
    public void toPovRay(FAggregate aggregate, ExPovRay preset, StringBuilder builder) {

        ExPovRayDef.core(aggregate, preset, builder);
    }

    //--------------------------------------------------

    @Override
    public String toChart(FMonitorCCRadiusOfGyration monitor) {
        return "";
    }

    @Override
    public String toChart(FMonitorPCRadiusOfGyration monitor) {
        FPlot results = monitor.getRefFPlot();

        results.swapXY();
        results.mutateX(FStat::ln);
        results.mutateY(FStat::ln);

        FPoly regression = results.reg().fitSlope((int) (results.size() * 0.9));

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
}
