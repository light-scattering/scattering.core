package eu.scattering.core.test.component.number;

import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.complex.FComplexProducer;
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
@DisplayName("FComplexProducer")
public class FComplexProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FComplexProducer producer = factory.getFComplexProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        AtomicInteger length = new AtomicInteger(1);

        FComplexProducer producer = factory.getFComplexProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            return factory.getFComplex(lengthCurrent, lengthCurrent);
        }, 1);

        FComplex resultA = producer.produce();
        FComplex resultB = producer.produce();

        Assertions.assertAll("Validate FComplex values",
                () -> assertTrue(resultA.isExact(1, 1),
                        "The FComplex A value is erroneous"),
                () -> assertTrue(resultB.isExact(2, 2),
                        "The FComplex B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce custom (simple)")
    void produceCustomSimple() {
        AtomicInteger length = new AtomicInteger(1);

        FComplexProducer producer = factory.getFComplexProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            return factory.getFComplex(lengthCurrent, lengthCurrent);
        });

        FComplex resultA = producer.produce();
        FComplex resultB = producer.produce();

        Assertions.assertAll("Validate FComplex values",
                () -> assertTrue(resultA.isExact(1, 1),
                        "The FComplex A value is erroneous"),
                () -> assertTrue(resultB.isExact(2, 2),
                        "The FComplex B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FComplexProducer producer = factory.getFComplexProducer();

        producer
                .withCustomRule((factory) -> factory.getFComplex(1, 1), 1)
                .withCustomRule((factory) -> factory.getFComplex(2, 2), 2);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getRe() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FComplex values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate")
    void iterate() {
        FComplexProducer producer = factory.getFComplexProducer();

        producer
                .withCustomRule((factory) -> factory.getFComplex(1, 1), 5)
                .withCustomRule((factory) -> factory.getFComplex(2, 2), 10)
                .withCustomRule((factory) -> factory.getFComplex(3, 3), 15);

        int qRe1 = 0;
        int qRe2 = 0;
        int qRe3 = 0;

        for (FComplex fComplex : producer) {

            if (fComplex.getRe() == 1) {
                qRe1++;
            } else if (fComplex.getRe() == 2) {
                qRe2++;
            } else if (fComplex.getRe() == 3) {
                qRe3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals( 5, qRe1, "Distribution 1 is erroneous");
        assertEquals(10, qRe2, "Distribution 2 is erroneous");
        assertEquals(15, qRe3, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Stream")
    void stream() {
        FComplexProducer producer = factory.getFComplexProducer();

        producer
                .withCustomRule((factory) -> factory.getFComplex(1, 1), 1)
                .withCustomRule((factory) -> factory.getFComplex(2, 2), 1);

        List<FComplex> list = producer.stream().limit(100).collect(Collectors.toList());

        Assertions.assertAll("Validate values",
                () -> assertTrue(list.stream().anyMatch(e -> e.getRe() == 1),
                        "The distribution is erroneous"),
                () -> assertTrue(list.stream().anyMatch(e -> e.getRe() == 2),
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Preset zero")
    void presetZero() {
        FComplexProducer producer = factory.getFComplexProducer()
                .withZero(1);

        FComplex resultA = producer.produce();
        FComplex resultB = producer.produce();

        Assertions.assertAll("Validate FComplex values",
                () -> assertTrue(resultA.isExact(0, 0),
                        "The FComplex A value is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0),
                        "The FComplex B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset zero (simple)")
    void presetZeroSimple() {
        FComplexProducer producer = factory.getFComplexProducer()
                .withZero();

        FComplex resultA = producer.produce();
        FComplex resultB = producer.produce();

        Assertions.assertAll("Validate FComplex values",
                () -> assertTrue(resultA.isExact(0, 0),
                        "The FComplex A value is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0),
                        "The FComplex B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
