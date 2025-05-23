package eu.scattering.core.test.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
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
        FVectorProducer producer = factory.getFVectorProducer().setPresetEmpty();

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 0, 0, 0),
                        "The FVector A value is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0, 0, 0, 0, 0),
                        "The FVector B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
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
    @DisplayName("Preset unit X")
    void presetUnitX() {
        FVectorProducer producer = factory.getFVectorProducer().setPresetUnitX();

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
        FVectorProducer producer = factory.getFVectorProducer().setPresetUnitY();

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
    @DisplayName("Preset unit Z")
    void presetUnitZ() {
        FVectorProducer producer = factory.getFVectorProducer().setPresetUnitZ();

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
        FPos3D base = factory.getFPos3D(1, 2, 3);
        FPairPos3D range = factory.getFPairPos3D(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01);
        FVectorProducer producer = factory.getFVectorProducer().setPresetInRange(base, range);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(Math.abs(resultA.getHeadX() - 1) < 0.01,
                        "Value X is incorrect"),
                () -> assertTrue(Math.abs(resultA.getHeadY() - 2) < 0.01,
                        "Value Y is incorrect"),
                () -> assertTrue(Math.abs(resultA.getHeadZ() - 3) < 0.01,
                        "Value Z is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset in sphere")
    void presetInSphere() {
        FPos3D base = factory.getFPos3D(1, 2, 3);
        FVectorProducer producer = factory.getFVectorProducer().setPresetInSphere(base, 0.01);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getMagnitude() < 0.01,
                        "Position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(1, 2, 3),
                        "Value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset on sphere")
    void presetOnSphere() {
        FPos3D base = factory.getFPos3D(1, 2, 3);
        FVectorProducer producer = factory.getFVectorProducer().setPresetOnSphere(base, 0.01);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertEquals(resultA.getMagnitude(), 0.01,
                        epsilon, "The position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(1, 2, 3),
                        "The value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }
}
