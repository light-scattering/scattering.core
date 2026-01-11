package eu.scattering.core.test.component.aggregate.monitor;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FMonitor PC")
public class FMonitorCCTest {

    @Nested
    @Tag("Construct")
    @DisplayName("FMonitor construct")
    class FMonitorConstructTest {

        @Test
        @DisplayName("Radius of gyration - Ballistic")
        void rogMonodisperseBallistic() {
            int quantity = 1000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(quantity, 10, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);

            fModel.build();

            String model = factory.getExportAspect().getFAggregateContext().toNGSolve(fAggregate);

            assertEquals(0, fAggregate.getLinearOverlapFactor(), 1E-4);
        }
    }
}
