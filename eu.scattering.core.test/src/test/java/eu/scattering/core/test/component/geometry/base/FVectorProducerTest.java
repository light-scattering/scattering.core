package eu.scattering.core.test.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

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
        producer.setConfig((fVector) -> {
            int lengthCurrent = length.getAndIncrement();

            fVector.set(-lengthCurrent, -lengthCurrent, -lengthCurrent, lengthCurrent, lengthCurrent, lengthCurrent);

            return fVector;
        });

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
                .addConfig((fVector) -> fVector.setHeadX(1), 0.25)
                .addConfig((fVector) -> fVector.setHeadX(2), 0.75);

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
    @DisplayName("Preset set unit X")
    void presetUnitX() {
        FVectorProducer producer = factory.getFVectorProducer();
        producer.setPresetUnitX();

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
    @DisplayName("Preset add unit X")
    void presetAddUnitX() {
        FVectorProducer producer = factory.getFVectorProducer().addPresetUnitX(1);

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
    @DisplayName("Preset set unit Y")
    void presetSetUnitY() {
        FVectorProducer producer = factory.getFVectorProducer();
        producer.setPresetUnitY();

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
    @DisplayName("Preset add unit Y")
    void presetAddUnitY() {
        FVectorProducer producer = factory.getFVectorProducer().addPresetUnitY(1);

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
    @DisplayName("Preset set unit Z")
    void presetSetUnitZ() {
        FVectorProducer producer = factory.getFVectorProducer();
        producer.setPresetUnitZ();

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
    @DisplayName("Preset add unit Z")
    void presetAddUnitZ() {
        FVectorProducer producer = factory.getFVectorProducer().addPresetUnitZ(1);

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
    @DisplayName("Preset set in range")
    void presetSetInRange() {
        FPairPos3D range = factory.getFPairPos3D(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01);
        FVectorProducer producer = factory.getFVectorProducer();
        producer.setPresetRange(range);

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
    @DisplayName("Preset add in range")
    void presetAddInRange() {
        FPairPos3D range = factory.getFPairPos3D(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01);
        FVectorProducer producer = factory.getFVectorProducer().addPresetInRange(range, 1);

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
    @DisplayName("Preset set in sphere")
    void presetSetInSphere() {
        FVectorProducer producer = factory.getFVectorProducer();
        producer.setPresetInSphere(0.01);

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
    @DisplayName("Preset add in sphere")
    void presetAddInSphere() {
        FVectorProducer producer = factory.getFVectorProducer().addPresetInSphere(0.01, 1);

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
    @DisplayName("Preset set on sphere")
    void presetSetOnSphere() {
        FVectorProducer producer = factory.getFVectorProducer();
        producer.setPresetOnSphere(0.01);

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

    @Test
    @DisplayName("Preset on sphere")
    void presetOnSphere() {
        FVectorProducer producer = factory.getFVectorProducer().addPresetOnSphere(0.01, 1);

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
