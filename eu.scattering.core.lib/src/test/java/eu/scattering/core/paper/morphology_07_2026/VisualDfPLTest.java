package eu.scattering.core.paper.morphology_07_2026;

import eu.scattering.core.design.component.aggregate.config.df.kinetic.cc.FConfigCCPL;
import eu.scattering.core.design.component.aggregate.config.df.kinetic.pc.FConfigPCPL;
import eu.scattering.core.design.component.aggregate.meta.df.kinetic.cc.FMetaCCPL;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

//@Disabled
@DisplayName("Paper - PL Visual")
public class VisualDfPLTest {

    @Test
    @Tag("sandbox")
    @DisplayName("PCPL - Image")
    void visualPCPL() {
        int size = 10000;
        double df = 1.8;
        double kf = 1.3;
        double r = 1;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

        var fMonitor = factory.getFMonitorContext().pc().radiusOfGyration(RadiusOfGyration.DEDICATED_FILIPPOV);

        var fModel = factory.getFModelContext().pc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);
        fModel.addStepMonitor(fMonitor);

        fModel.build();

        var fMeta = factory.getFMetaPCPL();

        var dim = fMonitor.getPowerLawDimension(FConfigPCPL.Preset.WINDOW, fMeta);

        String plot = fMeta.getPythonRenderScript();

        assertEquals(1.8, dim, 0.25);
        assertFalse(plot.isEmpty());

        String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

        assertFalse(model.isEmpty());

        String geometry = factory.getSaveAspect().getComponentContext().toBasic(fAggregate, ExBasic.MULTISPHERE);

        assertFalse(geometry.isEmpty());
    }

    @Test
    @Tag("sandbox")
    @DisplayName("CCPL - Image")
    void visualCCPL() {
        int size = 10000;
        double df = 1.8;
        double kf = 1.3;
        double r = 1;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

        var fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.DEDICATED_FILIPPOV);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);
        fModel.addStepMonitor(fMonitor);

        fModel.build();

        var fMeta = factory.getFMetaCCPL();

        double dim = fMonitor.getPowerLawDimension(FConfigCCPL.Preset.DROP, fMeta);

        String plotA = fMeta.getPythonRenderScript(FMetaCCPL.Plot.PARSED);
        String plotB = fMeta.getPythonRenderScript(FMetaCCPL.Plot.RAW);

        assertEquals(1.8, dim, 0.25);
        assertFalse(plotA.isEmpty());
        assertFalse(plotB.isEmpty());

        String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

        assertFalse(model.isEmpty());

        String geometry = factory.getSaveAspect().getComponentContext().toBasic(fAggregate, ExBasic.MULTISPHERE);

        assertFalse(geometry.isEmpty());
    }
}
