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
        FSphereProducer producer = factory.getFSphereProducer().setPresetEmpty();

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(0, 0, 0),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(0, 0, 0),
                        "The FSphere B value is erroneous"),
                () -> assertEquals(1, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(1, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
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
    @DisplayName("Preset radius")
    void presetInRange() {
        FSphereProducer producer = factory.getFSphereProducer().setPresetRndRadius(epsilon, 0.001);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(Math.abs(resultA.getRadius()) < 0.01,
                        "Radius A is incorrect"),
                () -> assertTrue(Math.abs(resultB.getRadius()) < 0.01,
                        "Radius B is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }
}
