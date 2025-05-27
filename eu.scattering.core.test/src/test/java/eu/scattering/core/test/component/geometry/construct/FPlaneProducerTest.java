package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneProducer;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPlaneProducer")
public class FPlaneProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FPlaneProducer producer = factory.getFPlaneProducer().setPresetEmpty();

        FPlane resultA = producer.produce();
        FPlane resultB = producer.produce();

        Assertions.assertAll("Validate FPlane values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 0),
                        "The FPlane A value is erroneous"),
                () -> assertTrue(resultB.getRefOrigin().isExact(0, 0, 0, 0, 0, 0),
                        "The FPlane B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        FPlaneProducer producer = factory.getFPlaneProducer();

        AtomicInteger length = new AtomicInteger(1);
        producer.setConfig((fPlane) -> {
            int lengthCurrent = length.getAndIncrement();

            fPlane.getRefOrigin().set(
                    lengthCurrent, lengthCurrent, lengthCurrent,
                    lengthCurrent, lengthCurrent, lengthCurrent
            );

            return fPlane;
        });

        FPlane resultA = producer.produce();
        FPlane resultB = producer.produce();

        Assertions.assertAll("Validate FPlane values",
                () -> assertTrue(resultA.getRefOrigin().isExact(1, 1, 1, 1, 1, 1),
                        "The FPlane A value is erroneous"),
                () -> assertTrue(resultB.getRefOrigin().isExact(2, 2, 2, 2, 2, 2),
                        "The FPlane B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FPlaneProducer producer = factory.getFPlaneProducer();

        producer
                .addConfig((fPlane) -> fPlane.set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 0.25)
                .addConfig((fPlane) -> fPlane.set(
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

        Assertions.assertAll("Validate FPlane values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }
    @Test
    @DisplayName("Preset unit X")
    void presetUnitX() {
        FPlaneProducer producer = factory.getFPlaneProducer().setPresetUnitX();

        FPlane resultA = producer.produce();
        FPlane resultB = producer.produce();

        Assertions.assertAll("Validate FPlane values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset unit Y")
    void presetUnitY() {
        FPlaneProducer producer = factory.getFPlaneProducer().setPresetUnitY();

        FPlane resultA = producer.produce();
        FPlane resultB = producer.produce();

        Assertions.assertAll("Validate FPlane values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 1, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset unit Z")
    void presetUnitZ() {
        FPlaneProducer producer = factory.getFPlaneProducer().setPresetUnitZ();

        FPlane resultA = producer.produce();
        FPlane resultB = producer.produce();

        Assertions.assertAll("Validate FPlane values",
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
        FPlaneProducer producer = factory.getFPlaneProducer().setPresetFixedPoint(fPos3D);

        FPlane resultA = producer.produce();
        FPlane resultB = producer.produce();

        Assertions.assertAll("Validate FPlane values",
                () -> assertTrue(resultA.isPartOf(factory.getFPoint(1, 2, 3)),
                        "The point should be a part of the FPlane"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
