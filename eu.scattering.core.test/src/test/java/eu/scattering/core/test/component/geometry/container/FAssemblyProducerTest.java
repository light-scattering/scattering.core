package eu.scattering.core.test.component.geometry.container;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.container.assembly.FAssemblyProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@Timeout(5)
@DisplayName("FAssemblyProducer")
public class FAssemblyProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FAssemblyProducer<FPoint> producer = factory.getFAssemblyProducer();

        FAssembly<FPoint> resultA = producer.produce();
        FAssembly<FPoint> resultB = producer.produce();

        Assertions.assertAll("Validate FAssembly values",
                () -> assertEquals(resultA, resultB,
                        "Elements should be equal"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
