package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneProducer;
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
        FPlaneProducer producer = factory.getFPlaneProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
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
    @DisplayName("Preset set unit X")
    void presetSetUnitX() {
        FPlaneProducer producer = factory.getFPlaneProducer();
        producer.setPresetDirX();

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
    @DisplayName("Preset add unit X")
    void presetAddUnitX() {
        FPlaneProducer producer = factory.getFPlaneProducer().addPresetDirX(1);

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
    @DisplayName("Preset set unit Y")
    void presetSetUnitY() {
        FPlaneProducer producer = factory.getFPlaneProducer();
        producer.setPresetDirY();

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
    @DisplayName("Preset add unit Y")
    void presetAddUnitY() {
        FPlaneProducer producer = factory.getFPlaneProducer().addPresetDirY(1);

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
    @DisplayName("Preset set unit Z")
    void presetSetUnitZ() {
        FPlaneProducer producer = factory.getFPlaneProducer();
        producer.setPresetDirZ();

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
    @DisplayName("Preset add unit Z")
    void presetAddUnitZ() {
        FPlaneProducer producer = factory.getFPlaneProducer().addPresetDirZ(1);

        FPlane resultA = producer.produce();
        FPlane resultB = producer.produce();

        Assertions.assertAll("Validate FPlane values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
