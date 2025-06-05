package eu.scattering.core.test.component.geometry.container;

import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.container.assembly.FAssemblyProducer;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FAssemblyProducer")
public class FAssemblyProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FAssemblyProducer<?> producer = factory.getFAssemblyProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        AtomicInteger length = new AtomicInteger(1);

        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer.withCustomRule((factoryLocal) -> {
            int lengthCurrent = length.getAndIncrement();

            FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();

            fAssembly.register(factory.getFSphere(lengthCurrent, lengthCurrent, lengthCurrent));

            return fAssembly;
        }, 1);

        FAssembly<FSphere> resultA = producer.produce();
        FAssembly<FSphere> resultB = producer.produce();

        Assertions.assertAll("Validate FAssembly values",
                () -> assertTrue(resultA.getGeometries().get(0).getRefCenter().isExact(1, 1, 1),
                        "The FAssembly A value is erroneous"),
                () -> assertTrue(resultB.getGeometries().get(0).getRefCenter().isExact(2, 2, 2),
                        "The FAssembly B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce custom (simple)")
    void produceCustomSimple() {
        AtomicInteger length = new AtomicInteger(1);

        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer.withCustomRule((factoryLocal) -> {
            int lengthCurrent = length.getAndIncrement();

            FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();

            fAssembly.register(factory.getFSphere(lengthCurrent, lengthCurrent, lengthCurrent));

            return fAssembly;
        });

        FAssembly<FSphere> resultA = producer.produce();
        FAssembly<FSphere> resultB = producer.produce();

        Assertions.assertAll("Validate FAssembly values",
                () -> assertTrue(resultA.getGeometries().get(0).getRefCenter().isExact(1, 1, 1),
                        "The FAssembly A value is erroneous"),
                () -> assertTrue(resultB.getGeometries().get(0).getRefCenter().isExact(2, 2, 2),
                        "The FAssembly B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(1));
                    return fAssembly;
                }, 1)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(2));
                    return fAssembly;
                }, 3);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getGeometries().get(0).getRadius() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FAssembly values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate")
    void iterate() {
        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(1));
                    return fAssembly;
                }, 5)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(2));
                    return fAssembly;
                }, 10)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(3));
                    return fAssembly;
                }, 15);

        int qValue1 = 0;
        int qValue2 = 0;
        int qValue3 = 0;

        for (FAssembly<FSphere> fAssembly : producer) {

            if (fAssembly.getGeometries().get(0).getRadius() == 1) {
                qValue1++;
            } else if (fAssembly.getGeometries().get(0).getRadius() == 2) {
                qValue2++;
            } else if (fAssembly.getGeometries().get(0).getRadius() == 3) {
                qValue3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals( 5, qValue1, "Distribution 1 is erroneous");
        assertEquals(10, qValue2, "Distribution 2 is erroneous");
        assertEquals(15, qValue3, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Stream")
    void stream() {
        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(1));
                    return fAssembly;
                }, 1)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(2));
                    return fAssembly;
                }, 5);

        List<FAssembly<FSphere>> list = producer.stream().limit(100).collect(Collectors.toList());

        Assertions.assertAll("Validate values",
                () -> assertTrue(list.stream().anyMatch(e -> e.getGeometries().get(0).getRadius() == 1),
                        "The distribution is erroneous"),
                () -> assertTrue(list.stream().anyMatch(e -> e.getGeometries().get(0).getRadius() == 2),
                        "The distribution is erroneous")
        );
    }
}
