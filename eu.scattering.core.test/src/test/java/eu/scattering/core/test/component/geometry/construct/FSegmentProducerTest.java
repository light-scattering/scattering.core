package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentProducer;
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
@DisplayName("FSegmentProducer")
public class FSegmentProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FSegmentProducer producer = factory.getFSegmentProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        AtomicInteger length = new AtomicInteger(1);

        FSegmentProducer producer = factory.getFSegmentProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            FSegment fSegment = factory.getFSegment();

            fSegment.getRefOrigin().set(
                    lengthCurrent, lengthCurrent, lengthCurrent,
                    lengthCurrent, lengthCurrent, lengthCurrent
            );

            return fSegment;
        }, 1);

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
    @DisplayName("Produce custom (simple)")
    void produceCustomSimple() {
        AtomicInteger length = new AtomicInteger(1);

        FSegmentProducer producer = factory.getFSegmentProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            FSegment fSegment = factory.getFSegment();

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
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 1)
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 2, 0, 0)), 3);

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
    @DisplayName("Iterate, auto")
    void iterateAuto() {
        FSegmentProducer producer = factory.getFSegmentProducer();

        producer
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 5)
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 2, 0, 0)), 10)
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 3, 0, 0)), 15);

        int qValue1 = 0;
        int qValue2 = 0;
        int qValue3 = 0;

        for (FSegment construct : producer.getListAuto()) {

            if (construct.getRefOrigin().getHeadX() == 1) {
                qValue1++;
            } else if (construct.getRefOrigin().getHeadX() == 2) {
                qValue2++;
            } else if (construct.getRefOrigin().getHeadX() == 3) {
                qValue3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals( 5, qValue1, "Distribution 1 is erroneous");
        assertEquals(10, qValue2, "Distribution 2 is erroneous");
        assertEquals(15, qValue3, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, fixed")
    void iterateFixed() {
        FSegmentProducer producer = factory.getFSegmentProducer();

        producer
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 1)
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 2, 0, 0)), 1)
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 3, 0, 0)), 1);

        int qValue1 = 0;
        int qValue2 = 0;
        int qValue3 = 0;

        for (FSegment construct : producer.getListFixed(8)) {

            if (construct.getRefOrigin().getHeadX() == 1) {
                qValue1++;
            } else if (construct.getRefOrigin().getHeadX() == 2) {
                qValue2++;
            } else if (construct.getRefOrigin().getHeadX() == 3) {
                qValue3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals(8, qValue1 + qValue2 + qValue3, "The number of elements is incorrect");
        assertEquals(3, qValue1, 1, "Distribution 1 is erroneous");
        assertEquals(3, qValue2, 1, "Distribution 2 is erroneous");
        assertEquals(3, qValue3, 1, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, random")
    void iterateRandom() {
        FSegmentProducer producer = factory.getFSegmentProducer();

        producer
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 20)
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 2, 0, 0)), 20)
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 3, 0, 0)), 20);

        List<FSegment> results = producer.getListRandomized(60);

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getRefOrigin().getHeadX() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }

    @Test
    @DisplayName("Stream")
    void stream() {
        FSegmentProducer producer = factory.getFSegmentProducer();

        producer
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 20)
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 2, 0, 0)), 20)
                .withCustomRule((factoryInternal) -> factoryInternal.getFSegment().set(
                        factory.getFPairPos3D(0, 0, 0, 3, 0, 0)), 20);

        List<FSegment> results = producer.stream().limit(60).collect(Collectors.toList());

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getRefOrigin().getHeadX() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }

    @Test
    @DisplayName("Preset FVector")
    void presetFVector() {
        FVectorProducer origin = factory.getFVectorProducer()
                .withDirOX(5);

        FSegmentProducer producer = factory.getFSegmentProducer()
                .withFVector(origin, 1);

        FSegment resultA = producer.produce();
        FSegment resultB = producer.produce();

        Assertions.assertAll("Validate values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 5, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset FVector (simple)")
    void presetFVectorSimple() {
        FVectorProducer origin = factory.getFVectorProducer()
                .withDirOX(5);

        FSegmentProducer producer = factory.getFSegmentProducer()
                .withFVector(origin);

        FSegment resultA = producer.produce();
        FSegment resultB = producer.produce();

        Assertions.assertAll("Validate values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 5, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce with engine")
    void produceWithEngine() {
        FSegmentProducer producer = factory.getFSegmentProducer().withCustomRule((factory, engine) -> {
            FSegment fSegment = factory.getFSegment();

            fSegment.getRefOrigin().set(
                    1, 2, 3,
                    2, 2, 3
            );

            engine.onSphere(fSegment.getRefOrigin());

            return fSegment;
        });

        FSegment resultA = producer.produce();
        FSegment resultB = producer.produce();

        Assertions.assertAll("Validate FSegment values",
                () -> assertEquals(1, resultA.getRefOrigin().getMagnitude(),
                        epsilon, "The FSegment A magnitude is erroneous"),
                () -> assertEquals(1, resultB.getRefOrigin().getMagnitude(),
                        epsilon, "The FSegment B magnitude is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
