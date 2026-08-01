package eu.scattering.core.paper.man_07_2026;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.kinetic.cc.FConfigCCPL;
import eu.scattering.core.design.component.aggregate.config.df.kinetic.pc.FConfigPCPL;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigBC;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigDC;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigMR;
import eu.scattering.core.design.component.aggregate.meta.df.kinetic.cc.FMetaCCPL;
import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaBC;
import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaDC;
import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaMR;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
@Tag("sandbox")
@DisplayName("Paper - Morphology (Visual)")
public class DFVisualTest {

    @Nested
    class PL {

        @Test
        @DisplayName("PCPL - Image")
        void visualPCPL() {
            int size = 10000;
            double df = 1.8;
            double kf = 1.3;
            double r = 1;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

            var fMonitor = factory.getFMonitorContext().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);

            var fModel = factory.getFModelContext().pc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);
            fModel.addStepMonitor(fMonitor);

            fModel.build();

            var fMeta = factory.getFMetaPCPL();

            var dimension = fMonitor.getPowerLawDimension(FConfigPCPL.Preset.WINDOW, fMeta);

            String plot = fMeta.getPythonRenderScript();

            assertEquals(1.8, dimension, 0.25);
            assertFalse(plot.isEmpty());

            String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

            assertFalse(model.isEmpty());

            String geometry = factory.getSaveAspect().getComponentContext().toBasic(fAggregate, ExBasic.MULTISPHERE);

            assertFalse(geometry.isEmpty());
        }

        @Test
        @DisplayName("CCPL - Image")
        void visualCCPL() {
            int size = 10000;
            double df = 1.8;
            double kf = 1.3;
            double r = 1;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

            var fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);
            fModel.addStepMonitor(fMonitor);

            fModel.build();

            var fMeta = factory.getFMetaCCPL();

            double dimension = fMonitor.getPowerLawDimension(FConfigCCPL.Preset.WINDOW, fMeta);

            String plotA = fMeta.getPythonRenderScript(FMetaCCPL.Plot.PARSED);
            String plotB = fMeta.getPythonRenderScript(FMetaCCPL.Plot.RAW);

            assertEquals(1.8, dimension, 0.25);
            assertFalse(plotA.isEmpty());
            assertFalse(plotB.isEmpty());

            String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

            assertFalse(model.isEmpty());

            String geometry = factory.getSaveAspect().getComponentContext().toBasic(fAggregate, ExBasic.MULTISPHERE);

            assertFalse(geometry.isEmpty());
        }
    }

    @Nested
    class DC {

        @Test
        @DisplayName("DC - Image")
        void visualDC() {
            int size = 10000;
            double df = 1.8;
            double kf = 1.3;
            double r = 1;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            fAggregate.pca();

            String geometry = factory.getSaveAspect().getComponentContext().toBasic(fAggregate, ExBasic.MULTISPHERE);

            assertFalse(geometry.isEmpty());

            String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.REFERENCE);

            assertFalse(model.isEmpty());

            FConfigDC config = factory.getFConfigDC(FConfigDC.Preset.FULL);
            FMetaDC meta = factory.getFMetaDC();

            double dimFull = fAggregate.getFractalDimension(config, meta);

            assertNotNull(meta.getPythonRenderScript());
            assertTrue(dimFull > 0);

            config.setRestricted(true);

            double dimRestricted = fAggregate.getFractalDimension(config, meta);

            assertNotNull(meta.getPythonRenderScript());
            assertTrue(dimRestricted > 0);
        }
    }

    @Nested
    class MR {

        @Test
        @DisplayName("MR - Image")
        void visualMR() {
            int size = 10000;
            double df = 1.8;
            double kf = 1.3;
            double r = 1;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            fAggregate.pca();

            String geometry = factory.getSaveAspect().getComponentContext().toBasic(fAggregate, ExBasic.MULTISPHERE);

            assertFalse(geometry.isEmpty());

            String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.REFERENCE);

            assertFalse(model.isEmpty());

            FConfigMR config = factory.getFConfigMR(FConfigMR.Preset.FULL);
            FMetaMR meta = factory.getFMetaMR();

            double dimFull = fAggregate.getFractalDimension(config, meta);

            assertNotNull(meta.getPythonRenderScript());
            assertTrue(dimFull > 0);

            config.setRestricted(true);

            double dimRestricted = fAggregate.getFractalDimension(config, meta);

            assertNotNull(meta.getPythonRenderScript());
            assertTrue(dimRestricted > 0);
        }
    }

    @Nested
    class BC {

        @Test
        @DisplayName("BC - Image")
        void visio() {
            int size = 10000;
            double df = 1.8;
            double kf = 1.3;
            double r = 1;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            fAggregate.setSphereCenterAsZero(1000);

            FPos3D length = fAggregate.getLength();
            double radius = fAggregate.getRadiusFrom(0, 0, 0);
            double diameter = fAggregate.getDiameter();
            double magnitude = radius / r;

            assertTrue(length.getD0() > 0 && length.getD1() > 0 && length.getD2() > 0);
            assertTrue(radius > 0);
            assertTrue(diameter > 0);
            assertTrue(magnitude > 0);

            String modelA = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

            assertFalse(modelA.isEmpty());

            String modelB = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOX_COUNTING);

            assertFalse(modelB.isEmpty());

            String geometry = factory.getSaveAspect().getComponentContext().toBasic(fAggregate, ExBasic.MULTISPHERE);

            assertFalse(geometry.isEmpty());

            FConfigBC config = factory.getFConfigBC(FConfigBC.Preset.BASELINE);
            FMetaBC meta = factory.getFMetaBC();

            double results = fAggregate.getFractalDimension(config, meta);

            assertTrue(results > 1);
            assertFalse(meta.getPythonRenderScript().isEmpty());
        }
    }

    @Nested
    class Geometry {

        @Test
        @DisplayName("Geometry - Basic")
        void basic() {
            FAggregate fAggregate1d = factory.getFAggregateContext().geometry().d1(50);
            FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2(25, 25);
            FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3(20, 20, 20);

            int particles1d = fAggregate1d.size();
            int particles2d = fAggregate2d.size();
            int particles3d = fAggregate3d.size();

            assertTrue(particles1d > 0);
            assertTrue(particles2d > 0);
            assertTrue(particles3d > 0);

            String fModel1d = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate1d, ExPovRay.BOUNDARY);
            assertFalse(fModel1d.isEmpty());

            String fModel2d = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate2d, ExPovRay.BOUNDARY);
            assertFalse(fModel2d.isEmpty());

            String fModel3d = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate3d, ExPovRay.BOUNDARY);
            assertFalse(fModel3d.isEmpty());
        }

        @Test
        @DisplayName("Geometry - Hexagonal")
        void hexagonal() {
            FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2Hex(30, 1);
            FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3Hex(20, 1);

            int particles2d = fAggregate2d.size();
            int particles3d = fAggregate3d.size();

            assertTrue(particles2d > 0);
            assertTrue(particles3d > 0);

            String fModel2d = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate2d, ExPovRay.BOUNDARY);
            assertFalse(fModel2d.isEmpty());

            String fModel3d = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate3d, ExPovRay.BOUNDARY);
            assertFalse(fModel3d.isEmpty());
        }
    }

    @Nested
    class Morphology {

        @Test
        @DisplayName("Morphology - Df = 1.4")
        void df14() {
            int size = 10000;
            double df = 1.4;
            double kf = 1.5;
            double r = 1;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

            assertFalse(model.isEmpty());
        }

        @Test
        @DisplayName("Morphology - Df = 1.8")
        void df18() {
            int size = 10000;
            double df = 1.8;
            double kf = 1.3;
            double r = 1;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

            assertFalse(model.isEmpty());
        }

        @Test
        @DisplayName("Morphology - Df = 2.2")
        void df22() {
            int size = 10000;
            double df = 2.2;
            double kf = 0.8;
            double r = 1;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

            assertFalse(model.isEmpty());
        }
    }
}
