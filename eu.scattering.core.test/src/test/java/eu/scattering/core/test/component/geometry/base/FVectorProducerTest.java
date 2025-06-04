package eu.scattering.core.test.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
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
@DisplayName("FVectorProducer")
public class FVectorProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FVectorProducer producer = factory.getFVectorProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        FVectorProducer producer = factory.getFVectorProducer();

        AtomicInteger length = new AtomicInteger(1);
        producer.withCustomRule((fVector) -> {
            int lengthCurrent = length.getAndIncrement();

            fVector.set(-lengthCurrent, -lengthCurrent, -lengthCurrent, lengthCurrent, lengthCurrent, lengthCurrent);

            return fVector;
        }, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(-1, -1, -1, 1, 1, 1),
                        "The FVector A value is erroneous"),
                () -> assertTrue(resultB.isExact(-2, -2, -2, 2, 2, 2),
                        "The FVector B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FVectorProducer producer = factory.getFVectorProducer();

        producer
                .withCustomRule((fVector) -> fVector.setHeadX(1), 1)
                .withCustomRule((fVector) -> fVector.setHeadX(2), 3);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getHeadX() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate")
    void iterate() {
        FVectorProducer producer = factory.getFVectorProducer();

        producer
                .withCustomRule((fVector) -> fVector.setHeadX(1), 5)
                .withCustomRule((fVector) -> fVector.setHeadX(2), 10)
                .withCustomRule((fVector) -> fVector.setHeadX(3), 15);

        int qLength1 = 0;
        int qLength2 = 0;
        int qLength3 = 0;

        for (FVector fVector : producer) {

            if (fVector.getHeadX() == 1) {
                qLength1++;
            } else if (fVector.getHeadX() == 2) {
                qLength2++;
            } else if (fVector.getHeadX() == 3) {
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
        FVectorProducer producer = factory.getFVectorProducer();

        producer
                .withCustomRule((fVector) -> fVector.setHeadX(1), 1)
                .withCustomRule((fVector) -> fVector.setHeadX(2), 1);

        List<FVector> list = producer.stream().limit(100).collect(Collectors.toList());

        Assertions.assertAll("Validate values",
                () -> assertTrue(list.stream().anyMatch(e -> e.getHeadX() == 1),
                        "The distribution is erroneous"),
                () -> assertTrue(list.stream().anyMatch(e -> e.getHeadX() == 2),
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Preset unit X")
    void presetUnitX() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withUnitX(1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 1, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset unit Y")
    void presetUnitY() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withUnitY(1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 0, 1, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset  unit Z")
    void presetAddUnitZ() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withUnitZ(1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset in range")
    void presetInRange() {
        FPairPos3D range = factory.getFPairPos3D(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01);
        FVectorProducer producer = factory.getFVectorProducer()
                .withInRange(range, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getHeadX() < 0.01,
                        "Value X is incorrect"),
                () -> assertTrue(resultA.getHeadY() < 0.01,
                        "Value Y is incorrect"),
                () -> assertTrue(resultA.getHeadZ() < 0.01,
                        "Value Z is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset in sphere")
    void presetInSphere() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withInsideSphere(0.01, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getMagnitude() < 0.01,
                        "Position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(0, 0, 0),
                        "Value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset on sphere")
    void presetOnSphere() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withOnSphere(0.01, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertEquals(resultA.getMagnitude(), 0.01,
                        epsilon, "The position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(0, 0, 0),
                        "The value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }
}
