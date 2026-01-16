package eu.scattering.core.test.component.aggregate.monitor;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import eu.scattering.core.design.component.aggregate.model.cc.dlca.FModelCCDLCA;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelPCDLA;
import eu.scattering.core.design.type.Dimension;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FMonitor PC")
public class FMonitorCCTest {

    @Nested
    @Tag("Construct")
    @DisplayName("FMonitor construct")
    class FMonitorConstructTest {

        @Disabled
        @Test
        @DisplayName("Radius of gyration - Ballistic 3D")
        void rogMonodisperseBallistic3D() {
            int quantity = 1000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(quantity, 10, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);

            fModel.build();

            String model = factory.getExportAspect().getFAggregateContext().toNGSolve(fAggregate);

            assertEquals(0, fAggregate.getLinearOverlapFactor(), 1E-4);
        }

        @Disabled
        @Test
        @DisplayName("Radius of gyration - Ballistic 2D")
        void rogMonodisperseBallistic2D() {
            int quantity = 1000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(quantity, 10, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(Dimension.D2, fAggregate);

            fModel.build();

            String model = factory.getExportAspect().getFAggregateContext().toNGSolve(fAggregate);

            assertEquals(0, fAggregate.getLinearOverlapFactor(), 1E-4);
        }

//        @Disabled
        @Test
        @DisplayName("Radius of gyration - RLCA 3D")
        void rogMonodisperseRLCA3D() {
            int quantity = 1000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(quantity,  10, 1);

            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(fAggregate);
            fModel.setInternalSpawn(true);

            fModel.build();

            String model = factory.getExportAspect().getFAggregateContext().toNGSolve(fAggregate);

            assertEquals(0, fAggregate.getLinearOverlapFactor(), 1E-4);
        }

//        @Disabled
        @Test
        @DisplayName("Radius of gyration - RLCA 2D")
        void rogMonodisperseRLCA2D() {
            int quantity = 1000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(quantity,  10, 1);

            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(Dimension.D2, fAggregate);
            fModel.setInternalSpawn(true);

            fModel.build();

            String model = factory.getExportAspect().getFAggregateContext().toNGSolve(fAggregate);

            assertEquals(0, fAggregate.getLinearOverlapFactor(), 1E-4);
        }

        @Disabled
        @Test
        @DisplayName("Radius of gyration - Tunable 3D")
        void rogMonodisperseTunable3D() {
            int quantity = 1000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(quantity,  10, 0.01);

            FModelCC fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.2, 2);

            fModel.build();

            String model = factory.getExportAspect().getFAggregateContext().toNGSolve(fAggregate);

            assertEquals(0, fAggregate.getLinearOverlapFactor(), 1E-4);
        }

        @Disabled
        @Test
        @DisplayName("Radius of gyration - Tunable 2D")
        void rogMonodisperseTunable2D() {
            int quantity = 3000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(quantity,  10, 0.5);

            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.8, 1.4);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            fModel.build();

            String model = factory.getExportAspect().getFAggregateContext().toNGSolve(fAggregate);

            assertEquals(0, fAggregate.getLinearOverlapFactor(), 1E-4);
        }
    }
}
