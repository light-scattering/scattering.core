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
        FLineProducer producer = factory.getFLineProducer().setPresetEmpty();

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 0),
                        "The FLine A value is erroneous"),
                () -> assertTrue(resultB.getRefOrigin().isExact(0, 0, 0, 0, 0, 0),
                        "The FLine B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
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
    @DisplayName("Preset unit X")
    void presetUnitX() {
        FLineProducer producer = factory.getFLineProducer().setPresetUnitX();

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
    @DisplayName("Preset unit Y")
    void presetUnitY() {
        FLineProducer producer = factory.getFLineProducer().setPresetUnitY();

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
    @DisplayName("Preset unit Z")
    void presetUnitZ() {
        FLineProducer producer = factory.getFLineProducer().setPresetUnitZ();

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
    @DisplayName("Preset fixed point")
    void presetFixedPoint() {
        FPos3D fPos3D = factory.getFPos3D(1, 2, 3);
        FLineProducer producer = factory.getFLineProducer().setPresetFixedPoint(fPos3D);

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
