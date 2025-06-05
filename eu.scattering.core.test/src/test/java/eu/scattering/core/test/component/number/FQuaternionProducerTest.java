package eu.scattering.core.test.component.number;

import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.component.number.quaternion.FQuaternionProducer;
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
@DisplayName("FQuaternionProducer")
public class FQuaternionProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FQuaternionProducer producer = factory.getFQuaternionProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        AtomicInteger length = new AtomicInteger(1);

        FQuaternionProducer producer = factory.getFQuaternionProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            return factory.getFQuaternion(lengthCurrent, lengthCurrent, lengthCurrent, lengthCurrent);
        }, 1);

        FQuaternion resultA = producer.produce();
        FQuaternion resultB = producer.produce();

        Assertions.assertAll("Validate FQuaternion values",
                () -> assertTrue(resultA.isExact(1, 1, 1, 1),
                        "The FQuaternion A value is erroneous"),
                () -> assertTrue(resultB.isExact(2, 2, 2, 2),
                        "The FQuaternion B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce custom (simple)")
    void produceCustomSimple() {
        AtomicInteger length = new AtomicInteger(1);

        FQuaternionProducer producer = factory.getFQuaternionProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            return factory.getFQuaternion(lengthCurrent, lengthCurrent, lengthCurrent, lengthCurrent);
        });

        FQuaternion resultA = producer.produce();
        FQuaternion resultB = producer.produce();

        Assertions.assertAll("Validate FQuaternion values",
                () -> assertTrue(resultA.isExact(1, 1, 1, 1),
                        "The FQuaternion A value is erroneous"),
                () -> assertTrue(resultB.isExact(2, 2, 2, 2),
                        "The FQuaternion B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FQuaternionProducer producer = factory.getFQuaternionProducer();

        producer
                .withCustomRule((factory) -> factory.getFQuaternion(1, 1, 1, 1), 1)
                .withCustomRule((factory) -> factory.getFQuaternion(2, 2, 2, 2), 2);

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
        FQuaternionProducer producer = factory.getFQuaternionProducer();

        producer
                .withCustomRule((factory) -> factory.getFQuaternion(1, 1, 1, 1), 5)
                .withCustomRule((factory) -> factory.getFQuaternion(2, 2, 2, 2), 10)
                .withCustomRule((factory) -> factory.getFQuaternion(3, 3, 3, 3), 15);

        int qRe1 = 0;
        int qRe2 = 0;
        int qRe3 = 0;

        for (FQuaternion fQuaternion : producer) {

            if (fQuaternion.getRe() == 1) {
                qRe1++;
            } else if (fQuaternion.getRe() == 2) {
                qRe2++;
            } else if (fQuaternion.getRe() == 3) {
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
        FQuaternionProducer producer = factory.getFQuaternionProducer();

        producer
                .withCustomRule((factory) -> factory.getFQuaternion(1, 1, 1, 1), 1)
                .withCustomRule((factory) -> factory.getFQuaternion(2, 2, 2, 2), 1);

        List<FQuaternion> list = producer.stream().limit(100).collect(Collectors.toList());

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
        FQuaternionProducer producer = factory.getFQuaternionProducer()
                .withZero(1);

        FQuaternion resultA = producer.produce();
        FQuaternion resultB = producer.produce();

        Assertions.assertAll("Validate FQuaternion values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 0),
                        "The FQuaternion A value is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0, 0, 0),
                        "The FQuaternion B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset zero (simple)")
    void presetZeroSimple() {
        FQuaternionProducer producer = factory.getFQuaternionProducer()
                .withZero();

        FQuaternion resultA = producer.produce();
        FQuaternion resultB = producer.produce();

        Assertions.assertAll("Validate FQuaternion values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 0),
                        "The FQuaternion A value is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0, 0, 0),
                        "The FQuaternion B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
