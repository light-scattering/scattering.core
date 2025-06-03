package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.ray.FRayProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FRayProducer")
public class FRayProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FRayProducer producer = factory.getFRayProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        FRayProducer producer = factory.getFRayProducer();

        AtomicInteger length = new AtomicInteger(1);
        producer.setConfig((fRay) -> {
            int lengthCurrent = length.getAndIncrement();

            fRay.getRefOrigin().set(
                    lengthCurrent, lengthCurrent, lengthCurrent,
                    lengthCurrent, lengthCurrent, lengthCurrent
            );

            return fRay;
        });

        FRay resultA = producer.produce();
        FRay resultB = producer.produce();

        Assertions.assertAll("Validate FRay values",
                () -> assertTrue(resultA.getRefOrigin().isExact(1, 1, 1, 1, 1, 1),
                        "The FRay A value is erroneous"),
                () -> assertTrue(resultB.getRefOrigin().isExact(2, 2, 2, 2, 2, 2),
                        "The FRay B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FRayProducer producer = factory.getFRayProducer();

        producer
                .addConfig((fRay) -> fRay.set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 0.25)
                .addConfig((fRay) -> fRay.set(
                        factory.getFPairPos3D(0, 0, 0, 2, 0, 0)), 0.75);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getRefOrigin().getHeadX() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FRay values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Preset set unit X")
    void presetSetUnitX() {
        FRayProducer producer = factory.getFRayProducer();
        producer.setPresetOX();

        FRay resultA = producer.produce();
        FRay resultB = producer.produce();

        Assertions.assertAll("Validate FRay values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset add unit X")
    void presetAddUnitX() {
        FRayProducer producer = factory.getFRayProducer().addPresetOX(1);

        FRay resultA = producer.produce();
        FRay resultB = producer.produce();

        Assertions.assertAll("Validate FRay values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset set unit Y")
    void presetSetUnitY() {
        FRayProducer producer = factory.getFRayProducer();
        producer.setPresetOY();

        FRay resultA = producer.produce();
        FRay resultB = producer.produce();

        Assertions.assertAll("Validate FRay values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 1, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset add unit Y")
    void presetAddUnitY() {
        FRayProducer producer = factory.getFRayProducer().addPresetOY(1);

        FRay resultA = producer.produce();
        FRay resultB = producer.produce();

        Assertions.assertAll("Validate FRay values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 1, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset set unit Z")
    void presetSetUnitZ() {
        FRayProducer producer = factory.getFRayProducer();
        producer.setPresetOZ();

        FRay resultA = producer.produce();
        FRay resultB = producer.produce();

        Assertions.assertAll("Validate FRay values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset add unit Z")
    void presetAddUnitZ() {
        FRayProducer producer = factory.getFRayProducer().addPresetOZ(1);

        FRay resultA = producer.produce();
        FRay resultB = producer.produce();

        Assertions.assertAll("Validate FRay values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
