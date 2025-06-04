package eu.scattering.core.test.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
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
@DisplayName("FPointProducer")
public class FPointProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FPointProducer producer = factory.getFPointProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        FPointProducer producer = factory.getFPointProducer();

        AtomicInteger length = new AtomicInteger(1);
        producer.withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            return factory.getFPoint(lengthCurrent, lengthCurrent, lengthCurrent);
        }, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.isExact(1, 1, 1),
                        "The FPoint A value is erroneous"),
                () -> assertTrue(resultB.isExact(2, 2, 2),
                        "The FPoint B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FPointProducer producer = factory.getFPointProducer();

        producer
                .withCustomRule((factory) -> factory.getFPoint().setX(1), 1)
                .withCustomRule((factory) -> factory.getFPoint().setX(2), 2);

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
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate")
    void iterate() {
        FPointProducer producer = factory.getFPointProducer();

        producer
                .withCustomRule((factory) -> factory.getFPoint().setX(1), 5)
                .withCustomRule((factory) -> factory.getFPoint().setX(2), 10)
                .withCustomRule((factory) -> factory.getFPoint().setX(3), 15);

        int qLength1 = 0;
        int qLength2 = 0;
        int qLength3 = 0;

        for (FPoint fPoint : producer) {

            if (fPoint.getX() == 1) {
                qLength1++;
            } else if (fPoint.getX() == 2) {
                qLength2++;
            } else if (fPoint.getX() == 3) {
                qLength3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals( 5, qLength1, "Distribution 1 is erroneous");
        assertEquals(10, qLength2, "Distribution 2 is erroneous");
        assertEquals(15, qLength3, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Stream")
    void stream() {
        FPointProducer producer = factory.getFPointProducer();

        producer
                .withCustomRule((factory) -> factory.getFPoint().setX(1), 1)
                .withCustomRule((factory) -> factory.getFPoint().setX(2), 1);

        List<FPoint> list = producer.stream().limit(100).collect(Collectors.toList());

        Assertions.assertAll("Validate values",
                () -> assertTrue(list.stream().anyMatch(e -> e.getX() == 1),
                        "The distribution is erroneous"),
                () -> assertTrue(list.stream().anyMatch(e -> e.getX() == 2),
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Preset zero")
    void presetZero() {
        FPointProducer producer = factory.getFPointProducer()
                .withZero(1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.isExact(0, 0, 0),
                        "The FPoint A value is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0, 0),
                        "The FPoint B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset in range")
    void presetInRange() {
        FPairPos3D range = factory.getFPairPos3D(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01);
        FPointProducer producer = factory.getFPointProducer()
                .withInRange(range, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(Math.abs(resultA.getX()) < 0.01,
                        "Value X is incorrect"),
                () -> assertTrue(Math.abs(resultA.getY()) < 0.01,
                        "Value Y is incorrect"),
                () -> assertTrue(Math.abs(resultA.getZ()) < 0.01,
                        "Value Z is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset in sphere")
    void presetInSphere() {
        FPointProducer producer = factory.getFPointProducer()
                .withInsideSphere(0.01, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.getMagnitude() < 0.01,
                        "Position is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset on sphere")
    void presetOnSphere() {
        FPointProducer producer = factory.getFPointProducer()
                .withOnSphere(0.01, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(resultA.getMagnitude(), 0.01,
                        epsilon, "Position is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }
}
