package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.line.FLineProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        AtomicInteger length = new AtomicInteger(1);

        FLineProducer producer = factory.getFLineProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            FLine fLine = factory.getFLine();

            fLine.getRefOrigin().set(
                    lengthCurrent, lengthCurrent, lengthCurrent,
                    lengthCurrent, lengthCurrent, lengthCurrent
            );

            return fLine;
        }, 1);

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
    @DisplayName("Produce custom (simple)")
    void produceCustomSimple() {
        AtomicInteger length = new AtomicInteger(1);

        FLineProducer producer = factory.getFLineProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            FLine fLine = factory.getFLine();

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
                .withCustomRule((factoryInternal) -> factoryInternal.getFLine().set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 1)
                .withCustomRule((factoryInternal) -> factoryInternal.getFLine().set(
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

        Assertions.assertAll("Validate FLine values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate")
    void iterate() {
        FLineProducer producer = factory.getFLineProducer();

        producer
                .withCustomRule((factoryInternal) -> factoryInternal.getFLine().set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 5)
                .withCustomRule((factoryInternal) -> factoryInternal.getFLine().set(
                        factory.getFPairPos3D(0, 0, 0, 2, 0, 0)), 10)
                .withCustomRule((factoryInternal) -> factoryInternal.getFLine().set(
                        factory.getFPairPos3D(0, 0, 0, 3, 0, 0)), 15);

        int qValue1 = 0;
        int qValue2 = 0;
        int qValue3 = 0;

        for (FLine construct : producer) {

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
    @DisplayName("Stream")
    void stream() {
        FLineProducer producer = factory.getFLineProducer();

        producer
                .withCustomRule((factoryInternal) -> factoryInternal.getFLine().set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 1)
                .withCustomRule((factoryInternal) -> factoryInternal.getFLine().set(
                        factory.getFPairPos3D(0, 0, 0, 2, 0, 0)), 5);

        List<FLine> list = producer.stream().limit(100).collect(Collectors.toList());

        Assertions.assertAll("Validate values",
                () -> assertTrue(list.stream().anyMatch(e -> e.getRefOrigin().getHeadX() == 1),
                        "The distribution is erroneous"),
                () -> assertTrue(list.stream().anyMatch(e -> e.getRefOrigin().getHeadX() == 2),
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Preset FVector")
    void presetFVector() {
        FVectorProducer origin = factory.getFVectorProducer()
                .withDirOX(5);

        FLineProducer producer = factory.getFLineProducer()
                .withFVector(origin, 1);

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

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

        FLineProducer producer = factory.getFLineProducer()
                .withFVector(origin);

        FLine resultA = producer.produce();
        FLine resultB = producer.produce();

        Assertions.assertAll("Validate values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 5, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
