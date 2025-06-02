package eu.scattering.core.test.component.number;

import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.component.number.quaternion.FQuaternionProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FQuaternionProducer")
public class FQuaternionProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FQuaternionProducer producer = factory.getFQuaternionProducer();

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
    @DisplayName("Produce custom")
    void produceCustom() {
        FQuaternionProducer producer = factory.getFQuaternionProducer();

        AtomicInteger length = new AtomicInteger(1);
        producer.setConfig((fQuaternion) -> {
            int lengthCurrent = length.getAndIncrement();

            fQuaternion.set(lengthCurrent, lengthCurrent, lengthCurrent, lengthCurrent);

            return fQuaternion;
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
                .setConfig((fQuaternion) -> fQuaternion.setRe(1), 0.25)
                .addConfig((fQuaternion) -> fQuaternion.setRe(2), 0.75);

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

        Assertions.assertAll("Validate FQuaternion values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Preset default")
    void presetDefault() {
        FQuaternionProducer producer = factory.getFQuaternionProducer().setPresetDefault();

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
