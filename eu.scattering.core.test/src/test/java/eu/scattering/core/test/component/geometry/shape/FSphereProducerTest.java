package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FSphereProducer")
public class FSphereProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FSphereProducer producer = factory.getFSphereProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        FSphereProducer producer = factory.getFSphereProducer();

        AtomicInteger radius = new AtomicInteger(1);
        producer.withCustomRule((factory) -> factory.getFSphere(radius.getAndIncrement()), 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.getRefCenter().isExact(0, 0, 0),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(0, 0, 0),
                        "The FSphere B value is erroneous"),
                () -> assertEquals(1, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(2, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 1)
                .withCustomRule((factory) -> factory.getFSphere(2), 3);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getRadius() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate")
    void iterate() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((fSphere) -> factory.getFSphere(1), 5)
                .withCustomRule((fSphere) -> factory.getFSphere(2), 10)
                .withCustomRule((fSphere) -> factory.getFSphere(3), 15);

        int qRadius1 = 0;
        int qRadius2 = 0;
        int qRadius3 = 0;

        int i = 0;
        for (FSphere fSphere : producer) {

            if (fSphere.getIndex() != i++) {
                throw new IllegalStateException("The index is erroneous");
            }

            if (fSphere.getRadius() == 1) {
                qRadius1++;
            } else if (fSphere.getRadius() == 2) {
                qRadius2++;
            } else if (fSphere.getRadius() == 3) {
                qRadius3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals( 5, qRadius1, "Distribution 1 is erroneous");
        assertEquals(10, qRadius2, "Distribution 2 is erroneous");
        assertEquals(15, qRadius3, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Stream")
    void stream() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((fSphere) -> factory.getFSphere(1), 1)
                .withCustomRule((fSphere) -> factory.getFSphere(2), 1);

        List<FSphere> list = producer.stream().limit(100).collect(Collectors.toList());

        Assertions.assertAll("Validate values",
                () -> assertTrue(list.stream().anyMatch(e -> e.getRadius() == 1),
                        "The distribution is erroneous"),
                () -> assertTrue(list.stream().anyMatch(e -> e.getRadius() == 2),
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Preset fixed radius")
    void presetFixRadius() {
        FSphereProducer producer = factory.getFSphereProducer()
                .withFixedRadius("TiO2", 5, 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(0, 0, 0),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(0, 0, 0),
                        "The FSphere B value is erroneous"),
                () -> assertEquals(5, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(5, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertEquals("TiO2", resultA.getTag(),
                        "Tag A is incorrect"),
                () -> assertEquals("TiO2", resultB.getTag(),
                        "Tag B is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset random radius")
    void presetRndRadius() {
        FSphereProducer producer = factory.getFSphereProducer()
                .withRandomRadius("TiO2", epsilon, 0.001, 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(Math.abs(resultA.getRadius()) < 0.01,
                        "Radius A is incorrect"),
                () -> assertTrue(Math.abs(resultB.getRadius()) < 0.01,
                        "Radius B is incorrect"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertEquals("TiO2", resultA.getTag(),
                        "Tag A is incorrect"),
                () -> assertEquals("TiO2", resultB.getTag(),
                        "Tag B is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }
}
