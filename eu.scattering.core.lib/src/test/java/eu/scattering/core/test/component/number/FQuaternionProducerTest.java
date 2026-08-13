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

import static eu.scattering.core.test.TestConfig.factory;
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
    @DisplayName("Iterate, auto")
    void iterateAuto() {
        FQuaternionProducer producer = factory.getFQuaternionProducer();

        producer
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(1), 5)
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(2), 10)
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(3), 15);

        int qRe1 = 0;
        int qRe2 = 0;
        int qRe3 = 0;

        for (FQuaternion fQuaternion : producer.getList()) {

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
    @DisplayName("Iterate, fixed")
    void iterateFixed() {
        FQuaternionProducer producer = factory.getFQuaternionProducer();

        producer
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(1), 1)
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(2), 1)
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(3), 1);

        int qRe1 = 0;
        int qRe2 = 0;
        int qRe3 = 0;

        for (FQuaternion fQuaternion : producer.getListFixed(8)) {

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

        assertEquals(8, qRe1 + qRe2 + qRe3, "The number of elements is incorrect");
        assertEquals(3, qRe1, 1, "Distribution 1 is erroneous");
        assertEquals(3, qRe2, 1, "Distribution 2 is erroneous");
        assertEquals(3, qRe3, 1, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, random")
    void iterateRandom() {
        FQuaternionProducer producer = factory.getFQuaternionProducer();

        producer
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(1), 20)
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(2), 20)
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(3), 20);

        List<FQuaternion> results = producer.getListRandomized(60);

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getRe() != 1) {
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
        FQuaternionProducer producer = factory.getFQuaternionProducer();

        producer
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(1), 20)
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(2), 20)
                .withCustomRule((factory) -> factory.getFQuaternion().setRe(3), 20);

        List<FQuaternion> results = producer.stream().limit(60).collect(Collectors.toList());

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getRe() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
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

    @Test
    @DisplayName("Produce with randomizer")
    void produceWithRandomizer() {
        FQuaternionProducer producer = factory.getFQuaternionProducer().withCustomRule((factory, random) -> {
            double re = random.nextDouble(0.01, 0.02);
            double i = random.nextDouble(0.03, 0.04);
            double j = random.nextDouble(0.05, 0.06);
            double k = random.nextDouble(0.07, 0.08);

            return factory.getFQuaternion(re, i, j, k);
        }, 1);

        FQuaternion resultA = producer.produce();
        FQuaternion resultB = producer.produce();

        Assertions.assertAll("Validate FQuaternion values",
                () -> assertTrue(resultA.getRe() >= 0.01 && resultA.getRe() < 0.02,
                        "The FQuaternion A Re value is erroneous"),
                () -> assertTrue(resultA.getI() >= 0.03 && resultA.getI() < 0.04,
                        "The FQuaternion A I value is erroneous"),
                () -> assertTrue(resultA.getJ() >= 0.05 && resultA.getJ() < 0.06,
                        "The FQuaternion A J value is erroneous"),
                () -> assertTrue(resultA.getK() >= 0.07 && resultA.getK() < 0.08,
                        "The FQuaternion A K value is erroneous"),
                () -> assertTrue(resultB.getRe() >= 0.01 && resultB.getRe() < 0.02,
                        "The FQuaternion B Re value is erroneous"),
                () -> assertTrue(resultB.getI() >= 0.03 && resultB.getI() < 0.04,
                        "The FQuaternion B I value is erroneous"),
                () -> assertTrue(resultB.getJ() >= 0.05 && resultB.getJ() < 0.06,
                        "The FQuaternion B J value is erroneous"),
                () -> assertTrue(resultB.getK() >= 0.07 && resultB.getK() < 0.08,
                        "The FQuaternion B K value is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce with randomizer (simple)")
    void produceWithRandomizerSimple() {
        FQuaternionProducer producer = factory.getFQuaternionProducer().withCustomRule((factory, random) -> {
            double re = random.nextDouble(0.01, 0.02);
            double i = random.nextDouble(0.03, 0.04);
            double j = random.nextDouble(0.05, 0.06);
            double k = random.nextDouble(0.07, 0.08);

            return factory.getFQuaternion(re, i, j, k);
        });

        FQuaternion resultA = producer.produce();
        FQuaternion resultB = producer.produce();

        Assertions.assertAll("Validate FQuaternion values",
                () -> assertTrue(resultA.getRe() >= 0.01 && resultA.getRe() < 0.02,
                        "The FQuaternion A Re value is erroneous"),
                () -> assertTrue(resultA.getI() >= 0.03 && resultA.getI() < 0.04,
                        "The FQuaternion A I value is erroneous"),
                () -> assertTrue(resultA.getJ() >= 0.05 && resultA.getJ() < 0.06,
                        "The FQuaternion A J value is erroneous"),
                () -> assertTrue(resultA.getK() >= 0.07 && resultA.getK() < 0.08,
                        "The FQuaternion A K value is erroneous"),
                () -> assertTrue(resultB.getRe() >= 0.01 && resultB.getRe() < 0.02,
                        "The FQuaternion B Re value is erroneous"),
                () -> assertTrue(resultB.getI() >= 0.03 && resultB.getI() < 0.04,
                        "The FQuaternion B I value is erroneous"),
                () -> assertTrue(resultB.getJ() >= 0.05 && resultB.getJ() < 0.06,
                        "The FQuaternion B J value is erroneous"),
                () -> assertTrue(resultB.getK() >= 0.07 && resultB.getK() < 0.08,
                        "The FQuaternion B K value is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
