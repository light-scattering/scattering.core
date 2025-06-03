package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.line.FLineProducer;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FLineProducer")
public class FLineProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FLineProducer producer = factory.getFLineProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        FLineProducer producer = factory.getFLineProducer();

        AtomicInteger length = new AtomicInteger(1);
        producer.setConfig((fLine) -> {
            int lengthCurrent = length.getAndIncrement();

            fLine.getRefOrigin().set(
                    lengthCurrent, lengthCurrent, lengthCurrent,
                    lengthCurrent, lengthCurrent, lengthCurrent
            );

            return fLine;
        });

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(resultA.getRefOrigin().isExact(1, 1, 1, 1, 1, 1),
                        "The FLine A value is erroneous"),
                () -> assertTrue(resultB.getRefOrigin().isExact(2, 2, 2, 2, 2, 2),
                        "The FLine B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FLineProducer producer = factory.getFLineProducer();

        producer
                .addConfig((fLine) -> fLine.set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 0.25)
                .addConfig((fLine) -> fLine.set(
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

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Preset set unit X")
    void presetSetUnitX() {
        FLineProducer producer = factory.getFLineProducer();
        producer.setPresetOX();

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset add unit X")
    void presetAddUnitX() {
        FLineProducer producer = factory.getFLineProducer().addPresetOX(1);

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset set unit Y")
    void presetSetUnitY() {
        FLineProducer producer = factory.getFLineProducer();
        producer.setPresetOY();

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 1, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset add unit Y")
    void presetAddUnitY() {
        FLineProducer producer = factory.getFLineProducer().addPresetOY(1);

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 1, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset set unit Z")
    void presetSetUnitZ() {
        FLineProducer producer = factory.getFLineProducer();
        producer.setPresetOZ();

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset add unit Z")
    void presetAddUnitZ() {
        FLineProducer producer = factory.getFLineProducer().addPresetOZ(1);

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset set fixed point")
    void presetSetFixedPoint() {
        FPos3D fPos3D = factory.getFPos3D(1, 2, 3);
        FLineProducer producer = factory.getFLineProducer();
        producer.setPresetFixedPoint(fPos3D);

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(resultA.isPartOf(factory.getFPoint(1, 2, 3)),
                        "The point should be a part of the FLine"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset add fixed point")
    void presetAddFixedPoint() {
        FPos3D fPos3D = factory.getFPos3D(1, 2, 3);
        FLineProducer producer = factory.getFLineProducer().addPresetFixedPoint(fPos3D, 1);

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(resultA.isPartOf(factory.getFPoint(1, 2, 3)),
                        "The point should be a part of the FLine"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
