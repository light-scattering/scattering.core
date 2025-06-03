package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

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

        AtomicInteger length = new AtomicInteger(1);
        producer.setConfig((fSphere) -> {
            int radiusCurrent = length.getAndIncrement();

            fSphere.setRadius(radiusCurrent);

            return fSphere;
        });

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
                .addConfig((fSphere) -> fSphere.setRadius(1), 0.25)
                .addConfig((fSphere) -> fSphere.setRadius(2), 0.75);

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
    @DisplayName("Preset set fixed radius")
    void presetSetFixRadius() {
        FSphereProducer producer = factory.getFSphereProducer();
        producer.setPresetFixRadius("TiO2", 5);

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
                () -> assertEquals(0, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(1, resultB.getIndex(),
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
    @DisplayName("Preset add fixed radius")
    void presetAddFixRadius() {
        FSphereProducer producer = factory.getFSphereProducer()
                .addPresetFixRadius("TiO2", 5, 1);

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
                () -> assertEquals(0, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(1, resultB.getIndex(),
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
    @DisplayName("Preset set random radius")
    void presetSetRndRadius() {
        FSphereProducer producer = factory.getFSphereProducer();
        producer.setPresetRndRadius("TiO2", epsilon, 0.001);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(Math.abs(resultA.getRadius()) < 0.01,
                        "Radius A is incorrect"),
                () -> assertTrue(Math.abs(resultB.getRadius()) < 0.01,
                        "Radius B is incorrect"),
                () -> assertEquals(0, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertEquals("TiO2", resultA.getTag(),
                        "Tag A is incorrect"),
                () -> assertEquals("TiO2", resultB.getTag(),
                        "Tag B is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset add random radius")
    void presetAddRndRadius() {
        FSphereProducer producer = factory.getFSphereProducer()
                .addPresetRndRadius("TiO2", epsilon, 0.001, 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(Math.abs(resultA.getRadius()) < 0.01,
                        "Radius A is incorrect"),
                () -> assertTrue(Math.abs(resultB.getRadius()) < 0.01,
                        "Radius B is incorrect"),
                () -> assertEquals(0, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(1, resultB.getIndex(),
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
