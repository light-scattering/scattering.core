package eu.scattering.core.test.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPointProducer")
public class FPointProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FPointProducer producer = factory.getFPointProducer();

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.isExact(0, 0, 0), "The FPoint A is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0, 0), "The FPoint B is erroneous"),
                () -> assertNotSame(resultA, resultB, "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce default")
    void produceDefault() {
        FPointProducer producer = factory.getFPointProducer();

        AtomicInteger length = new AtomicInteger(1);
        producer.setConfig((fPoint) -> {
            int lengthCurrent = length.getAndIncrement();

            fPoint.set(lengthCurrent, lengthCurrent, lengthCurrent);

            return fPoint;
        });

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.isExact(1, 1, 1), "The FPoint A is erroneous"),
                () -> assertTrue(resultB.isExact(2, 2, 2), "The FPoint B is erroneous"),
                () -> assertNotSame(resultA, resultB, "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce modal")
    void produceModal() {
        FPointProducer producer = factory.getFPointProducer();

        producer.addConfig((fPoint) -> fPoint.setX(1), 0.25);
        producer.addConfig((fPoint) -> fPoint.setX(2), 0.75);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getX() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(countFinalA < countFinalB, "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Preset in range")
    void presetInRange() {
        FPairPos3D range = factory.getFPairPos3D(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01);
        FPointProducer producer = factory.getFPointProducer().setPresetInRange(range);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(Math.abs(resultA.getX()) < 0.01, "Value X is incorrect"),
                () -> assertTrue(Math.abs(resultA.getY()) < 0.01, "Value Y is incorrect"),
                () -> assertTrue(Math.abs(resultA.getZ()) < 0.01, "Value Z is incorrect"),
                () -> assertFalse(resultA.isExact(resultB), "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset in sphere")
    void presetInSphere() {
        FPointProducer producer = factory.getFPointProducer().setPresetInSphere(0.01);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.getMagnitude() < 0.01, "Position is incorrect"),
                () -> assertFalse(resultA.isExact(resultB), "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset on sphere")
    void presetOnSphere() {
        FPointProducer producer = factory.getFPointProducer().setPresetOnSphere(0.01);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(resultA.getMagnitude(), 0.01, epsilon, "Position is incorrect"),
                () -> assertFalse(resultA.isExact(resultB), "Values should be different")
        );
    }
}
