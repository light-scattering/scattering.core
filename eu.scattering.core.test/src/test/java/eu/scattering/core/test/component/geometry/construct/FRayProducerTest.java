package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.ray.FRayProducer;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
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

        FRay resultA = producer.produce();
        FRay resultB = producer.produce();

        Assertions.assertAll("Validate FRay values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The FRay A value is erroneous"),
                () -> assertTrue(resultB.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The FRay B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
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
                .setConfig((fRay) -> fRay.set(
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
    @DisplayName("Preset unit X")
    void presetUnitX() {
        FRayProducer producer = factory.getFRayProducer().setPresetOX();

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
    @DisplayName("Preset unit Y")
    void presetUnitY() {
        FRayProducer producer = factory.getFRayProducer().setPresetOY();

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
    @DisplayName("Preset unit Z")
    void presetUnitZ() {
        FRayProducer producer = factory.getFRayProducer().setPresetOZ();

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
    @DisplayName("Preset fixed point")
    void presetFixedPoint() {
        FPos3D fPos3D = factory.getFPos3D(1, 2, 3);
        FRayProducer producer = factory.getFRayProducer().setPresetFixedPoint(fPos3D);

        FRay resultA = producer.produce();
        FRay resultB = producer.produce();

        Assertions.assertAll("Validate FRay values",
                () -> assertTrue(resultA.isPartOf(factory.getFPoint(1, 2, 3)),
                        "The point should be a part of the FRay"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
