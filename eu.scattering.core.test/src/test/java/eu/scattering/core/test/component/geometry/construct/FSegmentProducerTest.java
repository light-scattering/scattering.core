package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FSegmentProducer")
public class FSegmentProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FSegmentProducer producer = factory.getFSegmentProducer().setPresetEmpty();

        FSegment resultA = producer.produce();
        FSegment resultB = producer.produce();

        Assertions.assertAll("Validate FSegment values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 0),
                        "The FSegment A value is erroneous"),
                () -> assertTrue(resultB.getRefOrigin().isExact(0, 0, 0, 0, 0, 0),
                        "The FSegment B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        FSegmentProducer producer = factory.getFSegmentProducer();

        AtomicInteger length = new AtomicInteger(1);
        producer.setConfig((fSegment) -> {
            int lengthCurrent = length.getAndIncrement();

            fSegment.getRefOrigin().set(
                    lengthCurrent, lengthCurrent, lengthCurrent,
                    lengthCurrent, lengthCurrent, lengthCurrent
            );

            return fSegment;
        });

        FSegment resultA = producer.produce();
        FSegment resultB = producer.produce();

        Assertions.assertAll("Validate FSegment values",
                () -> assertTrue(resultA.getRefOrigin().isExact(1, 1, 1, 1, 1, 1),
                        "The FSegment A value is erroneous"),
                () -> assertTrue(resultB.getRefOrigin().isExact(2, 2, 2, 2, 2, 2),
                        "The FSegment B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FSegmentProducer producer = factory.getFSegmentProducer();

        producer
                .addConfig((fSegment) -> fSegment.set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 0.25)
                .addConfig((fSegment) -> fSegment.set(
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

        Assertions.assertAll("Validate FSegment values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }
    @Test
    @DisplayName("Preset unit X")
    void presetUnitX() {
        FSegmentProducer producer = factory.getFSegmentProducer().setPresetUnitX();

        FSegment resultA = producer.produce();
        FSegment resultB = producer.produce();

        Assertions.assertAll("Validate FSegment values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset unit Y")
    void presetUnitY() {
        FSegmentProducer producer = factory.getFSegmentProducer().setPresetUnitY();

        FSegment resultA = producer.produce();
        FSegment resultB = producer.produce();

        Assertions.assertAll("Validate FSegment values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 1, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset unit Z")
    void presetUnitZ() {
        FSegmentProducer producer = factory.getFSegmentProducer().setPresetUnitZ();

        FSegment resultA = producer.produce();
        FSegment resultB = producer.produce();

        Assertions.assertAll("Validate FSegment values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
